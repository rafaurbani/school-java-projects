import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MatrizEsparsa {
	private Celula[] matriz;
	private int totalLinhas, totalColunas;
	private int naoZeros;
	
	//construtor da matriz esparsa (se 1% das células for menor que 10, cria um array de tamanho 10)
	public MatrizEsparsa(int maxLin, int maxCol) {
		int tamanhoArray = (maxLin * maxCol) / 100;
		if (tamanhoArray < 10)
			tamanhoArray = 10;
		this.matriz = new Celula[tamanhoArray];
		this.totalLinhas = maxLin;
		this.totalColunas = maxCol;
		this.naoZeros = 0;
	}
	
	//get para uma célula (corre todos os elementos do array até achar, se não retorna 0)
	public double get(int numLinha, int numColuna) {
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null &&
				this.matriz[i].getPosLinha() == numLinha && 
				this.matriz[i].getPosColuna() == numColuna) {
				return this.matriz[i].getValor();
			}
		}
		
		return 0.0;
	}
	
	//set para uma célula
	public void set(double valor, int numLinha, int numColuna) {
		//primeiro verifica se já existe um valor na célula
		//se tiver, troca o valor. se o valor for 0, remove a celula do array
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null &&
				this.matriz[i].getPosLinha() == numLinha && 
				this.matriz[i].getPosColuna() == numColuna) {
				
				if (valor == 0.0) {
					this.matriz[i] = null;
					this.naoZeros--;
				} else
					this.matriz[i].setValor(valor);
				
				return;
			}
		}
		
		//se não existir, cria uma nova
		//try e catch para que se ultrapassar a porcentagem de células máximas, joga a exceção de tamanho máximo 
		try {
			int tamanhoMaximo = (this.totalColunas * this.totalLinhas) / 100;
			
			if (valor != 0.0 && this.naoZeros >= tamanhoMaximo) {
				throw new MatrixFullException(1);
			}
			
			//se não jogar a exceção, cria uma nova célula na primeira posicao nula do array
			for (int i = 0; i < this.matriz.length; i++) {
				if (this.matriz[i] == null) {
					this.matriz[i] = new Celula(valor, numLinha, numColuna);
					this.naoZeros++;
					return;
				}
			}
		} catch (MatrixFullException e) {
			System.out.println(e);
		}
	}
	
	//método que soma todos os elementos da matriz e retorna o valor
	public double soma() {
		double soma = 0.0;
		
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null)
				soma += this.matriz[i].getValor();
		}
		
		return soma;
	}
	
	//método que procura o menor valor da array e retorna ele
	public double min() {
		double min = 0.0;
		
		//procura o menor número comparando ao min, ou se min == 0.0, coloca o primeiro número da array diferente de 0
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null && 
				this.matriz[i].getValor() != 0.0 && 
				(this.matriz[i].getValor() < min || min == 0.0)) {
				
				min = this.matriz[i].getValor();
			}
		}
		
		return min;
	}
	
	//método que procura o maior valor da array e retorna ele
	public double max() {
		double max = 0.0;
		
		//procura o maior número comparando ao max, ou se max == 0.0, coloca o primeiro número da array diferente de 0
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null && 
				this.matriz[i].getValor() != 0.0 && 
				(this.matriz[i].getValor() > max || max == 0.0)) {
					max = this.matriz[i].getValor();
			}
		}
		
		return max;
	}
	
	//método que verifica se existe uma célula com o valor do parâmetro
	//retorna true se existir, e false se não existir
	public boolean exist(double valor) {
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i].getValor() == valor)
				return true;
		}
		
		return false;
	}
	
	//método que retorna a média dos valores da coluna diferente de 0
	public double coluna(int numCol) {
		double media = 0.0;
		int contador = 0;
		
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null && this.matriz[i].getPosColuna() == numCol) {
				media += this.matriz[i].getValor();
				contador++;
			}
		}
		
		//se o contador for 0, a exceção retorna 0, se não retorna a média
		try {
			media /= contador;
			return media;
		} catch (ArithmeticException e) {
			return 0.0;
		}
	}
	
	//método que retorna a média dos valores da linha diferente de 0
	public double linha(int numLin) {
		double media = 0.0;
		int contador = 0;
		
		for (int i = 0; i < this.matriz.length; i++) {
			if (this.matriz[i] != null && this.matriz[i].getPosLinha() == numLin) {
				media += this.matriz[i].getValor();
				contador++;
			}
		}
		
		//se o contador for 0, a exceção retorna 0, se não retorna a média
		try {
			media /= contador;
			return media;
		} catch (ArithmeticException e) {
			return 0.0;
		}
	}
	
	//método que retorna uma matriz de doubles dos valores da matriz esparsa da região dos parâmetros
	public double[][] copia(int primLin, int primCol, int quantasLinhas, int quantasColunas) {
		double[][] copiaMatriz = new double[quantasLinhas][quantasColunas];
				
		for (int i = 0, l = primLin; i < copiaMatriz.length; i++, l++) {
			for (int j = 0, c = primCol; j < copiaMatriz[i].length; j++, c++) {
				if (get(l, c) != 0.0)
					copiaMatriz[i][j] = get(l, c);
				else
					copiaMatriz[i][j] = 0.0;
			}
		}
		
		return copiaMatriz;
	} 
	
	//imprime a matriz inteira, usando o método copia() e todas as linhas e colunas da matriz
	public void imprimeMatriz() {
		double[][] matriz = copia(0, 0, this.totalLinhas, this.totalColunas);
				
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.printf("%7.2f ", matriz[i][j]);
			}
			System.out.println();
		}
	}
	
	//retorna uma nova matriz esparsa, sendo a matriz transposta da matriz do parâmetro
	public MatrizEsparsa transposta(MatrizEsparsa m) {
		MatrizEsparsa novaMatriz = new MatrizEsparsa(m.getTotalColunas(), m.getTotalLinhas());
		
		//inverte as posições (linha vira coluna e coluna vira linha)
		for (int i = 0; i < m.matriz.length; i++) {
			if (m.matriz[i] != null) {
				novaMatriz.set(m.matriz[i].getValor(), m.matriz[i].getPosColuna(), m.matriz[i].getPosLinha());
			}
		}
		
		return novaMatriz;
		
	} 
	
	//método que armazena as células da matriz esparsa em um arquivo
	public void armazena(String nomeArquivo) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
			for (int i = 0; i < this.matriz.length; i++) {
				if (this.matriz[i] != null) {
					os.writeObject((Object)this.matriz[i]);
				}
			}	
		} catch (IOException e) {
			System.out.println("Erro na gravação do arquivo: " + e);
		}
	}
	
	//método que carrega a matriz esparsa do arquivo do parâmetro
	public void carrega(String nomeArquivo) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
			
			Object celula = null;			
			int totCelulas = 0;
			
			while ((celula = ois.readObject()) != null) {
				if (this.matriz[totCelulas] == null)
					this.matriz[totCelulas++] = (Celula)celula;
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Erro na leitura do arquivo - Classe não encontrada");
		} catch (EOFException e) {
			System.out.println("Arquivo lido com sucesso até o fim");
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo " + e);
		}
	}
	
	//getters e setters
	public Celula[] getMatriz() {
		return matriz;
	}

	public void setMatriz(Celula[] matriz) {
		this.matriz = matriz;
	}

	public int getTotalLinhas() {
		return totalLinhas;
	}

	public int getTotalColunas() {
		return totalColunas;
	}

	public int getNaoZeros() {
		return naoZeros;
	}

	public void setNaoZeros(int naoZeros) {
		this.naoZeros = naoZeros;
	}
}
