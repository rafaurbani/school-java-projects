import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Planilha {
	
	private Celula[] planilha;
	private int linhas;
	private int colunas;

	public Planilha(int l, int c) {
		this.planilha = new Celula[l*c];
		this.linhas = l;
		this.colunas = c;
	}

	//setters para células diferentes para valor, string e fórmula, que usam o setCel de célula
	public void setCel(double valor, int l, int c) {
		setCel(new Celula(valor, l, c));
	}

	public void setCel(String string, int l, int c) {
		setCel(new Celula(string, l, c));
	}

	public void setCel(Formula formula, int l, int c) {
		setCel(new Celula(formula, l, c));
	}

	public void setCel(Celula celula) {
		//primeiro verifica se a célula já existe. se existir, insere o novo valor na célula
		for (int i = 0; i < this.planilha.length; i++) {
			if (this.planilha[i] != null &&
				celula.getLinha() == this.planilha[i].getLinha() &&
				celula.getColuna() == this.planilha[i].getColuna()) {
				
				this.planilha[i].setValor(celula.getValor());
				this.planilha[i].setString(celula.getString());
				this.planilha[i].setFormula(celula.getFormula());
			}
		}
		
		//se não existir, insere a célula na primeira posicao nula da array
		for (int i = 0; i < this.planilha.length; i++) {
			if (this.planilha[i] == null) {
				this.planilha[i] = celula;
				return;
			}
		}
	}

	//getters de célula e de valor/string/formula da célula
	public Celula getCel(int l, int c) {
		//loop para procurar a célula da array com a mesma linha e coluna dos parâmetros
		for (int i = 0; i < this.planilha.length; i++) {
			if (this.planilha[i] != null && this.planilha[i].getLinha() == l && this.planilha[i].getColuna() == c)
				return this.planilha[i];
		}

		return null;
	}

	public double getCelValor(int l, int c) {
		Celula cel = getCel(l, c);
		
		//se não for nulo, retorna o valor, se não retorna nulo
		if (cel != null)
			return cel.getValor();
		else 
			return 0.0;
	}
	
	public String getCelString(int l, int c) {
		Celula cel = getCel(l, c);

		//se não for nulo, retorna a string, se não retorna nulo
		if (cel != null)
			return cel.getString();
		else
			return null;
	}
	
	public Formula getCelFormula(int l, int c) {
		Celula cel = getCel(l, c);

		//se não for nulo, retorna a formula, se não retorna nulo
		if (cel != null)
			return cel.getFormula();
		else
			return null;
	}
	
	//método para resolver a fórmula
	public String resolveFormula(Formula f) {
		try {
			//cria variaveis para as celulas e valores dela 
			Celula cel1 = getCel(f.getL1(), f.getC1());
			Celula cel2 = getCel(f.getL2(), f.getC2());
			double valor1 = cel1.getValor();
			double valor2 = cel2.getValor();
			
			//metodo recursivo para se o valor for 0 e a celula houver uma formula
			if (valor1 == 0.0 && cel1.getFormula() != null)
				valor1 = Double.parseDouble(resolveFormula(cel1.getFormula()));
			
			if (valor2 == 0.0 && cel2.getFormula() != null)
				valor2 = Double.parseDouble(resolveFormula(cel1.getFormula()));		
			
			//switch para diferentes operações
			switch (f.getOp()) {
			case '+':
				return valor1 + valor2 + "";
			case '-':
				return valor1 - valor2 + "";
			case '*':
				return valor1 * valor2 + "";
			case '/':
				return valor1 / valor2 + "";
			default:
				//default e excecao imprime erro e retorna 0
				return "#ERRO!";
			}
		} catch (ArithmeticException e) {
			return "#ERRO!";
		} catch (NullPointerException e) {
			return "#ERRO!";
		} catch (NumberFormatException e) {
			return "#ERRO!";
		}
	}

	//mostra planilha em determinadas linhas e colunas
	public void mostraPlan(int primLin, int primCol, int ultLin, int ultCol) {
		//primeiro imprime o cabeçalho (mostrando as colunas)
		System.out.printf("%-4s | ", " ");
		
		for (int cabCol = primCol; cabCol <= ultCol; cabCol++ ) {
			System.out.printf("%15d | ", cabCol);
		}
		
		//faz a impressão da linha contínua embaixo do cabeçalho
		
		System.out.printf("\n-----|-", " ");
		
		for (int cab2 = primCol; cab2 <= ultCol; cab2++) {
			System.out.printf("----------------|-");
		}
		
		//faz a impressão das células
		for (int i = primLin; i <= ultLin; i++) {
			//número da linha
			System.out.printf("\n%-4d | ", i);
			for (int j = primCol; j <= ultCol; j++) {
				Celula cel = getCel(i, j);
				
				//imprime a célula dependendo do conteúdo, ou deixa vazio se não houver nada na célula
				if (cel == null) 
					System.out.printf("%15s", " ");
				else if (cel.getValor() != 0.0 && cel.getString() != null)
					System.out.printf("%-15s", "#ERRO");
				else if (cel.getValor() != 0.0)
					System.out.printf("%-15.1f", cel.getValor());
				else if (cel.getString() != null)
					System.out.printf("%-15s", cel.getString());
				else if (cel.getFormula() != null)
					System.out.printf("%-15s", resolveFormula(cel.getFormula()));
				
				System.out.printf(" | ");
			}
			
			//impressão das linhas contínuas para separar as linhas
			System.out.printf("\n-----|-", " ");
			
			for (int cab = primCol; cab <= ultCol; cab++) {
				System.out.printf("----------------|-");
			}
		}
		
		System.out.println();
	}
	
	//usa o método mostraPlan para imprimir a planilha completa
	public void imprimePlanCompleta() {
		mostraPlan(1, 1, this.linhas, this.colunas);
	}
	
	//salva a planilha (células como objetos) em um arquivo
	public void salvaPlan(String nomeArquivo) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
			for (int i = 0; i < this.planilha.length; i++) {
				if (this.planilha[i] != null) {
					out.writeObject((Object)this.planilha[i]);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro na gravação do arquivo - " + e);
		}
	}
	
	//lê a planilha do arquivo do parâmetro
	public void lePlanilha(String nomeArquivo) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
			
			limpaPlanilha();
			Object celula = null;
			int totCelulas = 0;
			
			while((celula = in.readObject()) != null) {
				this.planilha[totCelulas++] = (Celula)celula;
			}
			
		} catch (EOFException e) {
			System.out.println("Planilha lida até o final com sucesso!");
		} catch (ClassNotFoundException e) {
			System.out.println("Erro na leitura - classe não encontrada");;
		} catch (IOException e) {
			System.out.println("Erro na leitura - " + e);
		}
	}

	//limpa as células das linhas e colunas determinadas (recursivo)
	public void limpaCels(int linI, int colI, int linF, int colF) {
		if (linI <= linF && colI <= colF) {
			
			Celula cel = getCel(linI, colI);
			if (cel != null) {
				for (int i = 0; i < this.planilha.length; i++) {
					if (this.planilha[i] != null &&
						cel.getLinha() == this.planilha[i].getLinha() &&
						cel.getColuna() == this.planilha[i].getColuna()) {
						
						this.planilha[i] = null;
					}
				}
			}
			
			//inicia a contagem das colunas do começo se chegar no final da coluna
			if (colI == colF) {
				colI = 1;
				linI++;
			} else 
				colI++;

			limpaCels(linI, colI, linF, colF);
		} else {
			System.out.println("\nCélula limpada!\n");
			return;
		}
	}
	
	//usa o método limpaCels para limpar toda a planilha
	public void limpaPlanilha() {
		limpaCels(1, 1, this.linhas, this.colunas);
	}
}
