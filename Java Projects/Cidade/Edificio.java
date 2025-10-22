
@SuppressWarnings("serial")
public class Edificio extends Edificacao {
	private int quantosElevadores;
	private int quantasVagasBox;
	private Sala[] condominio;
	private int totSalasOcupadas;
	
	public Edificio(int quantAndares, String material, double areaConst, int quantosElevadores, int quantasVagasBox) {
		super(quantAndares, material, areaConst);
		this.quantosElevadores = quantosElevadores;
		this.quantasVagasBox = quantasVagasBox;
	}
	
	public Edificio(int quantAndares, String material, double areaConst,
			        int quantosElevadores, int quantasVagasBox, int quantSalas) {
		super(quantAndares, material, areaConst);
		this.quantosElevadores = quantosElevadores;
		this.quantasVagasBox = quantasVagasBox;
		this.condominio = new Sala[quantSalas];
		this.totSalasOcupadas = 0;
	}
	
	public void ocupaSala(Sala sala) {
		for (int i = 0; i < this.condominio.length; i++) {
			if (this.condominio[i] == null) {
				this.condominio[i] = sala;
				break;
			}
		}
	}
	
	public String mostraSalas() {
		String s = "Salas do condomínio: \n";
		
		for (int i = 0; i < this.condominio.length; i++) {
			if (this.condominio[i] == null) {
				s += "Sala " + (i + 1) + ": VAZIA";
			} else {
				s += "Sala " + (i + 1) + ": OCUPADA\n";
				if (this.condominio[i] instanceof ApResidencial)
					s += "- Apartamento Residencial";
				else
					s += "- Sala Comercial";
			}
			
			s += "\n";
		}
		
		return s;
	}

	public int getQuantosElevadores() {
		return quantosElevadores;
	}

	public void setQuantosElevadores(int quantosElevadores) {
		this.quantosElevadores = quantosElevadores;
	}

	public int getQuantasVagasBox() {
		return quantasVagasBox;
	}

	public void setQuantasVagasBox(int quantasVagasBox) {
		this.quantasVagasBox = quantasVagasBox;
	}

	public Sala[] getCondominio() {
		return condominio;
	}
	
	public int getTotSalasOcupadas() {
		return totSalasOcupadas;
	}
}
