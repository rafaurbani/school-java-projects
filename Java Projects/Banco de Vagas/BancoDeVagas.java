
public class BancoDeVagas {
	//atributos - arrays para armazenar as vagas, candidatos e funcionários
	private Vaga[] vagas;
	private Candidato[] candidatos;
	private Funcionario[] funcionarios;
	
	//construtor que inicializa as arrays vazias
	public BancoDeVagas() {
		this.vagas = new Vaga[100];
		this.candidatos = new Candidato[100];
		this.funcionarios = new Funcionario[100];
	}
	
	//método para adicionar vaga no array de vagas
	public boolean adicionaVaga(Vaga vaga) {
		//verifica se a vaga já existe no array de vagas (se sim, retorna false)
		for (int i = 0; i < this.vagas.length; i++) {
			if (this.vagas[i] != null & this.vagas[i] == vaga)
				return false;
		}
		
		//se a vaga ainda não existe, adiciona na primeira posição nula do array, e retorna true
		for (int i = 0; i < this.vagas.length; i++) {
			if (this.vagas[i] == null) {
				this.vagas[i] = vaga;
				return true;
			}
		}
		
		//se acontecer algum erro ou não for inserido a vaga, retorna false
		return false;
	}
	
	//método para adicionar candidato no array de candidatos
	public boolean adicionaCandidato(Candidato candidato) {
		//verifica se o candidato já existe no array de candidatos (se sim, retorna false)
		for (int i = 0; i < this.candidatos.length; i++) {
			if (this.candidatos[i] != null & this.candidatos[i] == candidato)
				return false;
		}
		
		//se o candidato ainda não existe, adiciona na primeira posição nula do array, e retorna true
		for (int i = 0; i < this.candidatos.length; i++) {
			if (this.candidatos[i] == null) {
				this.candidatos[i] = candidato;
				return true;
			}
		}
		
		//se acontecer algum erro ou não for inserido o candidato, retorna false
		return false;
	}
	
	//método para contratar o candidato (insere o candidato na array de funcionário, e retira a vaga e o candidato das arrays correspondentes)
	public boolean contratarCandidato(int idVaga, int idCandidato) {
		//loop e if para verificar se o id da vaga do parâmetro existe na array de vagas
		for (int iV = 0; iV < this.vagas.length; iV++) {
			if (this.vagas[iV] != null && this.vagas[iV].getId() == idVaga) {
				//loop e if para verificar se o id do candidato do parâmetro existe no array de candidatos
				for (int iC = 0; iC < this.candidatos.length; iC++) {
					if (this.candidatos[iC] != null && this.candidatos[iC].getId() == idCandidato) {
						//loop e if para inserir o candidato na primeira posicao nula do array de funcionários (contratar)
						for (int iF = 0; iF < this.funcionarios.length; iF++) {
							if (this.funcionarios[iF] == null) {
								String cargo = "";
								//if para inserir a descrição de acordo com a vaga
								if (this.vagas[iV] instanceof VagaDesenvolvedor)
									cargo += "Desenvolvedor";
								else if (this.vagas[iV] instanceof VagaGestor)
									cargo += "Gestor";
								else
									cargo += "Funcionário";
								
								//insere o funcionário no array
								this.funcionarios[iF] = new Funcionario(this.candidatos[iC].getNome(), this.candidatos[iC].getDataNascimento(), cargo, this.vagas[iV].getSalario());
								
								//remove a vaga e o candidato das arrays correspondentes e retorna true
								this.vagas[iV] = null;
								this.candidatos[iC] = null;
								return true;
							}
						}
					}
				}
			}
		}
		
		//se houver algum erro ou o candidato não for inserido, retorna false
		return false;
	}
	
