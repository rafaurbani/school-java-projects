
public class Pessoa {
	//atributos (countPessoa - estático para diferenciar o id)
	private static int countPessoa = 0;
	protected int id;
	protected String nome;
	protected Data dataNascimento;
	
	//construtor que aumenta 1 no countPessoa a cada pessoa criada
	Pessoa(String nome, Data dataNascimento) {
		this.id = ++countPessoa;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	//getters e setters
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Data getDataNascimento() {
		return dataNascimento;
	}

	//método toString que retorna String com ID, nome e data de nascimento organizados
	public String toString() {
		return String.format("ID: %s\nNome: %s\nData de Nascimento: %s\n", this.id, this.nome, this.dataNascimento.toString());
	}
}
