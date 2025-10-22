import java.io.Serializable;

@SuppressWarnings("serial")
public class Celula implements Serializable {
	//classe da célula individual, com valor, posicao da linha e posicao da coluna
	public double valor;
	public int posLinha;
	public int posColuna;
	
	public Celula(double valor, int posLinha, int posColuna) {
		this.valor = valor;
		this.posLinha = posLinha;
		this.posColuna = posColuna;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getPosLinha() {
		return posLinha;
	}

	public void setPosLinha(int posLinha) {
		this.posLinha = posLinha;
	}

	public int getPosColuna() {
		return posColuna;
	}

	public void setPosColuna(int posColuna) {
		this.posColuna = posColuna;
	}
}
