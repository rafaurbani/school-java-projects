public class Principal {
	//método main
	public static void main(String[] args) {
		//arrays para armazenar os pacientes, médicos e consultas (tamanho aumentado conforme adicionado)
		Paciente[] arrayPacientes = new Paciente[5];
		Medico[] arrayMedicos = new Medico[5];
		Consulta[] arrayConsultas = new Consulta[10];
		
		//mensagem de boas-vindas
		System.out.println("Bem-vindo(a) ao Controle de Consultas v1.0!");
		
		//loop para selecionar as operações (número 7 quebra o loop e todo número fora do intervalo 1-7 repete o loop)
		operacao:while(true) {
			System.out.println("-------------------------------------------");
			System.out.println("| Escolha a operação a ser executada:     |");
			System.out.println("|    1. Criar novo paciente               |");
			System.out.println("|    2. Criar novo médico                 |");
			System.out.println("|    3. Criar nova consulta               |");
			System.out.println("|    4. Acessar histórico de consultas    |");
			System.out.println("|       para determinado paciente         |");
			System.out.println("|    5. Deletar uma consulta específica   |");
			System.out.println("|       do histórico                      |");
			System.out.println("|    6. Imprimir os pacientes para        |");
			System.out.println("|       a vacinação                       |");
			System.out.println("|    7. Sair do programa                  |");
			System.out.println("-------------------------------------------");
			
			//leitura do número da operação
			int op = Teclado.leInt();
			
			//switch para realizar a operação conforme o número (default = repete o loop operação)
			switch(op) {
			//criar novo paciente
			case 1:
				//boolean e loop para aumentar array se estiver cheia
				boolean cheioP = true;
				while(true) {
					for (int i = 0; i < arrayPacientes.length; i++) {
						if (arrayPacientes[i] == null) {
							//criação do paciente sendo colocada na primeira posição nula do array
							arrayPacientes[i] = criaPaciente();
							cheioP = false;
							System.out.println("-------------------------------------------");
							System.out.println("|             Paciente criado!            |");
							System.out.println("-------------------------------------------");
							//imprime o paciente recém-criado
							arrayPacientes[i].imprimir();
							System.out.println("-------------------------------------------");
							Teclado.leString("|       Aperte enter para continuar       |");
							continue operacao;
						}
					}
					
					//criação da nova array se estiver cheia, repetindo o loop depois de criada
					if(cheioP) {
						Paciente[] novaArray = new Paciente[arrayPacientes.length + 10];
						for (int i = 0; i < arrayPacientes.length; i++) {
							if (arrayPacientes[i] != null)
								novaArray[i] = arrayPacientes[i];
						}
						arrayPacientes = novaArray;
					}
				}
			//criar novo médico
			case 2:
				//boolean e loop para aumentar array se estiver cheia
				boolean cheioM = true;
				while(true) {
					for (int i = 0; i < arrayMedicos.length; i++) {
						if (arrayMedicos[i] == null) {
							//criação do médico sendo colocada na primeira posição nula do array
							arrayMedicos[i] = criaMedico();
							cheioM = false;
							System.out.println("-------------------------------------------");
							System.out.println("|              Médico criado!             |");
							System.out.println("-------------------------------------------");
							//imprime o médico recém-criado
							arrayMedicos[i].imprimir();
							System.out.println("-------------------------------------------");
							Teclado.leString("|       Aperte enter para continuar       |");
							continue operacao;
						}
					}
					
					//criação da nova array se estiver cheia, repetindo o loop depois de criada
					if(cheioM) {
						Medico[] novaArray = new Medico[arrayMedicos.length + 10];
						for (int i = 0; i < arrayMedicos.length; i++) {
							if (arrayMedicos[i] != null)
								novaArray[i] = arrayMedicos[i];
						}
						arrayMedicos = novaArray;
					}
				}
			//criar nova consulta
			case 3:
				//boolean e loop para aumentar array se estiver cheia
				boolean cheioC = true;
				while(true) {
					for (int i = 0; i < arrayConsultas.length; i++) {
						if (arrayConsultas[i] == null) {
							//leitura do id do paciente para associar a consulta ao paciente
							int idPaciente = Teclado.leInt("ID do paciente:");
							//criação da consulta em uma variável
							Consulta consulta = criaConsulta(idPaciente);
							
							for (int iP = 0; iP < arrayPacientes.length; i++) {
								if (arrayPacientes[iP] != null && arrayPacientes[iP].getId() == idPaciente) {
									//consulta sendo associada ao paciente com o mesmo id solicitado
									arrayPacientes[iP].getHistorico().adicionarConsulta(consulta);
									break;
								}
							}
							
							//consulta sendo colocada na primeira posição nula do array
							arrayConsultas[i] = consulta;
							
							cheioC = false;
							
							System.out.println("-------------------------------------------");
							System.out.println("|             Consulta criada!            |");
							System.out.println("-------------------------------------------");
							
							//impressão da consulta
							arrayConsultas[i].imprimir();
							
							System.out.println("-------------------------------------------");
							Teclado.leString("|       Aperte enter para continuar       |");							
							continue operacao;
						}
					}
					
					//criação da nova array se estiver cheia, repetindo o loop depois de criada
					if(cheioC) {
						Consulta[] novaArray = new Consulta[arrayConsultas.length + 10];
						for (int i = 0; i < arrayConsultas.length; i++) {
							if (arrayConsultas[i] != null)
								novaArray[i] = arrayConsultas[i];
						}
						arrayConsultas = novaArray;
					}
				}
			//acessar histórico do paciente
			case 4:
				//leitura do id do paciente
				int idAc = Teclado.leInt("Digite o ID do paciente:");
				
				//boolean para verificar se foi achado o id solicitado
				boolean idAchadoAc = false;
				
				for (int i = 0; i < arrayPacientes.length; i++) {
					if (arrayPacientes[i] != null && arrayPacientes[i].getId() == idAc) {
						idAchadoAc = true;
						//acessa o histórico do paciente e imprime o histórico se houver, ou imprime que não há se não tiver
						if (arrayPacientes[i].getHistorico() != null)
							acessarHistorico(arrayPacientes[i].getHistorico());
					}
				}
				
				//mensagem se o id não foi encontrado
				if (!(idAchadoAc))
					System.out.println("ID não encontrado!");
				
				System.out.println("-------------------------------------------");
				Teclado.leString("|       Aperte enter para continuar       |");
			
				break;
			//deletar consulta por id da consulta
			case 5:
				//impressão de todas as consultas disponíveis
				for (int i = 0; i < arrayConsultas.length; i++) {
					if (arrayConsultas[i] != null)
						arrayConsultas[i].imprimir();
				}
				
				int idConsultaDel = Teclado.leInt("Digite o ID da consulta a ser deletada");
				
				//deleção da consulta no histórico do paciente
				for (int i = 0; i < arrayPacientes.length; i++) {
					if (arrayPacientes[i] != null && arrayPacientes[i].getHistorico() != null) {
						for (int iCon = 0; iCon < arrayPacientes[i].getHistorico().getConsultas().length; iCon++) {
							if (arrayPacientes[i].getHistorico().getConsultas()[iCon] != null && arrayPacientes[i].getHistorico().getConsultas()[iCon].getIdConsulta() == idConsultaDel) {
								arrayPacientes[i].getHistorico().getConsultas()[iCon] = null;
							}
						}
					}
				}
				
				//deleção da consulta
				for (int i = 0; i < arrayConsultas.length; i++) {
					if (arrayConsultas[i].getIdConsulta() == idConsultaDel) {
						arrayConsultas[i] = null;
						break;
					}
				}
				
				System.out.println("-------------------------------------------");
				System.out.println("|           Consulta deletada!            |");
				System.out.println("-------------------------------------------");
				Teclado.leString("|       Aperte enter para continuar       |");
				break;
				
			//imprime os pacientes em ordem decrescente de idade para a vacinação
			case 6:
				
				//cria a array nova usando o método ordemVacinacao
				Paciente[] arrayVacinacao = ordemVacinacao(arrayPacientes);
				
				//verifica se há pacientes
				if (arrayVacinacao != null) {
					System.out.println("-------------------------------------------");
					System.out.println("            Ordem de vacinação             ");
					System.out.println("(Paciente mais velho ao paciente mais novo)");
					System.out.println("-------------------------------------------");
					
					//loop para imprimir os pacientes
					for (int i = 0; i < arrayVacinacao.length; i++) {
						if (arrayVacinacao[i] != null) {
							System.out.printf("Paciente n° %d\n", i+1);
							arrayVacinacao[i].imprimir();
							System.out.println("-------------------------------------------");
						}
					}
				}
				//se não há pacientes, imprime a mensagem que não há pacientes
				else {
					System.out.println("-------------------------------------------");
					System.out.println("|      Não há pacientes cadastrados!      |");
					System.out.println("-------------------------------------------");						
				}
				
				Teclado.leString("|       Aperte enter para continuar       |");
				
				break;
				
			//sair do programa (quebra o loop operação)	
			case 7:
				System.out.println("-------------------------------------------");
				System.out.println("|        Muito obrigado por usar o        |");
				System.out.println("|          Controle de Consultas!         |");
				System.out.println("-------------------------------------------");
				break operacao;
				
			//qualquer número fora do intervalo 1-7 para repetir o menu	
			default:
				System.out.println("-------------------------------------------");
				System.out.println("|     Opção inválida! Digite novamente    |");
			}
		}
	}
	
	//método para criar um paciente
	public static Paciente criaPaciente() {
		//gera um nome aleatório e imprime
		String nome = Utilitario.gerarNomeAleatorio();
		System.out.println("Nome do paciente: " + nome);
		
		//pede o cpf e verifica se tem 11 caracteres, para validar da forma correta
		String cpf = Teclado.leString("CPF do paciente:");
		while (cpf.length() != 11) {
			cpf = Teclado.leString("CPF inválido! Digite novamente:");
		} 
		cpf = Utilitario.validaCpf(cpf);
			
		//pede a idade e verifica se foi inserido uma idade
		int idade = Teclado.leInt("Idade do paciente:");
		while (idade == 0) {
			idade = Teclado.leInt("Idade inválida! Digite novamente");
		} 
		
		//pede o telefone e verifica se tem 11 caracteres, para validar da forma correta
		String telefone = Teclado.leString("Telefone do paciente:");
		while (telefone.length() != 11) {
			telefone = Teclado.leString("Telefone inválido! Digite novamente:");
		} 
		telefone = Utilitario.validaTelefone(telefone);
		
		//pede a profissão e verifica se foi inserido uma profissão
		String profissao = Teclado.leString("Profissão do paciente:");
		while (profissao.equals("")) {
			profissao = Teclado.leString("Profissão inválida! Digite novamente");
		} 
		
		//imprime as opções de escolha do plano de saúde, para selecionar na array da classe Utilitario
		System.out.println("Escolha o plano de saúde do paciente:");
		System.out.println("    1. Particular");
		System.out.println("    2. Convênio");
		System.out.println("    3. SUS");
		int pds = Teclado.leInt();
		//loop para validar se a opção está válida
		while (pds < 1 || pds > 3) {
			System.out.println("Opção inválida! Digite novamente:");
			System.out.println("    1. Particular");
			System.out.println("    2. Convênio");
			System.out.println("    3. SUS");
			pds = Teclado.leInt();
		} 

		//retorna uma instância do paciente criado
		return new Paciente(nome, cpf, idade, telefone, profissao, pds);
	}
	
	//método para criar um médico
	public static Medico criaMedico() {
		//gera um nome aleatório e imprime
		String nome = Utilitario.gerarNomeAleatorio();
		System.out.println("Nome do médico: " + nome);
		
		//pede o cpf e verifica se tem 11 caracteres, para validar da forma correta
		String cpf = Teclado.leString("CPF do médico:");
		while (cpf.length() != 11) {
			cpf = Teclado.leString("CPF inválido! Digite novamente:");
		} 
		cpf = Utilitario.validaCpf(cpf);
		
		//pede a idade e verifica se uma idade foi inserida
		int idade = Teclado.leInt("Idade do médico:");
		while (idade == 0) {
			idade = Teclado.leInt("Idade inválida! Digite novamente");
		} 
		
		//pede o telefone e verifica se tem 11 caracteres, para validar da forma correta
		String telefone = Teclado.leString("Telefone do médico:");
		while (telefone.length() != 11) {
			telefone = Teclado.leString("Telefone inválido! Digite novamente:");
		}
		telefone = Utilitario.validaTelefone(telefone);
		
		//pede a especialidade e verifica se foi inserido uma especialidade
		String especialidade = Teclado.leString("Especialidade do médico");
		while (especialidade.equals("")) {
			especialidade = Teclado.leString("Especialidade inválida! Digite novamente");
		} 

		//retorna uma instância do médico criado
		return new Medico(nome, cpf, idade, telefone, especialidade);
	}
	
	//método para criar uma consulta
	public static Consulta criaConsulta(int id) {
		//pede o motivo da consulta e verifica se um motivo foi inserido
		String motivo = Teclado.leString("Motivo da consulta");
		while (motivo.equals("")) {
			motivo = Teclado.leString("Motivo inválido! Digite novamente");
		} 
		
		//imprime as opções de escolha do exame, para selecionar na array da classe Utilitario
		System.out.println("Escolha o exame a ser realizado:");
		System.out.println("    1. Sangue");
		System.out.println("    2. Raio X");
		System.out.println("    3. Ecografia");
		System.out.println("    4. Tomografia");
		System.out.println("    5. Ressonância");
		int exame = Teclado.leInt();
		//loop para validar se a opção está válida
		while (exame < 1 || exame > 5) {
			System.out.println("Opção inválida! Digite novamente:");
			System.out.println("    1. Sangue");
			System.out.println("    2. Raio X");
			System.out.println("    3. Ecografia");
			System.out.println("    4. Tomografia");
			System.out.println("    5. Ressonância");
			exame = Teclado.leInt();
		} 
		
		//pede a data da consulta e verifica se uma data foi inserida
		String data = Teclado.leString("Data do exame: ");
		while (data.equals("")) {
			data = Teclado.leString("Data inválida! Digite novamente");
		} 
		
		//retorna uma instância da consulta criada
		return new Consulta(id, motivo, exame, data);
	}
	
	//imprime o histórico do parâmetro
	public static void acessarHistorico(Historico historico) {
		//boolean e loop para verificar se há consultas para imprimir ou não
		boolean háConsultas = false;
		for (int i = 0; i < historico.getConsultas().length; i++) {
			if (historico.getConsultas()[i] != null) {
				háConsultas = true;
			}
		}
		
		//imprime o histórico se há consultas (háConsultas = true), ou imprime que não há consultas se não houver (háConsultas = false)
		if (háConsultas)
			historico.imprimir();
		else
			System.out.println("Ainda não há consultas com este paciente!");
	}
	
	//método para retornar uma array com os pacientes em ordem decrescente de idade para a vacinação
	public static Paciente[] ordemVacinacao(Paciente[] arrayPacientes) {
		//cria a array do mesmo tamanho da array do parâmetro (array de pacientes)
		Paciente[] novaArray = new Paciente[arrayPacientes.length];
		
		//boolean e loop para inserir e verificar se inseriu algum paciente na nova array
		boolean inseriu = false;
		for (int i = 0; i < arrayPacientes.length; i++) {
			if (arrayPacientes[i] != null) {
				inseriu = true;
				novaArray[i] = arrayPacientes[i];
			}
		}
		
		//se não inseriu nenhum paciente, retorna null
		if (!(inseriu)) {
			return null;
		}
		
		//bubble sort para ordenar os pacientes em ordem decrescente
		for (int i = 0; i < novaArray.length; i++) {
			for (int j = 0; j < novaArray.length - i; j++) {
				if (novaArray[j] != null && novaArray[j+1] != null && novaArray[j].getIdade() < novaArray[j+1].getIdade()) {
					Paciente aux = novaArray[j];
					novaArray[j] = novaArray[j+1];
					novaArray[j+1] = aux;
				}
			}
		}
		
		//retorna a nova array
		return novaArray;		
	}
}
