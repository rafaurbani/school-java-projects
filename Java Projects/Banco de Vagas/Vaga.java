
public class Vaga {
	//atributos (countVaga - estático para diferenciar o id)
	private static int countVaga = 0;
	protected int id;
	protected String descricao;
	protected double salario;
	
	//construtor que aumenta 1 no countVaga a cada vaga criada
	public Vaga(String descricao, double salario) {
		this.id = ++countVaga;
		this.descricao = descricao;
		this.salario = salario;
	}
	
	//getters e setters
	public int getId() {
		return this.id;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public double getSalario() {
		return this.salario;
	}
	
	public void alteraSalario(double salario) {
		this.salario = salario;
	}

	//método toString que retorna String com ID, descrição e salário da vaga
	public String toString() {
		return String.format("ID: %d\nDescricao: %s\nSalario: R$ %.2f\n", this.id, this.descricao, this.salario); 
	}
	
	
}

