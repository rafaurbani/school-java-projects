import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		GameOfLife jogo = new GameOfLife(10, 10);
		
		jogo.setCellAlive(1, 1);
		jogo.setCellAlive(1, 2);
		jogo.setCellAlive(1, 4);
		jogo.setCellAlive(1, 5);
		jogo.setCellAlive(2, 1);
		jogo.setCellAlive(2, 4);
		
		System.out.println(jogo);
		
		for (int i = 0; i < 100; i++) {
			jogo.execCycle();
			System.out.println(jogo);
			s.nextLine();
		}
		
		s.close();

	}

}
