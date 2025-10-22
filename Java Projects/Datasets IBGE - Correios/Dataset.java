import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.util.Scanner;

public class Dataset {
	private DLinkedList<Estado> estados;
	private DLinkedList<Municipio> municipios;
	private DLinkedList<Bairro> bairros;
	private DLinkedList<Logradouro> logradouros;
	
	public Dataset() {
		estados = new DLinkedList<Estado>();
		municipios = new DLinkedList<Municipio>();
		bairros = new DLinkedList<Bairro>();
		logradouros = new DLinkedList<Logradouro>();
	}
	
	public void leDataset() {
		Scanner scan = new Scanner(System.in);
		String divisor = "--------------------------------------";
		
		leEstados();
		System.out.println(estados);
		System.out.println(divisor);
		System.out.println("Escolha o estado pelo ID ou UF:");
		
		leMunicipios(returnCod(1, scan.nextLine()));
		System.out.println(municipios);
		System.out.println(divisor);
		System.out.println("Escolha a cidade pelo ID ou nome:");
		
		leBairros(returnCod(2, scan.nextLine()));
		System.out.println(bairros);
		System.out.println(divisor);
		System.out.println("Escolha o bairro pelo ID ou nome:");
		long codBairroEscolhido = returnCodBairro(scan.nextLine());
		
		DLinkedIterator<Bairro> itBairros = new DLinkedIterator<Bairro>(bairros);
		Bairro bairroEscolhido = itBairros.current();
		
		while(bairroEscolhido.getCodBairro() != codBairroEscolhido) {
			bairroEscolhido = itBairros.next();
		}
		
		leLogradouros(bairroEscolhido);
		
		scan.close();
	}
	
	public void leEstados() {
		try (BufferedReader in = new BufferedReader(new FileReader("estados.csv"))) {
			String linha = in.readLine();
			while ((linha = in.readLine()) != null) {
				adicionaEstado(linha);
			}
		} catch (EOFException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaEstado(String linha) throws OverflowException {
		String[] novoEstado = linha.split(",");
		
		int codUf = Integer.parseInt(novoEstado[1]);
		String nome = novoEstado[2];
		String uf = novoEstado[3];
		int codRegiao = Integer.parseInt(novoEstado[4]);
		
		estados.addEnd(new Estado(codUf, nome, uf, codRegiao));
	}
	
	public void leMunicipios(int ufEscolhido) {
		
		try (BufferedReader in = new BufferedReader(new FileReader("municipios.csv"))) {
					
			String linha = in.readLine();
			while ((linha = in.readLine()) != null) {
				adicionaMunicipio(linha, ufEscolhido);
			}
		} catch (EOFException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaMunicipio(String linha, int ufEscolhido) throws OverflowException {
		String[] novoMunicipio = linha.split(",");
		
		int codEstado = Integer.parseInt(novoMunicipio[1].substring(0, 2));
		int codMunicipio = Integer.parseInt(novoMunicipio[1]);
		String nome = novoMunicipio[2];
		String uf = novoMunicipio[3];
		
		if (ufEscolhido == codEstado)
			municipios.addEnd(new Municipio(codMunicipio, nome, uf));
	}
	
	public void leBairros(int munEscolhido) {
		try (BufferedReader in = new BufferedReader(new FileReader("bairros.csv"))) {
			String linha = in.readLine();
			while ((linha = in.readLine()) != null) {
				adicionaBairro(linha, munEscolhido);
			}
		} catch (EOFException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaBairro(String linha, int munEscolhido) throws OverflowException {
		String[] novoBairro = linha.split(",");
		String[] nomes = novoBairro[2].split(" - ");
		
		int codMunicipio = Integer.parseInt(novoBairro[1].substring(0, 8));
		long codBairro = Long.parseLong(novoBairro[1]);
		String nomeBairro = nomes[0];
		String nomeMunicipio = nomes[1];
		String uf = novoBairro[3];
		
		if (munEscolhido == codMunicipio)
			bairros.addEnd(new Bairro(codMunicipio, codBairro, nomeBairro, nomeMunicipio, uf));
	}
	
	public void leLogradouros(Bairro bairroEscolhido) {
		try (BufferedReader in = new BufferedReader(new FileReader("cep.csv"))) {
			String linha;
			while ((linha = in.readLine()) != null) {
				adicionaLogradouros(linha, bairroEscolhido);
			}
		} catch (EOFException e) {
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adicionaLogradouros(String linha, Bairro bairroEscolhido) throws OverflowException {
		String[] novoLogradouro = linha.split(";");
		
		String uf = novoLogradouro[0];
		String municipio = novoLogradouro[1];
		String bairro = novoLogradouro[2];
		int cep = Integer.parseInt(novoLogradouro[3]);
		String nome = novoLogradouro[4];
		
		if (bairroEscolhido.getUf().equals(uf) &&
			bairroEscolhido.getNomeMunicipio().equals(municipio) &&
			bairroEscolhido.getNomeBairro().equals(bairro)) {
			logradouros.addEnd(new Logradouro(uf, municipio, bairro, cep, nome));
		}
	}
	
	public int returnCod(int tipo, String nome) {
		try {
			int cod = Integer.parseInt(nome);
			return cod;
		} catch (NumberFormatException e) {			
			switch(tipo) {
			case 1: //estado
				DLinkedIterator<Estado> itEstado = new DLinkedIterator<Estado>(estados);
				Estado estado = itEstado.current();
				
				while (!(estado.getUf().equalsIgnoreCase(nome))) {
					estado = itEstado.next();
				}
				
				return estado.getCodUf();
			case 2: //municipio
				DLinkedIterator<Municipio> itMun = new DLinkedIterator<Municipio>(municipios);
				Municipio municipio = itMun.current();
				
				while (!(municipio.getNome().equalsIgnoreCase(nome))) {
					municipio = itMun.next();
				}
				
				return municipio.getCodMunicipio();				
			default:
				return -1;
			}
		}
	}
	
	public long returnCodBairro(String nome) {
		try {
			long cod = Long.parseLong(nome);
			return cod;
		} catch (NumberFormatException e) {		
			DLinkedIterator<Bairro> itBairro = new DLinkedIterator<Bairro>(bairros);
			Bairro bairro = itBairro.current();
			
			while (!(bairro.getNomeBairro().equalsIgnoreCase(nome))) {
				bairro = itBairro.next();
			}
		
			return bairro.getCodBairro();
		}
	}
}
