
public class Main {

	public static void main(String[] args) {
		SimuladorEstacionamento sim = new SimuladorEstacionamento();
		
		for (int i = 1; i <= 200; i++) {
			String ciclo = sim.execCiclo(i);
			System.out.println(ciclo);
			if (i == 1 || i % 10 == 0)
				sim.imprimirMapa(ciclo, "ciclos.txt");
			
			if (i % 5 == 0) {
				sim.imprimirMapaCsv(i, "ciclos.csv");
			}
		}
	}

}
