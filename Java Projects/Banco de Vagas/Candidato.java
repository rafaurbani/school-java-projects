public class Candidato extends Pessoa {
	//atributos
	private Competencia[] competencias;
	private double expSalario;
	
	//construtor com array de competencias vazio e salário 0
	public Candidato(String nome, Data dataNascimento) {
		super(nome, dataNascimento);
		this.competencias = new Competencia[100];
		this.expSalario = 0.0;
	}

	//construtor com array de competencias e salário definidos nos parâmetros
	public Candidato(String nome, Data dataNascimento, Competencia[] competencias, double expSalario) {
		super(nome, dataNascimento);
		this.competencias = competencias;
		this.expSalario = expSalario;
	}

	//getter da array de competências
	public Competencia[] getCompetencias() {
		return competencias;
	}

	//método para adicionar ou alterar competencia da array
	public boolean alteraCompetencias(Competencia competencia) {
		//se a competencia já existir, alterar apenas o nível e retorna true
		for (int i = 0; i < this.competencias.length; i++) {
			if (this.competencias[i] != null && this.competencias[i] == competencia) {
				this.competencias[i].setNivel(competencia.getNivel());
				return true;
			}
		}
		
		//se não existir, insere na primeira posicao nula do array e retorna true
		for (int i = 0; i < this.competencias.length; i++) {
			if (this.competencias[i] == null) {
				this.competencias[i] = competencia;
				return true;
			}
		}
		
		//se houver algum erro ou não for inserido, retorna false
		return false;
	}

	//getter e setter de expSalario
	public double getExpSalario() {
		return expSalario;
	}

	public void setExpSalario(double expSalario) {
		this.expSalario = expSalario;
	}

	//método toString que retorna String com a superclasse Pessoa e todas as posições não nulas da array da classe, além do expSalario
	public String toString() {
		//criação da String das competências para ser retornado
		String competencias = "";
		//loop para adicionar as competências para a String competencias;
		for (int i = 0; i < this.getCompetencias().length; i++) {
			if (getCompetencias()[i] != null) {
				competencias += getCompetencias()[i];
			}
		}
		//retorno da String com a superclasse Pessoa, a String criada e a expectativa salarial
		return String.format("%s\nCompetencias:\n%s\nExpectativa salarial: R$ %.2f\n", super.toString(), competencias, this.expSalario);
	}

	
}
