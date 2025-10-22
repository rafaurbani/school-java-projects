
public class Estado {
	private int codUf;
	private String nome;
	private String uf;
	private int codRegiao;

	public Estado(int codUf, String nome, String uf, int codRegiao) {
		this.codUf = codUf;
		this.nome = nome;
		this.uf = uf;
		this.codRegiao = codRegiao;
	}

	public int getCodUf() {
		return codUf;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public int getCodRegiao() {
		return codRegiao;
	}

	public String getNomeRegiao() {
		switch (codRegiao) {
		case 1: return "Norte";
		case 2: return "Nordeste";
		case 3: return "Sudeste";
		case 4: return "Sul";
		case 5:	return "Centro-Oeste";
		default: return "";
		}
	}
	
	public String toString() {
		return String.format("Estado ID %d:\n	%s (%s)\n	Região %s", getCodUf(), getNome(), getUf(), getNomeRegiao());
	}
	
	
}
