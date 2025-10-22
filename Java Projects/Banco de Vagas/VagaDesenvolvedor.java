public class VagaDesenvolvedor extends Vaga {
	//atributos - array de competências
	private Competencia[] reqLinguagens;
	private Competencia[] reqBancosDeDados;
	
	//construtor com listas de competências inicializados vazios
	public VagaDesenvolvedor(String descricao, double salario) {
		super(descricao, salario);
		this.reqLinguagens = new Competencia[100];
		this.reqBancosDeDados = new Competencia[100];
	}
	
	//construtor com listas de competências inicializadas pelos arrays do parâmetro
	public VagaDesenvolvedor(String descricao, double salario, Competencia[] reqLinguagens, Competencia[] reqBancosDeDados) {
		super(descricao, salario);
		this.reqLinguagens = reqLinguagens;
		this.reqBancosDeDados = reqBancosDeDados;
	}
	
	//método para adicionar ou alterar competencia da array reqLinguagens
	public boolean alteraReqLinguagens(Competencia competencia) {
		//se a competencia já existir, alterar apenas o nível e retorna true
		for (int i = 0; i < this.reqLinguagens.length; i++) {
			if (this.reqLinguagens[i] != null && this.reqLinguagens[i] == competencia) {
				this.reqLinguagens[i].setNivel(competencia.getNivel());
				return true;
			}
		}
		
		//se não existir, insere na primeira posicao nula do array e retorna true
		for (int i = 0; i < this.reqLinguagens.length; i++) {
			if (this.reqLinguagens[i] == null) {
				this.reqLinguagens[i] = competencia;
				return true;
			}
		}
		
		//se houver algum erro ou não for inserido, retorna false
		return false;
	}
	
	//getter para array reqLinguagens
	public Competencia[] getReqLinguagens() {
		return this.reqLinguagens;
	}
	
	//método para adicionar ou alterar competencia da array reqBancosDeDados
	public boolean alteraReqBancosDeDados(Competencia competencia) {
		//se a competencia já existir, alterar apenas o nível e retorna true
		for (int i = 0; i < this.reqBancosDeDados.length; i++) {
			if (this.reqBancosDeDados[i] != null && this.reqBancosDeDados[i] == competencia) {
				this.reqBancosDeDados[i].setNivel(competencia.getNivel());
				return true;
			}
		}
		
		//se não existir, insere na primeira posicao nula do array e retorna true
		for (int i = 0; i < this.reqBancosDeDados.length; i++) {
			if (this.reqBancosDeDados[i] == null) {
				this.reqBancosDeDados[i] = competencia;
				return true;
			}
		}
		
		//se houver algum erro ou não for inserido, retorna false
		return false;
	}
	
	//getter para array reqBancosDeDados
	public Competencia[] getReqBancosDeDados() {
		return this.reqBancosDeDados;
	}

	//método toString que retorna String com a superclasse Vaga, e todas as posições não nulas das duas arrays da classe
	public String toString() {
		//criação da String das competências de linguagens para ser retornado
		String competenciasLinguagens = "";
		//loop para adicionar as competências para a String competenciasLinguagens;
		for (int i = 0; i < this.getReqLinguagens().length; i++) {
			if (getReqLinguagens()[i] != null) {
				competenciasLinguagens += getReqLinguagens()[i];
			}
		}
		
		//criação da String das competências de bancos de dados para ser retornado
		String competenciasBDD = "";
		//loop para adicionar as competências para a String competenciasBDD;
		for (int i = 0; i < this.getReqBancosDeDados().length; i++) {
			if (getReqBancosDeDados()[i] != null) {
				competenciasBDD += getReqBancosDeDados()[i];
			}
		}
		//retorno da String com a superclasse Vaga e as Strings criadas 
		return String.format("%s\nCompetencias de Linguagens:\n%s\nCompetencias de Bancos de Dados:\n%s", super.toString(), competenciasLinguagens, competenciasBDD);
	}
	
	
}
