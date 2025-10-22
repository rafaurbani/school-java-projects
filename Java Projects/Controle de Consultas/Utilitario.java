public class Utilitario
{
	//atributos: arrays de exames e planos de saúde para serem escolhidos na classe Principal (exames = consulta / plano de saúde = paciente)
    public static String[] exames = {"Sangue", "Raio X", "Ecografia", "Tomografia", "Ressonância"};
    public static String [] planosDeSaude = {"Particular", "Convênio", "SUS"};

    //método para gerar um nome aleatório e retornar em uma String
    public static String gerarNomeAleatorio()
    {
    	//arrays com nomes e sobrenomes aleatórios
        String[] nomes = {"Alice", "Miguel", "Sophia", "Arthur", "Helena", "Bernardo", "Valentina", "Heitor", "Laura", "Davi", "Isabella", "Lorenzo", "Manuela", "Théo", "Júlia", "Pedro", "Heloísa", "Gabriel", "Luiza", "Enzo", "Maria Luiza", "Matheus", "Lorena", "Lucas", "Lívia", "Benjamin", "Giovanna", "Nicolas", "Maria Eduarda", "Guilherme", "Beatriz", "Rafael", "Maria Clara", "Joaquim", "Cecília", "Samuel", "Eloá", "Enzo Gabriel", "Lara", "João Miguel", "Maria Júlia", "Henrique", "Isadora", "Gustavo", "Mariana", "Murilo", "Emanuelly", "Pedro Henrique", "Ana Júlia", "Pietro", "Ana Luiza", "Lucca", "Ana Clara", "Felipe", "Melissa", "João Pedro", "Yasmin", "Isaac", "Maria Alice", "Benício", "Isabelly", "Daniel", "Lavínia", "Anthony", "Esther", "Leonardo", "Sarah", "Davi Lucca", "Elisa", "Bryan", "Antonella", "Eduardo", "Rafaela", "João Lucas", "Maria Cecília", "Victor", "Liz", "João", "Marina", "Cauã", "Nicole", "Antônio", "Maitê", "Vicente", "Isis", "Caleb", "Alícia", "Gael", "Luna", "Bento", "Rebeca", "Caio", "Agatha", "Emanuel", "Letícia", "Vinícius", "Maria", "João Guilherme", "Gabriela", "Davi Lucas"};
        String[] sobrenomes = {"Silva", "Souza", "Costa", "Santos", "Oliveira", "Pereira", "Rodrigues", "Almeida", "Nascimento", "Lima", "Araújo", "Fernandes", "Carvalho", "Gomes", "Martins", "Rocha", "Ribeiro", "Alves", "Monteiro", "Mendes", "Barros", "Freitas", "Barbosa", "Pinto", "Moura", "Cavalcanti", "Dias", "Castro", "Campos", "Cardoso"};

        //retorno do nome completo
        return String.format("%s %s", nomes[(int)(Math.random() * 100)], sobrenomes[(int)(Math.random() * 30)]);
    }

    //método para validar o cpf e retornar no formato correto
    public static String validaCpf(String cpf)
    {
        return String.format("%s.%s.%s-%s", cpf.substring(0, 3), cpf.substring(3, 6), cpf.substring(6, 9), cpf.substring(9, 11));
    }
    
    //método para validar o telefone e retornar no formato correto
    public static String validaTelefone(String telefone)
    {
        return String.format("(%s) %s-%s", telefone.substring(0, 2), telefone.substring(2, 7), telefone.substring(7, 11));
    }
}