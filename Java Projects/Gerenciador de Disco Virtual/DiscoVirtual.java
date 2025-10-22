import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class DiscoVirtual {
	private Driver d;

	private int clusterSize = 240;
	private int quantClusters = 2160;

	private ArrayList<Byte> buffer;
	private int[] vetFAT;
	private DirEntry[] dirAtual;
	private int currentPosFATdir;
	private Stack<Integer> stackPosFATdir;

	String divisoria = "----------------------------------------------";

	public DiscoVirtual() {
		d = new Driver(clusterSize, quantClusters);

		buffer = new ArrayList<Byte>();
		vetFAT = new int[quantClusters];
		dirAtual = new DirEntry[10];
		currentPosFATdir = 20;
		stackPosFATdir = new Stack<Integer>();
	}

	public void inicializaDV() {
		if (d.FATisWritten()) { // se fat tiver escrita, le a fat do disco
			leFATdoDisco();
			changeDirAtual(20);
		} else { // se nao tiver escrita, cria uma nova
			vetFAT[0] = 0; // boot

			for (int i = 1; i < 19; i++) {
				vetFAT[i] = i + 1; // clusters da FAT
			}

			vetFAT[19] = 0; // ultimo cluster da FAT
			vetFAT[20] = 0; // diretorio raiz

			for (int i = 21; i < 2160; i++) {
				vetFAT[i] = 9999; // clusters livres
			}

			atualizaFATnoDisco();
		}
	}

	private void leFATdoDisco() {
		// insere os bytes da fat no buffer
		for (int i = 1; i <= 19; i++) {
			byte[] cluster = d.readCluster(i);
			for (byte b : cluster)
				buffer.add(b);
		}

		Iterator<Byte> itBuffer = buffer.iterator();

		for (int i = 0; i < 2160; i++) {
			// soma os dois bytes para int
			int posFAT = (itBuffer.next() * 256) + Byte.toUnsignedInt(itBuffer.next());
			vetFAT[i] = posFAT;
		}

		clearBuffer();
	}

	private void atualizaFATnoDisco() {
		// copia vetFAT para buffer
		for (int i = 0; i < quantClusters; i++) {
			buffer.add((byte) (vetFAT[i] / 256));
			buffer.add((byte) (vetFAT[i] % 256));
		}

		// copia buffer para o disco
		writeBufferOnDisk(1, 19, true);
	}

	private void atualizaDirAtualNoDisco() {
		// pega todas as entradas e pega seus bytes, e coloca no buffer
		for (DirEntry e : dirAtual) {
			if (e != null) {
				for (byte bytesDir : e.getAllBytes())
					buffer.add(bytesDir);
			}
		}

		// escreve o dir em bytes na posicao dele
		writeBufferOnDisk(currentPosFATdir, 1, true);
	}

	private void writeBufferOnDisk(int clusterInicial, int quantosClusters, boolean sobrescreve) {
		Iterator<Byte> itBuffer = buffer.iterator();
		int prevCluster = -1;
		// segue o iterator do buffer colocando na posicao em sequencia do cluster
		// criado
		for (int qClusters = 0; qClusters < quantosClusters; qClusters++) {
			byte[] cluster = new byte[clusterSize];

			for (int i = 0; i < clusterSize; i++) {
				if (itBuffer.hasNext())
					cluster[i] = itBuffer.next();
			}

			int writtenCluster;

			// se sobrescreve for true, os clusters podem ser sobrescritos
			// se não, pega o proximo cluster livro
			if (sobrescreve)
				writtenCluster = clusterInicial + qClusters;
			else
				writtenCluster = getNextCluster();

			// escreve cluster no disco
			d.writeCluster(writtenCluster, cluster);

			// muda a fat colocando os clusters na ordem caso nao sobrescrever
			if (!sobrescreve) {
				vetFAT[writtenCluster] = 0; // coloca 0 no cluster atual (se for o ultimo, fica 0 também)

				if (qClusters > 0) // escreve no cluster anterior da fat o cluster atual
					vetFAT[prevCluster] = writtenCluster;

				prevCluster = writtenCluster;
			}
		}
		clearBuffer();
	}

	private int changePosExtendedDir(int posDir) {
		if (posDir > 9) {
			for (int i = 0; i < posDir / 10; i++) {
				if (vetFAT[currentPosFATdir] != 0 && vetFAT[currentPosFATdir] != 9999)
					changeDirAtual(vetFAT[currentPosFATdir]);
				else
					return posDir % 10;
			}
		}

		return posDir % 10;
	}

	private int verificaNome(String nome, char tipo) {
		// verifica se o nome do parametro existe no diretorio
		boolean extPasta = true;
		int firstPosFATdir = currentPosFATdir;
		int extPastaNum = 0;

		// procura o nome na pasta
		while (extPasta) {
			for (int i = 0; i < dirAtual.length; i++) {
				if (dirAtual[i] != null && dirAtual[i].getTipo() == tipo
						&& dirAtual[i].getNomeExt().equalsIgnoreCase(nome)) {
					changeDirAtual(firstPosFATdir);
					return (extPastaNum * 10) + i; // retorna a posicao se tiver o mesmo nome
				}
			}

			// se a pasta for estendida, faz o loop novamente no novo diretório
			if (vetFAT[currentPosFATdir] != 0) {
				changeDirAtual(vetFAT[currentPosFATdir]);
				extPastaNum++;
			} else
				extPasta = false;
		}

		// retorna -1 se o nome não existir
		changeDirAtual(firstPosFATdir);
		return -1;
	}

	private void importaArquivo(String nomeArquivo, String nomeArquivoDisco) {
		// importa arquivo fora do disco e insere no disco
		try (FileInputStream fis = new FileInputStream(nomeArquivoDisco)) {
			byte byteLido;
			int tam = 0;
			int quantClusterArq = 1;

			// le os bytes do arquivo para o buffer
			while ((byteLido = (byte) fis.read()) != -1) {
				buffer.add(tam++, byteLido);
				if (tam % clusterSize == 0)
					quantClusterArq++;
			}

			// joga excecao se o arquivo for maior que o espaco livre
			if (quantClusterArq > getQuantFreeClusters()) {
				System.out.println("ERRO: disco cheio");
				clearBuffer();
				return;
			}

			// escreve buffer no primeiro cluster livre
			int firstCluster = getNextCluster();

			writeBufferOnDisk(firstCluster, quantClusterArq, false);
			atualizaFATnoDisco();

			// separa extensão do nome do arquivo do disco
			String[] nomeArquivoArray = nomeArquivoDisco.split("\\.");
			addDirEntry(nomeArquivo, nomeArquivoArray[1], 'A', firstCluster, tam, -1);

		} catch (FileNotFoundException e) {
			System.out.println("ERRO: arquivo não encontrado/não existe");
			clearBuffer();
		} catch (IOException e) {
			System.out.println("ERRO na importação do arquivo");
			clearBuffer();
		}
	}

	private DirEntry leArquivo(int posDir) {
		// CUIDADO: não limpa buffer
		int oldPosFATdir = currentPosFATdir;

		// troca a diratual se a posicao for maior que 9, e retorna a posicao na dir
		// trocada
		posDir = changePosExtendedDir(posDir);

		if (posDir == -1 || dirAtual[posDir] == null || dirAtual[posDir].getTipo() != 'A') {
			changeDirAtual(oldPosFATdir);
			return null;
		}

		DirEntry arquivo = dirAtual[posDir];
		int totClusters = (arquivo.getTamanho() / 240) + 1;
		int clusterFATatual = arquivo.getCluster1();

		changeDirAtual(oldPosFATdir);

		// adiciona os bytes do arquivo no buffer
		for (int clusterAtual = 0; clusterAtual < totClusters; clusterAtual++) {
			byte[] cluster = d.readCluster(clusterFATatual);

			for (byte b : cluster)
				buffer.add(b);

			if (vetFAT[clusterFATatual] != 0)
				clusterFATatual = vetFAT[clusterFATatual];
		}

		// retorna os dados do arquivo como direntry
		return arquivo;
	}

	private void changeDirAtual(int posFAT) {
		// le o diretorio do disco e coloca no buffer
		for (byte b : d.readCluster(posFAT)) {
			buffer.add(b);
		}

		Iterator<Byte> itBuffer = buffer.iterator();
		DirEntry[] newDir = new DirEntry[10];

		// usa os sets do direntry para colocar os bytes
		for (int i = 0; i < 10; i++) {
			newDir[i] = new DirEntry();

			for (int posNome = 0; posNome < 16; posNome++) {
				newDir[i].setByteNome(posNome, itBuffer.next());
			}

			for (int posExt = 0; posExt < 3; posExt++) {
				newDir[i].setByteExt(posExt, itBuffer.next());
			}

			newDir[i].setTipo((char) (byte) itBuffer.next());

			// para o cluster e tamanho, soma os dois bytes (multiplicando e transformando
			// de byte para int)
			newDir[i].setCluster1((short) ((itBuffer.next() * 256) + Byte.toUnsignedInt(itBuffer.next())));
			newDir[i].setTamanho((short) ((itBuffer.next() * 256) + Byte.toUnsignedInt(itBuffer.next())));

			// verifica se os bytes são todos nulos, e se sim, coloca o direntry como null
			// na array
			byte[] clusterNulo = new byte[24];
			if (Arrays.equals(newDir[i].getAllBytes(), clusterNulo)) {
				newDir[i] = null;
			}
		}

		dirAtual = newDir;
		currentPosFATdir = posFAT;
		clearBuffer();
	}

	private void addDirEntry(String nome, String ext, char tipo, int cluster1, int tamanho, int estPasta) {
		int oldPosFATdir = estPasta;

		// se for uma pasta estendida, não troca o oldposfatdir
		if (estPasta == -1)
			oldPosFATdir = currentPosFATdir;

		// procura o primeiro elemento nulo da array para inserir
		for (int i = 0; i < dirAtual.length; i++) {
			if (dirAtual[i] == null) {
				dirAtual[i] = new DirEntry(nome, ext, tipo, cluster1, tamanho);
				atualizaDirAtualNoDisco();
				if (estPasta != -1) // se for uma extensao da pasta, volta ao diretório primario
					changeDirAtual(oldPosFATdir);
				return;
			}
		}

		// se a array estiver cheia e não foi estendida ainda, cria uma nova para
		// estender
		if (vetFAT[currentPosFATdir] == 0) {
			int nextCluster = getNextCluster();
			vetFAT[currentPosFATdir] = nextCluster;
			vetFAT[nextCluster] = 0;
			atualizaFATnoDisco();
			dirAtual = new DirEntry[10];
		}

		// troca a diratual para o dir seguinte e chama o mesmo método com a posfatdir
		// principal
		changeDirAtual(vetFAT[currentPosFATdir]);
		addDirEntry(nome, ext, tipo, cluster1, tamanho, oldPosFATdir);
	}

	private void printDirAtual() {
		int oldPosFATdir = currentPosFATdir;
		int estDirAtual = 0;
		boolean extendedDir = true;

		while (extendedDir) {
			// loop que procura e imprime o direntry se nao for nulo
			for (int i = 0; i < dirAtual.length; i++) {
				DirEntry d = dirAtual[i];
				if (d != null) {
					if (estDirAtual == 0 && i == 0) { // cabeçalho
						System.out.println();
						System.out.println(" POS |             NOME | EXT |  1CL |    TAM ");
						System.out.println(divisoria);
					}
					if (estDirAtual == 0) // se o diretorio for estendido, imprime com o numero da posicao na frente
						System.out.printf("   %d | ", i);
					else
						System.out.printf("%3d%d | ", estDirAtual, i);

					System.out.println(d); // imprime a direntry
				} else if (d == null && i == 0 && estDirAtual == 0) {
					System.out.println("ERRO: diretório vazio");
					return;
				}
			}

			// se o diretorio for estendido, troca a diratual para a seguinte e repete o
			// loop
			if (vetFAT[currentPosFATdir] != 0) {
				changeDirAtual(vetFAT[currentPosFATdir]);
				estDirAtual++;
			} else // caso não seja, quebra o loop
				extendedDir = false;
		}

		System.out.println();
		changeDirAtual(oldPosFATdir);
	}

	private int getNextCluster() {
		// retorna o primeiro cluster livre do disco
		for (int i = 21; i < vetFAT.length; i++) {
			if (vetFAT[i] == 9999)
				return i;
		}

		return -1;
	}

	private int getQuantFreeClusters() {
		// retorna o número de clusters livre no disco
		int tot = 0;

		for (int i = 0; i < vetFAT.length; i++) {
			if (vetFAT[i] == 9999)
				tot++;
		}

		return tot;
	}

	private void clearBuffer() {
		// limpa o buffer
		buffer.clear();
	}

	// -----CRIAÇÃO-----

	public void novoArquivo(String nomeArquivo, String nomeArquivoDisco) {
		// se os parametros não são nulos e o nomearquivodisco tem uma extensao
		if (nomeArquivoDisco != null && nomeArquivo != null && nomeArquivoDisco.split("\\.").length > 1) {
			// cria uma string juntando o nome criado com a extensao do arquivo
			String nomeArquivoExt = nomeArquivo + "." + nomeArquivoDisco.split("\\.")[1];
			if (verificaNome(nomeArquivoExt, 'A') == -1) // verifica se já tem um arquivo com o mesmo nome
				importaArquivo(nomeArquivo, nomeArquivoDisco);
			else // se já tiver, imprime o erro
				System.out.println("ERRO: já existe um arquivo neste diretório com o mesmo nome");
		} else
			System.out.println("ERRO: arquivo não encontrado/não existe");
	}

	public void novoDir(String nome) {
		try { // faz um try para ver se o diretorio não é composto apenas por numeros
			Integer.parseInt(nome); // se a conversao der certo, o nome é apenas numeros e imprime o erro
			System.out.println("ERRO: não é possível criar uma pasta com o nome apenas contendo números");
			return;
		} catch (NumberFormatException e) { // caso a excecao seja jogada, o nome contém outros caracteres
			if (verificaNome(nome, 'D') == -1) { // verifica se não tem uma pasta com o mesmo nome
				int pos = getNextCluster(); // procura o cluster livre e coloca na fat
				vetFAT[pos] = 0;
				atualizaFATnoDisco();
				addDirEntry(nome, null, 'D', pos, 0, -1); // adiciona na diratual
			} else // se já existir uma pasta com o mesmo nome, imprime o erro
				System.out.println("ERRO: já existe uma pasta neste diretório com o mesmo nome");
		}
	}

	// -----VISUALIZAÇÃO-----

	public void imprimeArquivo(int posDir) throws InvalidCommandException {
		DirEntry arquivo = leArquivo(posDir);
		if (arquivo == null)
			throw new InvalidCommandException();

		System.out.println();
		System.out.println("ARQUIVO: " + arquivo.getNomeExt() + ":");
		System.out.println(divisoria);

		// imprime os bytes do buffer passando pelo iterator
		Iterator<Byte> itBuffer = buffer.iterator();
		while (itBuffer.hasNext()) {
			byte b = itBuffer.next();
			if (b != 0)
				System.out.print((char) b);
		}

		System.out.println();
		clearBuffer();
	}

	public void visualizaArquivo(int posDir) throws FileDoesNotExistException, InvalidCommandException {
		if (posDir < 0)
			throw new FileDoesNotExistException();
		DirEntry arquivo = leArquivo(posDir);

		// joga excecoes se arquivo for nulo ou nao for arquivo
		if (arquivo == null)
			throw new FileDoesNotExistException();
		if (arquivo.getTipo() != 'A')
			throw new InvalidCommandException();

		// imprime detalhes do arquivo
		System.out.println();
		System.out.println("Detalhes do arquivo " + arquivo.getNomeExt() + ":");
		System.out.println(divisoria);
		System.out.printf("Nome:       %s\n", arquivo.getNome());
		System.out.printf("Extensão:   .%s\n", arquivo.getExt());
		System.out.printf("1° cluster: %d\n", arquivo.getCluster1());
		System.out.printf("Tamanho:    %d\n", arquivo.getTamanho());
		System.out.println();

		clearBuffer();
	}

	public void visualizaPasta(int posDir) throws DirDoesNotExistException, InvalidCommandException {
		if (posDir == -1) {// se parametro for -1, imprime o diretorio atual
			printDirAtual();
		} else {
			int oldPosFATdir = currentPosFATdir;

			posDir = changePosExtendedDir(posDir);

			// joga excecoes se não existir pasta ou se não for uma pasta
			if (dirAtual[posDir] == null) {
				changeDirAtual(oldPosFATdir);
				throw new DirDoesNotExistException();
			}
			if (dirAtual[posDir].getTipo() != 'D') {
				changeDirAtual(oldPosFATdir);
				throw new InvalidCommandException();
			}

			// troca para a posicao do cluster da pasta, imprime, e volta ao cluster
			// original
			changeDirAtual(dirAtual[posDir].getCluster1());
			printDirAtual();
			changeDirAtual(oldPosFATdir);
		}
	}

	public void visualizaPasta(String path) throws DirDoesNotExistException, InvalidCommandException {
		// visualiza a pasta por path
		int oldPosFATdir = currentPosFATdir;

		// imprime a pasta se existir path
		boolean existe = pastaPorPath(path, false);
		if (existe)
			visualizaPasta(-1);

		changeDirAtual(oldPosFATdir);
	}

	// -----REMOÇÃO-----
	public void removeArquivo(int posDir) throws FileDoesNotExistException, InvalidCommandException {
		int oldPosFATdir = currentPosFATdir;
		// troca a pasta se for estendida, e a posicao se for maior que 9
		posDir = changePosExtendedDir(posDir);

		// joga excecoes se a posicao for nula ou nao for arquivo
		if (dirAtual[posDir] == null) {
			changeDirAtual(oldPosFATdir);
			throw new FileDoesNotExistException();
		}

		if (dirAtual[posDir].getTipo() != 'A') {
			changeDirAtual(oldPosFATdir);
			throw new InvalidCommandException();
		}

		byte[] clusterVazio = new byte[clusterSize];
		int currentCluster = dirAtual[posDir].getCluster1();
		int nextCluster;

		// escreve o cluster vazio nos clusters do arquivo, e atualiza a fat
		for (int i = 0; i <= dirAtual[posDir].getTamanho() / 240; i++) {
			d.writeCluster(currentCluster, clusterVazio);
			nextCluster = vetFAT[currentCluster];
			vetFAT[currentCluster] = 9999;
			currentCluster = nextCluster;
		}

		// apaga o arquivo da dir e atualiza diratual e fat
		apagaDaDir(posDir);
		atualizaDirAtualNoDisco();
		atualizaFATnoDisco();

		changeDirAtual(oldPosFATdir);
	}

	public void removePasta(int posDir) throws DirDoesNotExistException, FileDoesNotExistException, InvalidCommandException {
		int oldPosFATdir = currentPosFATdir;

		posDir = changePosExtendedDir(posDir);

		// joga excecoes se posicao for nula ou nao for diretorio
		if (dirAtual[posDir] == null) {
			changeDirAtual(oldPosFATdir);
			throw new DirDoesNotExistException();
		}

		if (dirAtual[posDir].getTipo() != 'D') {
			changeDirAtual(oldPosFATdir);
			throw new InvalidCommandException();
		}

		int clusterPasta = dirAtual[posDir].getCluster1(); // pega o cluster da pasta
		apagaDaDir(posDir); // apaga do diretorio

		// muda para o diretorio da pasta a ser apagada e apaga o conteudo
		changeDirAtual(clusterPasta);
		boolean temMaisDiretorios = true;
		while (temMaisDiretorios) {
			for (int i = 0; i < dirAtual.length; i++) {
				if (dirAtual[i] != null) {
					if (dirAtual[i].getTipo() == 'A')
						removeArquivo(i);
					else
						removePasta(i);
				}
			}

			// se a pasta for estendida, muda o diratual pra proxima e repete o loop
			if (vetFAT[currentPosFATdir] != 0) {
				int prevPosDir = currentPosFATdir;
				changeDirAtual(vetFAT[currentPosFATdir]);
				vetFAT[prevPosDir] = 9999;
			} else { // caso nao for, quebra o loop
				temMaisDiretorios = false;
			}
		}
		
		vetFAT[currentPosFATdir] = 9999;
		atualizaDirAtualNoDisco();
		changeDirAtual(oldPosFATdir);
		atualizaFATnoDisco();
	}

	private void apagaDaDir(int pos) {
		// apaga o diretorio da diratual
		int oldPosFATdir = currentPosFATdir;

		// coloca os proximos elementos nas posicoes anteriores
		for (int i = pos; i < dirAtual.length - 1; i++)
			dirAtual[i] = dirAtual[i + 1];

		atualizaDirAtualNoDisco();

		// caso a pasta for estendida, muda pra pasta e faz o mesmo metodo
		if (vetFAT[currentPosFATdir] != 0) {
			changeDirAtual(vetFAT[currentPosFATdir]);
			DirEntry dir0ext = dirAtual[0];

			if (dir0ext == null) { // se for nulo, muda a fat e exclui a pasta
				vetFAT[currentPosFATdir] = 9999;
				changeDirAtual(oldPosFATdir);
				vetFAT[currentPosFATdir] = 0;
			} else { // caso tenha algo, apaga o 0 (que vai para o 9 anterior) e muda todas as
						// posicoes para - 1
				apagaDaDir(0);
				changeDirAtual(oldPosFATdir);
			}

			dirAtual[9] = dir0ext;
		} else {
			dirAtual[9] = null;
		}

		atualizaFATnoDisco();
		atualizaDirAtualNoDisco();
	}

	// -----ALTERA DIRETÓRIO-----
	public void entraPasta(int posDir) throws DirDoesNotExistException, InvalidCommandException {
		stackPosFATdir.push(currentPosFATdir); // coloca a posfatdir atual na stack, para quando precisar voltar

		posDir = changePosExtendedDir(posDir);

		if (posDir < 0 || dirAtual[posDir] == null)
			throw new DirDoesNotExistException();
		if (dirAtual[posDir].getTipo() != 'D')
			throw new InvalidCommandException();

		changeDirAtual(dirAtual[posDir].getCluster1());
	}

	public void voltaPasta() {
		if (stackPosFATdir.isEmpty()) // se está no diretório raiz
			System.out.println("ERRO: não há pasta pai para o diretório raiz");
		else // caso não, volta ao diretorio no topo da stack
			changeDirAtual(stackPosFATdir.pop());
	}

	public boolean pastaPorPath(String path, boolean changeStack)
			throws DirDoesNotExistException, InvalidCommandException {
		int oldPosFATdir = currentPosFATdir;
		String[] paths = path.split("/"); // separa o path
		changeDirAtual(20); // vai ao diretorio raiz para comecar procurando pelas pastas
		if (changeStack) // se o método for usado para continuar na pasta do path, coloca as posicoes na
							// stack
			stackPosFATdir.clear();

		loop: for (int iPath = 1; iPath < paths.length; iPath++) { // procura cada pasta do path separado
			boolean temMaisDiretorios = true;

			while (temMaisDiretorios) {
				for (int iDir = 0; iDir < dirAtual.length; iDir++) {
					DirEntry dirEnt = dirAtual[iDir];

					if (dirEnt != null && dirEnt.getTipo() == 'D' && dirEnt.getNome().equalsIgnoreCase(paths[iPath])) {
						// se achou, entra na pasta (se for ficar nela, mudando a stack) ou apenas muda
						// o diretório
						if (changeStack)
							entraPasta(iDir);
						else
							changeDirAtual(dirAtual[iDir].getCluster1());

						continue loop;
					}
				}

				// se a pasta for estendida, muda para a mesma e continua o loop
				if (vetFAT[currentPosFATdir] != 0) {
					changeDirAtual(vetFAT[currentPosFATdir]);
				} else {
					temMaisDiretorios = false;
				}
			}

			// imprime caso não ache a pasta do path, e volta para o diretorio que estava
			System.out.println("ERRO: pasta não existe");
			changeDirAtual(oldPosFATdir);
			return false;
		}

		return true;
	}

	public void rootDir() {
		// volta ao diretorio raiz
		stackPosFATdir.clear();
		changeDirAtual(20);
	}

	// -----COPIA-----
	public void copiaArquivo(int posDir, String path)
			throws FileDoesNotExistException, InvalidCommandException, DirDoesNotExistException {
		DirEntry arquivo = leArquivo(posDir);

		if (arquivo == null)
			throw new FileDoesNotExistException();
		if (arquivo.getTipo() != 'A')
			throw new InvalidCommandException();

		int oldPosFATdir = currentPosFATdir;
		int clusterWritten = getNextCluster(); // procura o proximo cluster para ser escrito
		int totClusters = (arquivo.getTamanho() / 240) + 1;

		pastaPorPath(path, false); // vai para a pasta do path do parametro

		if (verificaNome(arquivo.getNomeExt(), 'A') != -1) { // cancela a copia se já existir um arquivo com o mesmo
																// nome
			System.out.println("ERRO: já existe um arquivo neste diretório com o mesmo nome");
			clearBuffer();
			changeDirAtual(oldPosFATdir);
			return;
		}

		// escreve o arquivo do buffer no cluster
		writeBufferOnDisk(clusterWritten, totClusters, false);
		atualizaFATnoDisco();

		// adiciona o novo arquivo na diratual
		addDirEntry(arquivo.getNome(), arquivo.getExt(), 'A', clusterWritten, arquivo.getTamanho(), -1);

		clearBuffer();
		changeDirAtual(oldPosFATdir);
	}

	public void copiaPasta(int posDir, String path)
			throws DirDoesNotExistException, InvalidCommandException, FileDoesNotExistException {
		int oldPosFATdir = currentPosFATdir;

		// troca a diratual/posdir se a pasta for estendida
		posDir = changePosExtendedDir(posDir);

		if (posDir < 0 || dirAtual[posDir] == null)
			throw new DirDoesNotExistException();
		if (dirAtual[posDir].getTipo() != 'D')
			throw new InvalidCommandException();

		DirEntry pasta = dirAtual[posDir];

		// entra na pasta do path e cria uma nova pasta lá
		pastaPorPath(path, false);

		if (verificaNome(pasta.getNome(), 'D') != -1) { // cancela a copia se já existir uma pasta com o mesmo nome
			System.out.println("ERRO: já existe uma pasta neste diretório com o mesmo nome");
			changeDirAtual(oldPosFATdir);
			return;
		}

		// caso não tenha, cria uma nova pasta e passa o diratual para ela
		novoDir(pasta.getNome());
		changeDirAtual(pasta.getCluster1());

		// string do path da nova pasta, para os arquivos/pastas serem copiados
		String pathNovaPasta = path + "/" + pasta.getNome();

		boolean temMaisDiretorios = true;

		while (temMaisDiretorios) {
			for (int i = 0; i < dirAtual.length; i++) {
				if (dirAtual[i] != null) {
					if (dirAtual[i].getTipo() == 'A') // se nao for nulo e for arquivo, chama o copiaarquivo()
						copiaArquivo(i, pathNovaPasta);
					else
						copiaPasta(i, pathNovaPasta); // se for pasta, chama o copiapasta novamente, mas com a nova
														// pasta
				}
			}

			// se a pasta for estendida, passa a posicao do diretorio estendido e continua o
			// loop
			if (vetFAT[currentPosFATdir] != 0)
				changeDirAtual(vetFAT[currentPosFATdir]);
			else
				temMaisDiretorios = false;
		}

		changeDirAtual(oldPosFATdir);
		atualizaDirAtualNoDisco();
		atualizaFATnoDisco();
	}

	// -----VERIFICACAO DE SISTEMA-----
	public void verificaSistema() {
		int totBytesLivres = 0;
		int totClustersAlocados = 0;
		int totPastas = 0;
		int totArquivos = 0;

		for (int i = 0; i < vetFAT.length; i++) {
			if (vetFAT[i] != 9999)
				totClustersAlocados++;
		}

		totBytesLivres = totClustersAlocados * 240;

		// array de int com 2 elementos (0 = arquivos e 1 = pastas)
		int[] arqDirs = new int[2];

		// contar arquivos/pastas comecando pela raiz
		arqDirs = contaArqDirs(arqDirs, 20);

		totArquivos = arqDirs[0];
		totPastas = arqDirs[1];

		// imprime os dados
		System.out.println("\nVerificação do status do sistema");
		System.out.println(divisoria);

		System.out.printf(" %6d/%6d bytes livres\n", totBytesLivres, (clusterSize * quantClusters));
		System.out.printf(" %8d/%4d clusters alocados\n", totClustersAlocados, quantClusters);
		System.out.printf(" %13d arquivo(s)\n", totArquivos);
		System.out.printf(" %13d pasta(s)\n\n", totPastas);
	}

	private int[] contaArqDirs(int[] arqDirs, int posFATdirAtual) {
		int oldPosFATdir = currentPosFATdir;
		changeDirAtual(posFATdirAtual);

		for (int i = 0; i < dirAtual.length; i++) { // conta os arquivos/pastas
			if (dirAtual[i] != null && dirAtual[i].getTipo() == 'A')
				arqDirs[0]++;
			else if (dirAtual[i] != null && dirAtual[i].getTipo() == 'D') {
				arqDirs[1]++;
				arqDirs = contaArqDirs(arqDirs, dirAtual[i].getCluster1()); // chama o mesmo metodo mas nessa pasta
			}
		}

		if (vetFAT[posFATdirAtual] != 0)
			contaArqDirs(arqDirs, vetFAT[posFATdirAtual]);

		changeDirAtual(oldPosFATdir);
		return arqDirs;
	}

	// ----------
	public String getPathAtual() {
		// pega o path atual para imprimir no console antes dos comandos
		int oldPosFATdir = currentPosFATdir;
		stackPosFATdir.push(currentPosFATdir);
		String path = "F:";

		// o path é pelas posicoes da fat
		loop: for (int i = 0; i < stackPosFATdir.size() - 1; i++) {
			changeDirAtual(stackPosFATdir.get(i));

			boolean temMaisDiretorios = true;
			while (temMaisDiretorios) {
				for (DirEntry d : dirAtual) { // procura o diretorio com o mesmo cluster que o cluster seguinte da stack
					if (d != null && d.getCluster1() == stackPosFATdir.get(i + 1)) {
						path += "/" + d.getNome(); // se achou, junta os paths
						continue loop;
					}
				}

				// caso a diratual seja estendida
				if (vetFAT[currentPosFATdir] != 0)
					changeDirAtual(vetFAT[currentPosFATdir]);
				else
					temMaisDiretorios = false;
			}
		}

		stackPosFATdir.pop();
		changeDirAtual(oldPosFATdir);
		return path;
	}

	public int getPosByName(String nome) {
		// retorna a posicao na diratual (ou estendida) do nome do parametro
		String[] nomeExt = nome.split("\\."); // separa o nome da extensao
		if (nomeExt.length >= 2) // se tiver extensao, é arquivo
			return verificaNome(nome, 'A');
		else // se não tiver, é diretorio
			return verificaNome(nome, 'D');
	}

	public void fechaDisco() {
		// fecha o disco
		d.closeDisk();
	}

	public void clearDisk() {
		// restaura o disco, excluindo tudo
		d.clearDisk();
		inicializaDV();
		dirAtual = new DirEntry[10];
		currentPosFATdir = 20;
		stackPosFATdir.clear();
	}
}
