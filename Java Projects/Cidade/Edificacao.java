import java.io.Serializable;

@SuppressWarnings("serial")
public class Edificacao implements Serializable{
	private int quantAndares;
	private String material;
	private double areaConst;
	
	public Edificacao(int quantAndares, String material, double areaConst) {
		this.quantAndares = quantAndares;
		this.material = material;
		this.areaConst = areaConst;
	}

	public int getQuantAndares() {
		return quantAndares;
	}

	public void setQuantAndares(int quantAndares) {
		this.quantAndares = quantAndares;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public double getAreaConst() {
		return areaConst;
	}

	public void setAreaConst(double areaConst) {
		this.areaConst = areaConst;
	}
}
