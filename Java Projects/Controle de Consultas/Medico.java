public class Medico extends Pessoa
{
	//atributos
    private String especialidade;

    //métodos
    public Medico(String nome, String cpf, int idade, String telefone, String especialidade) {
        super(nome, cpf, idade, telefone);
        this.especialidade = especialidade;
    }

    //imprime as informações do médico de forma organizada
    public void imprimir() {
        System.out.printf("Dados do médico:\n");
        System.out.printf("  Nome: %s\n", this.getNome());
        System.out.printf("  CPF: %s\n", this.getCpf());
        System.out.printf("  Idade: %d anos\n", this.getIdade());
        System.out.printf("  Telefone: %s\n", this.getTelefone());
        System.out.printf("  Especialidade: %s\n", this.especialidade);
    }
    
    //getters e setters
    public String getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}