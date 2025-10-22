import java.io.Serializable;

@SuppressWarnings("serial")
public class Formula implements Serializable{
	private char op;
	private int l1, c1, l2, c2;

	//a fórmula só salva as linhas e colunas, e o método resolveFormula que resolve direto na classe Planilha
	public Formula(char op, int l1, int c1, int l2, int c2) {
		super();
		this.op = op;
		this.l1 = l1;
		this.c1 = c1;
		this.l2 = l2;
		this.c2 = c2;
	}

	public char getOp() {
		return op;
	}

	public int getL1() {
		return l1;
	}

	public int getC1() {
		return c1;
	}

	public int getL2() {
		return l2;
	}

	public int getC2() {
		return c2;
	}
	
	
}