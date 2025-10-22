public class Main {
	//método main
	public static void main(String[] args) {
		//cria uma instância do BancoDeVagas
		BancoDeVagas banco = new BancoDeVagas();
		
		//gera 15 novas vagas e 15 novos candidatos, e adiciona no banco
		for (int i = 0; i < 15; i++) {
			banco.adicionaVaga(geraVagaAleatoria());
			banco.adicionaCandidato(geraCandidatoAleatorio());
		}
		
		//gera uma vaga de gestor e uma competencia para testar posteriormente (id da vaga será 16)
		VagaGestor vaga = new VagaGestor("Vaga de Gestor - VAGA TESTE", 5000);
		Competencia comp = geraCompetenciaAleatoria();
		
		//adiciona a competencia na vaga, a vaga no banco, e imprime a vaga
		vaga.alteraReqMetodologias(comp);
		banco.adicionaVaga(vaga);
		System.out.println(vaga);
		System.out.printf("-------------------------------------------------\n\n");
		
		//gera um candidato com a competência gerada para testar posteriormente (id do candidato será 16)
		Candidato candidato = new Candidato("Fulano da Silva", (new Data(05, 02, 2003)));
		candidato.alteraCompetencias(comp);
		candidato.setExpSalario(3000);
		
		//adiciona o candidato no banco e imprime (o primeiro candidato a ser impresso)
		banco.adicionaCandidato(candidato);
		System.out.println(candidato);
		System.out.printf("-------------------------------------------------\n\n");
		
		//lista todos os candidatos e todas as vagas do banco
		banco.listaCandidatos();
		banco.listaVagas();
		
		//filtra as vagas com a expectativa salarial igual ou maior que 3000
		banco.listaVagasFiltro(3000);
		//filtra as vagas com a competência gerada anteriormente (que vai imprimir apenas a vaga de gestor gerada separadamente, e alguma outra vaga se tiver sorte e forem geradas iguais)
		banco.listaVagasFiltro(comp);
		
		//lista as vagas para o candidato gerado anteriormente (com o id 16 da vaga gerada)
		banco.listaVagasParaCandidato(16);
		//lista os candidatos (com o id 16 do candidato gerado) (vai imprimir o candidato criado anteriormente, e alguma outra vaga se tiver sorte e forem geradas iguais)
		banco.listaCandidatosParaVaga(16);
		
		//contrata o candidato do id 16 para a vaga do id 16, e imprime se foi inserido corretamente (true)
		System.out.println("Candidato contratado: " + banco.contratarCandidato(16, 16));
		System.out.println();
		
		//lista os funcionários (apenas o candidato do id 16, que agora tem o id 17)
		banco.listaFuncionarios();
		
	}
	
	//cria e retorna uma competência aleatória
	public static Competencia geraCompetenciaAleatoria() {
		//gera um nível de 1 a 3 aleatoriamente
		int nivel = (int)(Math.random() * 3 + 1);		
		
		//lista com competências aleatórias
		String[] listaCompetencias = {"Conhecimento de banco de dados", "Compreendimento de HTML, CSS e JavaScript ", "Conhecimento de ferramentas de controle de versão", "Experiência na escrita de documentos", "Agilidade e flexibilidade", "Habilidades de comunicação", "Conhecimento de lógica de programação", "Conhecimento de back-end", "Conhecimento de front-end", "Compreender diferentes tipos de linguagem", "Saber inglês", "Ter foco e persistência", "Analisar e resolver problemas", "Virtualização", "Flexibilidade", "Trabalho em equipe", "Manutenção da carreira", "Desenvolvimento pessoal", "Vendas e marketing", "Desenvolvimento móvel", "Segurança online", "Processos de pensamento empresarial"};
		
		//gera uma competência de forma aleatória da lista de competências
		String competencia = listaCompetencias[(int)(Math.random() * listaCompetencias.length)];
		
		//retorna uma nova instância de competencia
		return new Competencia(competencia, nivel);
		
	}
	
	//cria e retorna uma vaga aleatória
	public static Vaga geraVagaAleatoria() {
		//gera um número (1 ou 2) para determinar o tipo da vaga (1 - desenvolvedor / 2 - gestor)
		int dOuG = (int)(Math.random() * 2 + 1);
		
		//gera um salário aleatório de 500 a 5000 reais
		int salario = (int)((Math.random() * 10 + 1)* 500);
		
		//if para determinar o tipo de vaga de acordo com a variável dOuG
		if (dOuG == 1) {
			//vaga de desenvolvedor
			
			//cria as arrays das competências da vaga com tamanho de 10 competencias para cada (caso alguma competencia for adiciona depois)
			Competencia[] reqLinguagens = new Competencia[10];
			Competencia[] reqBancosDeDados = new Competencia[10];
			
			//gera as competencias aleatórias e insere nas arrays (três para cada array)
			for (int i = 0; i < 3; i++) {
				reqLinguagens[i] = geraCompetenciaAleatoria();
				reqBancosDeDados[i] = geraCompetenciaAleatoria();
			}
			
			//retorna uma nova instancia de vaga
			return new VagaDesenvolvedor("Vaga de Desenvolvedor", salario, reqLinguagens, reqBancosDeDados);
			
		} else if (dOuG == 2) {
			//vaga de gestor
			
			
			//cria a array das competências da vaga com tamanho de 10 competencias (caso alguma competencia for adiciona depois)
			Competencia[] reqMetodologias = new Competencia[10];
			
			//gera as competencias aleatórias e insere na array
			for (int i = 0; i < 3; i++) {
				reqMetodologias[i] = geraCompetenciaAleatoria();
			}
			
			//retorna uma nova instancia de vaga
			return new VagaGestor("Vaga de Gestor", salario, reqMetodologias);
		}
		
		//caso ocorra algum erro na hora de gerar o número da variável dOuG, retorna uma vaga padrão de salário 1000 (praticamente impossível acontecer o erro, mas caso ocorra, não retorna null)
		return new Vaga("Vaga", 1000);
	}
	
	//cria e retorna um candidato aleatória
	public static Candidato geraCandidatoAleatorio() {
		//arrays com nomes e sobrenomes aleatórios para gerar nome aleatório
        String[] nomes = {"Alice", "Miguel", "Sophia", "Arthur", "Helena", "Bernardo", "Valentina", "Heitor", "Laura", "Davi", "Isabella", "Lorenzo", "Manuela", "Théo", "Júlia", "Pedro", "Heloísa", "Gabriel", "Luiza", "Enzo", "Maria Luiza", "Matheus", "Lorena", "Lucas", "Lívia", "Benjamin", "Giovanna", "Nicolas", "Maria Eduarda", "Guilherme", "Beatriz", "Rafael", "Maria Clara", "Joaquim", "Cecília", "Samuel", "Eloá", "Enzo Gabriel", "Lara", "João Miguel", "Maria Júlia", "Henrique", "Isadora", "Gustavo", "Mariana", "Murilo", "Emanuelly", "Pedro Henrique", "Ana Júlia", "Pietro", "Ana Luiza", "Lucca", "Ana Clara", "Felipe", "Melissa", "João Pedro", "Yasmin", "Isaac", "Maria Alice", "Benício", "Isabelly", "Daniel", "Lavínia", "Anthony", "Esther", "Leonardo", "Sarah", "Davi Lucca", "Elisa", "Bryan", "Antonella", "Eduardo", "Rafaela", "João Lucas", "Maria Cecília", "Victor", "Liz", "João", "Marina", "Cauã", "Nicole", "Antônio", "Maitê", "Vicente", "Isis", "Caleb", "Alícia", "Gael", "Luna", "Bento", "Rebeca", "Caio", "Agatha", "Emanuel", "Letícia", "Vinícius", "Maria", "João Guilherme", "Gabriela", "Davi Lucas"};
        String[] sobrenomes = {"Silva", "Souza", "Costa", "Santos", "Oliveira", "Pereira", "Rodrigues", "Almeida", "Nascimento", "Lima", "Araújo", "Fernandes", "Carvalho", "Gomes", "Martins", "Rocha", "Ribeiro", "Alves", "Monteiro", "Mendes", "Barros", "Freitas", "Barbosa", "Pinto", "Moura", "Cavalcanti", "Dias", "Castro", "Campos", "Cardoso"};

        //forma um nome completo
        String nome = String.format("%s %s", nomes[(int)(Math.random() * 100)], sobrenomes[(int)(Math.random() * 30)]);
    
        //cria uma instancia de data gerada de forma aleatória (dias até 29 para não acontecer de existir um 30/02)
        Data data = new Data((int)(Math.random() * 29 + 1), (int)(Math.random() * 12 + 1), (int)(Math.random() * 30 + 1970));
        
        //gera um salário aleatório de 500 a 5000 reais
		int salario = (int)((Math.random() * 10 + 1)* 500);
		 
		//cria a array das competências da vaga com tamanho de 10 competencias (caso alguma competencia for adiciona depois)
		Competencia[] competencias = new Competencia[10];
			
		//gera as competencias aleatórias e insere na array
		for (int i = 0; i < 5; i++) {
			competencias[i] = geraCompetenciaAleatoria();
		}
		
		//retorna uma nova instância de candidato
		return new Candidato(nome, data, competencias, salario);
	}

}
