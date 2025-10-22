
public class Main {

	public static void main(String[] args) {
		Labirinto lab = new Labirinto();
		
		lab.leLabirinto();
		lab.imprimeLabirinto();
		
		if (!lab.procuraSaida(12, 13))
			System.out.println("Não achou a saída");
		
		lab.imprimeLabirinto();
	}

}
