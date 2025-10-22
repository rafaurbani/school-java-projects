
public class Main {

	public static void main(String[] args) {
		//cria a planilha
		Planilha p = new Planilha(30, 40);
		
		//cria células de valor e string nas células dos parâmetros
		p.setCel(2.3 , 1 , 4);
		p.setCel(10.0, 4 , 2); 
		p.setCel("Contabilidade", 1 , 1);
		
		//cria duas fórmulas e insere nas células correspondentes
		p.setCel(new Formula('+', 1, 4, 4, 2), 2, 2);
		p.setCel(new Formula('+', 2, 2, 4, 2), 3, 3);
		
		//formula de erro
		p.setCel(new Formula('+', 1, 1, 4, 1), 4, 1);
		
		//mostra a planilha
		p.mostraPlan(1, 1, 4, 4);
		
		p.setCel(25.0, 4 , 2); 
		
		//salva a planilha
		p.salvaPlan("planilha.db");
		
		//limpa as linhas e colunas dos parâmetros
		p.limpaCels(1, 1, 4, 4);
		
		//mostra a planilha vazia
		p.mostraPlan(1, 1, 4, 4);
		
		//lê a planilha gravada anteriormente
		p.lePlanilha("planilha.db");
		
		//imprime a planilha recém lida
		p.mostraPlan(1, 1, 4, 4);
	}
}
