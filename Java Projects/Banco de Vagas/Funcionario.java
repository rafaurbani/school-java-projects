
public class Funcionario extends Pessoa {
	//atributos
	private String cargo;
	private double salario;
	
	//construtor
	public Funcionario(String nome, Data dataNascimento, String cargo, double salario) {
		super(nome, dataNascimento);
		this.cargo = cargo;
		this.salario = salario;
	}

	//getters e setters
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	//método toString que retorna String com a superclasse Pessoa, cargo e salário 
	public String toString() {
		return String.format("%sCargo: %s\nSalário: R$%.2f\n", super.toString(), this.cargo, this.salario);
	}
	
	
}
