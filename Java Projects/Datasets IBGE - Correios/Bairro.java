
public class Bairro {
	private long codBairro;
	private String nomeBairro;
	private String nomeMunicipio;
	private String uf;
	
	public Bairro(int codMunicipio, long codBairro, String nomeBairro, String nomeMunicipio, String uf) {
		this.codBairro = codBairro;
		this.nomeBairro = nomeBairro;
		this.nomeMunicipio = nomeMunicipio;
		this.uf = uf;
	}

	public long getCodBairro() {
		return codBairro;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public String getUf() {
		return uf;
	}
	
	public String toString() {
		return String.format("Bairro ID %d:\n	%s (%s, %s)", getCodBairro(), getNomeBairro(), getNomeMunicipio(), getUf());
	}
}
