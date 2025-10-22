
public class Logradouro {
	private String uf;
	private String municipio;
	private String bairro;
	private int cep;
	private String nome;
	
	public Logradouro(String uf, String municipio, String bairro, int cep, String nome) {
		this.uf = uf;
		this.municipio = municipio;
		this.bairro = bairro;
		this.cep = cep;
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public String getMunicipio() {
		return municipio;
	}

	public String getBairro() {
		return bairro;
	}

	public int getCep() {
		return cep;
	}

	public String getNome() {
		return nome;
	}

	public String toString() {
		return String.format("Logradouro CEP %d:\n	%s", getCep(), getNome());
	}	
}
