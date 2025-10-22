import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SimuladorEstacionamento {
	private Carro[][] garagem;
	
	public SimuladorEstacionamento() {
		this.garagem = new Carro[5][10];
	}

	public boolean adicionarCarro(Carro carro) {
		double chance = Math.random();
		if (chance < 0.9) {
			for (int i = 0; i < garagem.length; i++) {
				for (int j = 0; j < garagem[i].length; j++) {
					if (this.garagem[i][j] == null) {
						this.garagem[i][j] = carro;
						return true;
					}
				}
			}
		} else {
			for (int i = (int)(Math.random() * 5 + 1); i < garagem.length; i = (int)(Math.random() * 5 + 1)) {
				for (int j = 0; j < garagem[i].length; j++) {
					if (this.garagem[i][j] == null) {
						this.garagem[i][j] = carro;
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public String mostrarMapa() {
		String s = "";
		
		for (int i = this.garagem.length - 1; i >= 0; i--) {
			s += (i + 1) + ": ";
			
			for (int j = 0; j < this.garagem[i].length; j++) {
				if (this.garagem[i][j] != null) {
					s += "X ";
				} else {
					s += "- ";
				}
			}
			
			s += "\n";
		}
		
		return s;
	}
	
	public String execCiclo(int c) {
		String s = "";
		s += "Ciclo: " + c + "\n";
		double chance = Math.random();
		
		for (int i = 0; i < this.garagem.length; i++) {
			for (int j = 0; j < this.garagem[i].length; j++) {
				Carro carroAtual = this.garagem[i][j];
				if (carroAtual != null) {
					carroAtual.setDuracaoPermanencia(carroAtual.getDuracaoPermanencia() - 1);
					if (carroAtual.getDuracaoPermanencia() == 0) {
						s += "Carro ID " + carroAtual.getId() + " saiu\n";
						this.garagem[i][j] = null;
					}
				}
			}
		}
		
		if (chance < 0.2) {
			Carro carro = new Carro();
			adicionarCarro(carro);
			s += "Chegou um novo carro! (ID: " + carro.getId() + ", duracao permanencia: " + carro.getDuracaoPermanencia() + ")\n";
		}
		
		s += mostrarMapa();
		
		return s;
	}
	
	public void imprimirMapa(String cicloAtual, String nomeArquivo) {
		try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo, true))) {
			out.println(cicloAtual);
		} catch (IOException e) {
			System.out.println("Erro na impressão do mapa");
		}
		
	}
	
	public void imprimirMapaCsv(int c, String nomeArquivo) {
		try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo, true))) {
			
			String s = "";
			s += "Ciclo: " + c + "\n";
			
			for (int i = this.garagem.length - 1; i >= 0; i--) {
				s += (i + 1) + ":,";
				
				for (int j = 0; j < this.garagem[i].length; j++) {
					if (this.garagem[i][j] != null) {
						s += "X,";
					} else {
						s += "-,";
					}
				}
				
				s += "\n";
			}
			
			out.println(s);
		} catch (IOException e) {
			System.out.println("Erro na impressão do mapa");
		}
	}

	
}
