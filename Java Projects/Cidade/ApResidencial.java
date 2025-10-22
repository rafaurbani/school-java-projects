@SuppressWarnings("serial")
public class ApResidencial extends Sala implements registroPrefeitura {
	private String proprietario;
	private double area;
	
	public ApResidencial(String p, double a) {
		this.proprietario = p;
		this.area = a;
	}
	
	public double getValor() {
		return valorCUB * 2;
	}
	
	public double calcCondominio() {
		return getValor() * this.area;
	}
	
	public String getProprietario() {
		return this.proprietario;
	}
}
