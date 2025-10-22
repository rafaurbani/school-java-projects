public class VagaGestor extends Vaga {
	//atributo - array de competências
	private Competencia[] reqMetodologias;
	
	//construtor com lista de competências inicializados vazios
	public VagaGestor(String descricao, double salario) {
		super(descricao, salario);
		this.reqMetodologias = new Competencia[100];
	}
	
	//construtor com lista de competências inicializadas pelos arrays do parâmetro
	public VagaGestor(String descricao, double salario, Competencia[] reqMetodologias) {
		super(descricao, salario);
		this.reqMetodologias = reqMetodologias;
	}
	
	//método para adicionar ou alterar competencia da array reqMetodologias
	public boolean alteraReqMetodologias(Competencia competencia) {
		//se a competencia já existir, alterar apenas o nível e retorna true
		for (int i = 0; i < this.reqMetodologias.length; i++) {
			if (this.reqMetodologias[i] != null && this.reqMetodologias[i] == competencia) {
				this.reqMetodologias[i].setNivel(competencia.getNivel());
				return true;
			}
		}
		
		//se não existir, insere na primeira posicao nula do array e retorna true
		for (int i = 0; i < this.reqMetodologias.length; i++) {
			if (this.reqMetodologias[i] == null) {
				this.reqMetodologias[i] = competencia;
				return true;
			}
		}
		
		//se houver algum erro ou não for inserido, retorna false
		return false;
	}
	
	//getter para array reqMetodologias
	public Competencia[] getReqMetodologias() {
		return this.reqMetodologias;
	}

	//método toString que retorna String com a superclasse Vaga, e todas as posições não nulas da array da classe
	public String toString() {
		//criação da String das competências para ser retornado
		String competencias = "";
		//loop para adicionar as competências para a String competencias;
		for (int i = 0; i < this.getReqMetodologias().length; i++) {
			if (getReqMetodologias()[i] != null) {
				competencias += getReqMetodologias()[i];
			}
		}
		
		//retorno da String com a superclasse Vaga e a String criada
		return String.format("%s\nCompetencias de Metodologias:\n%s", super.toString(), competencias);
	}
	
}
