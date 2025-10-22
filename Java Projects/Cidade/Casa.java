
@SuppressWarnings("serial")
public class Casa extends Edificacao {
	private boolean comGaragem;
	private boolean comPiscina;

	public Casa(int quantAndares, String material, double areaConst, boolean comGaragem, boolean comPiscina) {
		super(quantAndares, material, areaConst);
		this.comGaragem = comGaragem;
		this.comPiscina = comPiscina;
	}
	
	public boolean getComGaragem() {
		return comGaragem;
	}

	public void setComGaragem(boolean comGaragem) {
		this.comGaragem = comGaragem;
	}

	public boolean getComPiscina() {
		return comPiscina;
	}

	public void setComPiscina(boolean comPiscina) {
		this.comPiscina = comPiscina;
	}
}
