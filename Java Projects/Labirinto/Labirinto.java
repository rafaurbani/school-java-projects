import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Labirinto {
	private Celula[][] lab;
	private final int linhas = 14;
	private final int colunas = 21;
	
	public Labirinto() {
		this.lab = new Celula[linhas][colunas];
	}
	
	public void leLabirinto() {
		try (BufferedReader in = new BufferedReader(new FileReader("labirinto.txt"))) {
			String linha;
			int totLinhas = 0;
			
			while ((linha = in.readLine()) != null) {
				for (int i = 0; i < colunas; i++) {
					this.lab[totLinhas][i] = new Celula(linha.charAt(i));
				}
				totLinhas++;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void imprimeLabirinto() {
		for (int i = 0; i < this.lab.length; i++) {
			for (int j = 0; j < this.lab[i].length; j++) {
				System.out.printf("%c", this.lab[i][j].getValor());
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private boolean visitado(int x, int y) {
		return this.lab[x][y].isVisitada();
	}
	
	private boolean ehParede(int x, int y) {
		return (this.lab[x][y].getValor() == '#') ? true : false;
	}
	
	private boolean ehSaida(int x, int y) {
		return (this.lab[x][y].getValor() == 'S') ? true : false;
	}
	
	private boolean outOfBounds(int x, int y) {
		return ((x < 0 || x > linhas - 1) || (y < 0 || y > colunas - 1));
	}
	
	private boolean podeSeguir(int x, int y) {
		return (!outOfBounds(x, y) && !ehParede(x, y) && !visitado(x, y));
	}
	
	public boolean procuraSaida(int x, int y) {
		if (podeSeguir(x, y))
			return achaSaida(x, y);
		else
			return false;
	}
	
	public boolean achaSaida(int x, int y) {
		boolean achou = false;
		
		if (!ehSaida(x, y)) {
			lab[x][y].setVisitada(true);
			
			if (podeSeguir(x + 1, y))
				achou = achaSaida(x + 1, y);
			if (!achou && podeSeguir(x - 1, y))
				achou = achaSaida(x - 1, y);
			if (!achou && podeSeguir(x, y + 1))
				achou = achaSaida(x, y + 1);
			if (!achou && podeSeguir(x, y - 1))
				achou = achaSaida(x, y - 1);
		} else {
			System.out.println("Achou a saída em " + x + ", " + y + "\n");
			achou = true;
		}
		
		return achou;
		
	}
}
