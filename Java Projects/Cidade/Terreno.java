import java.io.Serializable;

@SuppressWarnings("serial")
public class Terreno implements Serializable {
	private double area;
	private Edificacao edificacao;
	
	public Terreno(double area) {
		this.area = area;
		this.edificacao = null;
	}
	
	public Terreno(double area, Edificacao edificacao) {
		this.area = area;
		this.edificacao = edificacao;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public Edificacao getEdificacao() {
		return edificacao;
	}

	public void setEdificacao(Edificacao edificacao) {
		this.edificacao = edificacao;
	}
	
	
}
