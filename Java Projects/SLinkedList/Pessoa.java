import java.util.Comparator;

public class Pessoa {
	private String estado, nome;
	private int idade;
	
	public Pessoa(String nome, String estado, int idade) {
		this.nome = nome;
		this.estado = estado;
		this.idade = idade;
	}

	public String getNome() {
		return nome;
	}

	public String getEstado() {
		return estado;
	}

	public int getIdade() {
		return idade;
	}

	public String toString() {
		return "Nome: " + nome + ", estado: " + estado + ", idade: " + idade;
	}
}

class SortEstado implements Comparator<Pessoa> {
	public int compare(Pessoa o1, Pessoa o2) {
		return o1.getEstado().compareTo(o2.getEstado());
	}
}

class SortNome implements Comparator<Pessoa> {
	public int compare(Pessoa o1, Pessoa o2) {
		return o1.getNome().compareTo(o2.getNome());
	}
}

class SortIdade implements Comparator<Pessoa> {
	public int compare(Pessoa o1, Pessoa o2) {
		return o1.getIdade() - o2.getIdade();
	}
}


