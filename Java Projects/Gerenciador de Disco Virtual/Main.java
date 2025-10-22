import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		DiscoVirtual dv = new DiscoVirtual();
		Scanner in = new Scanner(System.in);
		
		dv.inicializaDV();
		
		while (true) {
			System.out.printf("%s>", dv.getPathAtual()); // imprime o path atual
			String comando = in.nextLine();
	
			if (comando.equalsIgnoreCase("CLOSE")) // se o comando for close, quebra o while e fecha o disco
				break;
	
			if (comando.equalsIgnoreCase("CLEAR")) { // se o comando for clear para limpar o disco, continua o ciclo
				System.out.println("ATENÇÃO: todos os arquivos serão perdidos");
				System.out.println("Para restaurar o disco, digite CLEAR novamente");
				if (in.nextLine().equalsIgnoreCase("CLEAR"))
					dv.clearDisk();
				else
					System.out.println("Restauração cancelada");
				continue;
			}
	
			menu(dv, comando);
		}

		in.close();
		dv.fechaDisco();

	}
	
	public static void menu(DiscoVirtual dv, String comando) {
		String[] comandos = comando.split(" "); // divide o comando
		
		try {
			switch (comandos[0].toUpperCase()) {
			case "NEW":
				if (comandos.length > 2 // se o comando for maior que 2 e segue com file ou dir
						&& (comandos[1].equalsIgnoreCase("FILE") || comandos[1].equalsIgnoreCase("DIR"))) {
					if (comandos.length == 4 && comandos[1].equalsIgnoreCase("FILE"))
						dv.novoArquivo(comandos[2], comandos[3]); // new file precisa de dois elementos
					else if (comandos.length == 3 && comandos[1].equalsIgnoreCase("DIR")) // new dir precisa de um
						dv.novoDir(comandos[2]);
					else
						throw new InvalidCommandException(); // joga excecao se nao atender aos requisitos
				} else
					throw new InvalidCommandException();
				break;

			case "FILE":
				if (comandos.length != 2)
					throw new InvalidCommandException();
				if (comandos.length == 2) {
					int pos;

					try {
						pos = Integer.parseInt(comandos[1]); // se o comando for de posicao, transforma em int
					} catch (NumberFormatException e) {
						pos = dv.getPosByName(comandos[1]); // se nao for, procura a pos pelo nome
					}

					dv.visualizaArquivo(pos);
				}
				break;

			case "DIR":
				if (comandos.length > 2)
					throw new InvalidCommandException();

				if (comandos.length > 1) {
					int pos;

					try {
						pos = Integer.parseInt(comandos[1]); // se for por posicao, transforma em int
					} catch (NumberFormatException e) {
						if (comandos[1].startsWith("/")) { // se for por path, visualiza a pasta pelo path
							dv.visualizaPasta(comandos[1]);
							System.out.println();
							break;
						}

						pos = dv.getPosByName(comandos[1]); // se for pelo nome, procura a pos pelo nome
					}
					if (pos == -1)
						throw new InvalidCommandException();
					dv.visualizaPasta(pos);
				} else
					dv.visualizaPasta(-1);
				break;

			case "PRINT":
				if (comandos.length == 2) {
					int pos;
					try {
						pos = Integer.parseInt(comandos[1]);
					} catch (NumberFormatException e) {
						pos = dv.getPosByName(comandos[1]);
					}
					if (pos == -1)
						throw new InvalidCommandException();
					dv.imprimeArquivo(pos);
				}

				System.out.println();
				break;

			case "CD":
				if (comandos.length == 2) {
					int pos;

					try {
						pos = Integer.parseInt(comandos[1]);
					} catch (NumberFormatException e) {
						if (comandos[1].startsWith("/")) {
							dv.pastaPorPath(comandos[1], true);
							break;
						}
						pos = dv.getPosByName(comandos[1]);
					}
					if (pos == -1)
						throw new DirDoesNotExistException();
					dv.entraPasta(pos);
				} else
					throw new InvalidCommandException();
				break;

			case "BACK":
				if (comandos.length > 2)
					throw new InvalidCommandException();
				else if (comandos.length == 2 && comandos[1].equals("ROOT")) // se o comando for BACK ROOT, volta ao
																				// diretorio raiz
					dv.rootDir();
				else
					dv.voltaPasta();
				break;

			case "REMOVE":
				if (comandos.length == 3
						&& (comandos[1].equalsIgnoreCase("FILE") || comandos[1].equalsIgnoreCase("DIR"))) {
					int pos;
					if (comandos[1].equalsIgnoreCase("FILE")) {
						try {
							pos = Integer.parseInt(comandos[2]);
						} catch (NumberFormatException e) {
							pos = dv.getPosByName(comandos[2]);
						}

						if (pos < 0)
							throw new InvalidCommandException();
						dv.removeArquivo(pos);
					} else if (comandos[1].equalsIgnoreCase("DIR")) {
						try {
							pos = Integer.parseInt(comandos[2]);
						} catch (NumberFormatException e) {
							pos = dv.getPosByName(comandos[2]);
						}

						if (pos < 0)
							throw new InvalidCommandException();
						dv.removePasta(pos);
					}
				} else
					throw new InvalidCommandException();
				break;

			case "COPY":
				if (comandos.length == 4
						&& (comandos[1].equalsIgnoreCase("FILE") || comandos[1].equalsIgnoreCase("DIR"))
						&& comandos[3].startsWith("/")) {

					int pos;
					if (comandos[1].equalsIgnoreCase("FILE")) {
						try {
							pos = Integer.parseInt(comandos[2]);
						} catch (NumberFormatException e) {
							pos = dv.getPosByName(comandos[2]);
						}
						if (pos < 0)
							throw new InvalidCommandException();

						dv.copiaArquivo(pos, comandos[3]);
					} else if (comandos[1].equalsIgnoreCase("DIR")) {
						try {
							pos = Integer.parseInt(comandos[2]);
						} catch (NumberFormatException e) {
							pos = dv.getPosByName(comandos[2]);
						}
						if (pos < 0)
							throw new InvalidCommandException();

						dv.copiaPasta(pos, comandos[3]);
					}
				} else
					throw new InvalidCommandException();
				break;

			case "SYSTEM":
				if (comandos.length != 1)
					throw new InvalidCommandException();
				dv.verificaSistema();
				break;
			case "HELP":
				if (comandos.length > 2)
					throw new InvalidCommandException();
				help(comando.toUpperCase());
				break;
			default:
				throw new InvalidCommandException();
			}
		} catch (InvalidCommandException | DirDoesNotExistException | FileDoesNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void help(String comando) {
		String[] comandos = comando.split(" ");
		boolean generalHelp = true; //generalhelp faz imprimir todos os textos de ajuda

		// se o comando for dividido em dois, passa a segunda palavra 
		// para a primeira posicao (que sempre é help)
		if (comandos.length == 2) {
			if (comandos[1].equals("HELP"))
				comandos[1] = "error";

			comandos[0] = comandos[1];
			generalHelp = false; //também desativa a ajuda geral
		}

		try {
			switch (comandos[0]) {
			case "HELP":
			case "NEW":
				System.out.println("NEW     Cria um novo arquivo ou pasta no diretório atual");
				System.out.println("        + FILE nome arquivo (arquivo fora do disco virtual)");
				System.out.println("        + DIR nome ");
				if (!generalHelp)
					break;
			case "FILE":
				System.out.println("FILE    Visualiza dados do arquivo");
				System.out.println("        + pos");
				System.out.println("        + nome");
				if (!generalHelp)
					break;
			case "DIR":
				System.out.println("DIR     Visualiza a pasta atual ou a pasta do parâmetro");
				System.out.println("        + pos");
				System.out.println("        + nome");
				System.out.println("        + path");
				if (!generalHelp)
					break;
			case "PRINT":
				System.out.println("PRINT   Imprime arquivo de texto");
				System.out.println("        + pos");
				System.out.println("        + nome");
				if (!generalHelp)
					break;
			case "CD":
				System.out.println("CD      Muda o diretório atual para o diretório do parâmetro");
				System.out.println("        + pos");
				System.out.println("        + nome");
				System.out.println("        + path");
				if (!generalHelp)
					break;
			case "BACK":
				System.out.println("BACK    Volta para a pasta pai");
				System.out.println("        + ROOT (Volta ao diretório raiz)");
				if (!generalHelp)
					break;
			case "REMOVE":
				System.out.println("REMOVE  Remove um arquivo ou pasta do diretório atual");
				System.out.println("        + FILE nome");
				System.out.println("        + FILE pos");
				System.out.println("        + DIR nome");
				System.out.println("        + DIR pos ");
				if (!generalHelp)
					break;
			case "COPY":
				System.out.println("COPY    Copia um arquivo ou pasta para outro path");
				System.out.println("        + FILE nome path");
				System.out.println("        + FILE pos path");
				System.out.println("        + DIR nome path");
				System.out.println("        + DIR pos path");
				if (!generalHelp)
					break;
			case "SYSTEM":
				System.out.println("SYSTEM  Visualiza dados de controle do disco virtual");
				if (!generalHelp)
					break;
			case "CLOSE":
				System.out.println("CLOSE   Fecha o disco virtual");
				if (!generalHelp)
					break;
			case "CLEAR":
				System.out.println("CLEAR   Restaura o disco virtual");
				break;
			default:
				throw new InvalidCommandException();
			}
		} catch (InvalidCommandException e) {
			System.out.println(e.getMessage());
		}
	}
}
