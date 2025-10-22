
public class Main {
	public static void main(String[] args) {
		Restaurante rest = new Restaurante();
		
		//500 ciclos, cria um grupo sa cada 10
		for (int i = 0; i < 500; i++) {
			GrupoClientes grupo = null;
			
			if (i == 0 || i % 10 == 0) {
				grupo = new GrupoClientes();
			}
			
			System.out.println("CICLO " + (i + 1) + ":");
			System.out.println(rest.ciclo(grupo));
		}
	}
}
