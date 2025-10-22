public class Paciente extends Pessoa
{
	//atributos (com contador de pacientes para definir o id)
    private static int contadorPacientes = 1;
    private int id;
    private String profissao;
    private String planoDeSaude;
    private Historico historico;

    //métodos
    public Paciente(String nome, String cpf, int idade, String telefone, String profissao, int numPlanoDeSaude) {
        super(nome, cpf, idade, telefone);
        this.id = contadorPacientes++;
        this.profissao = profissao;
        this.planoDeSaude = Utilitario.planosDeSaude[numPlanoDeSaude - 1];
        this.historico = new Historico();
    }
    
    //imprime as informações do paciente de forma organizada
    public void imprimir() {
        System.out.printf("Dados do paciente:\n");
        System.out.printf("  Nome: %s\n", this.getNome());
        System.out.printf("  ID: %d\n", this.id);
        System.out.printf("  CPF: %s\n", this.getCpf());
        System.out.printf("  Idade: %d anos\n", this.getIdade());
        System.out.printf("  Telefone: %s\n", this.getTelefone());
        System.out.printf("  Profissão: %s\n", this.profissao);
        System.out.printf("  Plano de saúde: %s\n", this.planoDeSaude);
    }

    //getters e setters
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getProfissao()
    {
        return this.profissao;
    }

    public void setProfissao(String profissao)
    {
        this.profissao = profissao;
    }

    public String getPlanoDeSaude()
    {
        return this.planoDeSaude;
    }
    
    public void setPlanoDeSaude(String planoDeSaude) {
    	this.planoDeSaude = planoDeSaude;
    }

    public Historico getHistorico()
    {
        return this.historico;
    }

    public void setHistorico(Historico historico)
    {
        this.historico = historico;
    }
}
