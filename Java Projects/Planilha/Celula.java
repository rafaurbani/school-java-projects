import java.io.Serializable;

@SuppressWarnings("serial")
public class Celula implements Serializable {
	private double valor;
	private String string;
	private Formula formula;
	private int linha;
	private int coluna;

	//cria uma célula dependendo do parâmetro, e deixa os outros valores nulos
	public Celula(double v, int l, int c) {
		this.valor = v;
		this.string = null;
		this.formula = null;
		this.linha = l;
		this.coluna = c;
	}

	public Celula(String s, int l, int c) {
		this.valor = 0.0;
		this.string = s;
		this.formula = null;
		this.linha = l;
		this.coluna = c;
	}
	
	public Celula(Formula f, int l, int c) {
		this.valor = 0.0;
		this.string = null;
		this.formula = f;
		this.linha = l;
		this.coluna = c;
	}

	//getters e setters
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
}
