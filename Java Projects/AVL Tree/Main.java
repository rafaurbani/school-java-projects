import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		AVL avl = new AVL();
		try (Scanner in = new Scanner(System.in)) {
			MENU: while (true) {
				String command = in.nextLine();
				switch (command.toUpperCase()) {
					case "SEARCH" -> {
						System.out.println("Qual elemento você quer procurar?");
						int element = in.nextInt();
						avl.search(element);
						in.nextLine();
					}
					case "INSERT" -> {
						System.out.println("Qual elemento você quer inserir?");
						int element = in.nextInt();
						avl.insert(element);
						in.nextLine();
					}
					case "REMOVE" -> {
						System.out.println("Qual elemento você quer remover?");
						int element = in.nextInt();
						avl.remove(element);
						in.nextLine();
					}
					case "ORDER" -> {
						System.out.println("Em qual ordem você quer ver a árvore?");
						System.out.println("  1. Pré-ordem\n  2. Pós-ordem\n  3. Em ordem");
						int element = in.nextInt();
						while (element < 1 || element > 3) {
							System.out.println("Opção inválida! Digite novamente");
							element = in.nextInt();
						}
						avl.order(element);
						in.nextLine();
					}
					case "HELP" -> {
						System.out.println("SEARCH  Busca o nodo do elemento desejado");
						System.out.println("INSERT  Insere um novo nodo do elemento desejado");
						System.out.println("REMOVE  Remove o nodo do elemento desejado");
						System.out.println("ORDER   Imprime a árvore na ordem selecionada");
						System.out.println("CLOSE   Fecha a aplicação");
					}
					case "CLOSE" -> {
						break MENU;
					}
					default -> System.out.println("Comando inválido! Digite novamente");
				}
			}
		}
	}
}