	//método para listar as vagas para determinado candidato
	public void listaVagasParaCandidato(int idCandidato) {
		//variável do candidato
		Candidato candidato = null;
		
		//insere o candidato na variável de acordo com o id do parâmetro
		for (int i = 0; i < this.candidatos.length; i++) {
			if (this.candidatos[i] != null && this.candidatos[i].getId() == idCandidato) {
				candidato = this.candidatos[i];
				break;
			}
		}
		
		//se nenhum candidato for encontrado, retorna vazio para quebrar o método
		if (candidato == null)
			return;
		
		System.out.printf("LISTA DE VAGAS PARA CANDIDATO - ID %d\n", candidato.getId());
		System.out.printf("-------------------------------------------------\n\n");
		
		//loop para as competencias do candidato - procura as vagas que são compatíveis com pelo menos uma das competências do funcionário
		//
		loop:for (int i = 0; i < this.vagas.length; i++) {
			for (int iCompCan = 0; iCompCan < candidato.getCompetencias().length; iCompCan++) {
				//if para verificar se a vaga é uma instância da vaga de desenvolvedor
				if (this.vagas[i] != null && this.vagas[i] instanceof VagaDesenvolvedor) {
					VagaDesenvolvedor vaga = (VagaDesenvolvedor)this.vagas[i];
					//loops e ifs para verificar se as competencias não são nulas, e se uma das competências da vaga é compatível com as competências do funcionário (se sim, imprime a vaga)
					for (int iComp = 0; iComp < vaga.getReqLinguagens().length; iComp++) {
						if (vaga.getReqLinguagens()[iComp] != null && candidato.getCompetencias()[iCompCan] != null &&
						vaga.getReqLinguagens()[iComp] == candidato.getCompetencias()[iCompCan] && candidato.getExpSalario() <= this.vagas[i].getSalario()) {
							System.out.println(String.format("Vaga: \n%s\n", this.vagas[i].toString()));
							System.out.printf("-------------------------------------------------\n\n");
							continue loop;
						}
					}
					for (int iComp = 0; iComp < vaga.getReqBancosDeDados().length; iComp++) {
						if (vaga.getReqBancosDeDados()[iComp] != null && candidato.getCompetencias()[iCompCan] != null &&
						vaga.getReqBancosDeDados()[iComp] == candidato.getCompetencias()[iCompCan] && candidato.getExpSalario() <= this.vagas[i].getSalario()) {
							System.out.println(String.format("Vaga: \n%s\n", this.vagas[i].toString()));
							System.out.printf("-------------------------------------------------\n\n");
							continue loop;
						}
					}
				}
				
				//if para verificar se a vaga é uma instância da vaga de gestor
				else if (this.vagas[i] != null && this.vagas[i] instanceof VagaGestor) {
					VagaGestor vaga = (VagaGestor)this.vagas[i];
					//loop e if para verificar se as competencias não são nulas, e se uma das competências da vaga é compatível com as competências do funcionário (se sim, imprime a vaga)
					for (int iComp = 0; iComp < vaga.getReqMetodologias().length; iComp++) {
						if (vaga.getReqMetodologias()[iComp] != null && candidato.getCompetencias()[iCompCan] != null &&
						vaga.getReqMetodologias()[iComp] == candidato.getCompetencias()[iCompCan] && candidato.getExpSalario() <= this.vagas[i].getSalario()) { 
							System.out.println(String.format("Vaga: \n%s\n", this.vagas[i].toString()));
							System.out.printf("-------------------------------------------------\n\n");
							continue loop;
						}
					}
				}
			}
		}
	}
	
	//método para listar os candidatos para determinada vaga
	public void listaCandidatosParaVaga(int idVaga) {
		//variável da vaga
		Vaga vaga = null;
		
		//insere a vaga na variável de acordo com o id do parâmetro
		for (int i = 0; i < this.vagas.length; i++) {
			if (this.vagas[i] != null && this.vagas[i].getId() == idVaga) {
				vaga = this.vagas[i];
				break;
			}
		}
		
		//se nenhuma vaga for encontrada, retorna vazio para quebrar o método
		if (vaga == null)
			return;
		
		System.out.printf("LISTA DE CANDIDATOS PARA VAGA - ID %d\n", vaga.getId());
		System.out.printf("-------------------------------------------------\n\n");
		
		//procura os candidatos que são compatíveis com as competências da vaga
		loop:for (int iCan = 0; iCan < this.candidatos.length; iCan++) {
			//if para verificar se a vaga é uma instância da vaga de desenvolvedor
			if (vaga instanceof VagaDesenvolvedor && this.candidatos[iCan] != null) {
				VagaDesenvolvedor vagaD = (VagaDesenvolvedor)vaga;
				//loops e ifs para verificar se as competencias da vaga NÃO são compatíveis com as competências do funcionário (se não forem, quebra e continua o loop)
				for (int i = 0; i < vagaD.getReqBancosDeDados().length; i++) {
					if (this.candidatos[iCan].getCompetencias() != null && vagaD.getReqBancosDeDados()[i] != this.candidatos[iCan].getCompetencias()[i])
						continue loop;
				}
				
				for (int i = 0; i < vagaD.getReqLinguagens().length; i++) {
					if (this.candidatos[iCan].getCompetencias() != null && vagaD.getReqLinguagens()[i] != this.candidatos[iCan].getCompetencias()[i])
						continue loop;
				}
				
				//se a vaga for compatível e o loop não for quebrado, imprime o candidato em questão
				System.out.println(String.format("Candidato: \n%s\n", this.candidatos[iCan].toString()));
			}
			
			//if para verificar se a vaga é uma instância da vaga de gestor
			else if (vaga instanceof VagaGestor && this.candidatos[iCan] != null) {
				VagaGestor vagaG = (VagaGestor)vaga;
				//loop e if para verificar se as competencias da vaga NÃO são compatíveis com as competências do funcionário (se não forem, quebra e continua o loop)
				for (int i = 0; i < vagaG.getReqMetodologias().length; i++) {
					if (this.candidatos[iCan].getCompetencias() != null && vagaG.getReqMetodologias()[i] != this.candidatos[iCan].getCompetencias()[i])
						continue loop;
				}
				
				//se a vaga for compatível e o loop não for quebrado, imprime o candidato em questão
				System.out.println(String.format("Candidato: \n%s\n", this.candidatos[iCan].toString()));
				System.out.printf("-------------------------------------------------\n\n");
			}
		}
	}
	
