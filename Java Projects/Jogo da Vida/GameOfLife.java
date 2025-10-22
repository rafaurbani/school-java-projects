
public class GameOfLife {
	public char[][] matriz;
	public char[][] novaMatriz;
	
	public GameOfLife(int l, int c) {
		this.matriz = new char[l][c];
		this.novaMatriz = new char[l][c];
				
		for (int i = 0; i < this.matriz.length; i++) {
			for (int j = 0; j < this.matriz[i].length; j++) {
				setCellDead(i, j);
				this.novaMatriz[i][j] = '.';
			}
		}
	}
	
	public void setCellAlive(int l, int c) {
		this.matriz[l][c] = 'X';
	}
	
	public void setCellDead(int l, int c) {
		this.matriz[l][c] = '.';
	}
	
	public int verifyNeighbors(int i, int j) {
			
		int vizinhos = 0;
		
		for (int l = -1; l <= 1; l++) {
			for (int c = -1; c <= 1; c++) {
				if (l == 0 && c == 0)
					continue;
				
				int iTemp = i;
				int jTemp = j;
				
				if (i + l < 0)
					iTemp = this.matriz.length;
				else if (i + l == this.matriz.length) 
					iTemp = -1;
				
				if (j + c < 0)
					jTemp = this.matriz[i].length;
				else if (j + c == this.matriz[i].length)
					jTemp = -1;
				
				if (this.matriz[iTemp + l][jTemp + c] == 'X')
					vizinhos++;
			}
		}
		
		return vizinhos;
	}
	
	public void execCycle() {
		
		for (int i = 0; i < this.matriz.length; i++) {
			for (int j = 0; j < this.matriz[i].length; j++) {
				
				int vizinhos = verifyNeighbors(i, j);
				
				if (this.matriz[i][j] == 'X' &&
				   (vizinhos < 2 || vizinhos > 3))
					novaMatriz[i][j] = '.';
				
				if (this.matriz[i][j] == '.' && vizinhos == 3)
					novaMatriz[i][j] = 'X';
			}
		}
		
		this.matriz = novaMatriz;
	}

	public String toString() {
		String s = "";
		
		for (int i = 0; i < this.matriz.length; i++) {
			for (int j = 0; j < this.matriz[i].length; j++) {
				s += String.valueOf(this.matriz[i][j]) + " ";
			}
			s += "\n";
		}
		
		return s;
	}
	
	
	
}
