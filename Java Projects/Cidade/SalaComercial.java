
@SuppressWarnings("serial")
public class SalaComercial extends Sala implements registroPrefeitura {
	private String registroComercial;
	private double area;
	
	public SalaComercial (String r, double a) {
		this.registroComercial = r;
		this.area = a;
	}
	
	public double getValor() {
		return valorCUB * 3;
	}
	
	public double calcCondominio() {
		return getValor() * this.area * 1.7;
	}
	
	public String getRegistroComercial() {
		return this.registroComercial;
	}
}
