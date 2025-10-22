
public class Main {
	public static void main(String[] args) {
		//cria matriz de 100x100, contendo 10000 células
		MatrizEsparsa me = new MatrizEsparsa (100, 100);
		
		//sorteia 50 números para entrar na matriz esparsa
		for(int x=0; x<50; x++) {
			int sLin = (int)(Math.random() * 100);
			int sCol = (int)(Math.random() * 100);
			double sVal = Math.random() * 1000;
					
			me.set(sVal, sLin, sCol);
		}
		
		//define a célula 0, 0 em 999.99, e imprime
		me.set(999.99, 0, 0);
		System.out.println("Célula 0, 0 = " + me.get(0, 0));
		
		//define a célula 99, 99 em -999.99, e imprime
		me.set(-999.99, 99, 99);
		System.out.println("Célula 99, 99 = " + me.get(99, 99));
		
		System.out.println();
		
		//imprime a matriz esparsa completa
		me.imprimeMatriz();
		System.out.println();
		
		//mostra o total de elementos diferente de zero na matriz esparsa
		System.out.println("Não zeros: " + me.getNaoZeros());
		
		//métodos para teste
		System.out.println("Somatorio = " + me.soma());
		System.out.println("Menor número: " + me.min());
		System.out.println("Maior número: " + me.max());
		System.out.println("Existe o número 999.99: " + me.exist(999.99));
		System.out.println("Média coluna: " + me.coluna(0));
		System.out.println("Média linha: " + me.linha(99));
		
		//cópia que imprime a última célula (-999.99)
		double[][] matrizCopia = me.copia(90, 90, 10, 10);
		System.out.println("Última célula da cópia: " + matrizCopia[9][9]);
		
		//faz a matriz transposta e imprime
		System.out.println("Matriz transposta: ");
		MatrizEsparsa meTransposta = me.transposta(me);
		meTransposta.imprimeMatriz();
		
		//métodos de armazenar matriz em arquivo e de carregar arquivo para matriz
		System.out.println();
		me.armazena("matriz.db");
		me.carrega("matriz.db");
	}
}