	//método para filtrar e imprimir as vagas de acordo com a expectativa salarial recebida
	public void listaVagasFiltro(double expSalario) {
		//contador das vagas disponíveis
		int contVaga = 1;
		
		System.out.println("LISTA DE VAGAS - Filtro por expectativa salarial");
		System.out.printf("Expectativa salarial: R$ %.2f\n", expSalario);
		System.out.printf("-------------------------------------------------\n\n");
		
		//se a vaga não for nula e o salário da vaga for igual ou maior que a expectativa salarial do parâmetro, imprime a vaga
		for (int i = 0; i < this.vagas.length; i++) {
			if (this.vagas[i] != null && this.vagas[i].getSalario() >= expSalario) {
				System.out.println(String.format("Vaga %d: \n%s\n", contVaga++, this.vagas[i].toString()));
				System.out.printf("-------------------------------------------------\n\n");
			}
		}
	}
	
	//método para filtrar e imprimir as vagas de acordo com a competência recebida
	public void listaVagasFiltro(Competencia competencia) {
		//contador das vagas disponíveis
		int contVaga = 1;
		
		System.out.println("LISTA DE VAGAS - Filtro por competencia");
		System.out.printf("Competência:\n%s", competencia);
		System.out.printf("------------------------------------------\n\n");
		
		//loops e ifs para procurar as vagas compatíveis com a competência do parametro
		loop:for (int i = 0; i < this.vagas.length; i++) {
			//se a vaga for de desenvolvedor, verifica se a competência do parametro é compatível com alguma das competência das arrays da vaga
			if (this.vagas[i] != null && this.vagas[i] instanceof VagaDesenvolvedor) {
				VagaDesenvolvedor vaga = (VagaDesenvolvedor)this.vagas[i];
				//loops e ifs para verificar se é compatível, imprime a vaga e continua o loop
				for (int iComp = 0; iComp < vaga.getReqBancosDeDados().length; iComp++) {
					if (vaga.getReqBancosDeDados()[iComp] == competencia) {
						System.out.println(String.format("Vaga %d: \n%s\n", contVaga++, this.vagas[i].toString()));
						System.out.printf("-----------------------------------------------\n\n");
						continue loop;
					}
				}
				for (int iComp = 0; iComp < vaga.getReqLinguagens().length; iComp++) {
					if (vaga.getReqLinguagens()[iComp] == competencia) {
						System.out.println(String.format("Vaga %d: \n%s\n", contVaga++, this.vagas[i].toString()));
						System.out.printf("-----------------------------------------------\n\n");
						continue loop;
					}
				}
			}
			
			//se a vaga for de gestor, verifica se a competência do parametro é compatível com alguma das competência da array da vaga
			if (this.vagas[i] != null && this.vagas[i] instanceof VagaGestor) {
				VagaGestor vaga = (VagaGestor)this.vagas[i];
				//loop e if para verificar se é compatível, imprime a vaga e continua o loop
				for (int iComp = 0; iComp < vaga.getReqMetodologias().length; iComp++) {
					if (vaga.getReqMetodologias()[iComp] == competencia) {
						System.out.println(String.format("Vaga %d: \n%s\n", contVaga++, this.vagas[i].toString()));
						System.out.printf("-----------------------------------------------\n\n");
						continue loop;
					}
				}
			}
		}
	}
	
	//método que lista todas as vagas do array vagas de forma organizada
	public void listaVagas() {
		System.out.println("LISTA DE VAGAS:");
		System.out.printf("----------------------------------\n\n");
		for (int i = 0; i < this.vagas.length; i++) {
			if (this.vagas[i] != null) {
				System.out.println(this.vagas[i]);
				System.out.printf("----------------------------------\n\n");
			}
		}
	}
	
	//método que lista todos os candidatos do array candidatos de forma organizada
	public void listaCandidatos() {
		System.out.println("LISTA DE CANDIDATOS:");
		System.out.printf("----------------------------------\n\n");
		for (int i = 0; i < this.candidatos.length; i++) {
			if (this.candidatos[i] != null) {
				System.out.println(this.candidatos[i]);
				System.out.printf("----------------------------------\n\n");
			}
		}
	}
	
	//método que lista todos os funcionarios do array funcionarios de forma organizada
	public void listaFuncionarios() {
		System.out.println("LISTA DE FUNCIONÁRIOS:");
		System.out.printf("----------------------------------\n\n");
		for (int i = 0; i < this.funcionarios.length; i++) {
			if (this.funcionarios[i] != null) {
				System.out.println(this.funcionarios[i]);
				System.out.printf("----------------------------------\n\n");
			}
		}
	}
	
	
}
