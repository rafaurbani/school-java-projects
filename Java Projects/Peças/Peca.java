
public class Peca {
	private String nome;
	private int cod;
	private static int contadorCod = 1;
	private int unidsEmEstoque;
	private double precoUnit;
	
	public Peca(String nome, int unidsEmEstoque, double precoUnit) {
		this.nome = nome;
		this.cod = contadorCod++;
		this.unidsEmEstoque = unidsEmEstoque;
		this.precoUnit = precoUnit;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCod() {
		return cod;
	}

	public int getUnidsEmEstoque() {
		return unidsEmEstoque;
	}

	public void setUnidsEmEstoque(int unidsEmEstoque) {
		this.unidsEmEstoque = unidsEmEstoque;
	}

	public double getPrecoUnit() {
		return precoUnit;
	}

	public void setPrecoUnit(double precoUnit) {
		this.precoUnit = precoUnit;
	}
	
	public String toString() {
		String s = "";
		s += "Código: " + this.cod + "\n";
		s += "Nome: " + this.nome + "\n";
		s += "Unidades em estoque: " + this.unidsEmEstoque + "\n";
		s += String.format("Preço: R$%.2f\n", this.precoUnit);
		
		return s;		
	}
}
