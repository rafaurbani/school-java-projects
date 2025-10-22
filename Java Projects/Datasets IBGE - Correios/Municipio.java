
public class Municipio {
	private int codMunicipio;
	private String nome;
	private String uf;
	
	public Municipio(int codMunicipio, String nome, String uf) {
		this.codMunicipio = codMunicipio;
		this.nome = nome;
		this.uf = uf;
	}

	public int getCodMunicipio() {
		return codMunicipio;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}
	
	public String toString() {
		return String.format("Município ID %d:\n	%s (%s)", getCodMunicipio(), getNome(), getUf());
	}
}
