import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Municipio {
	private Cidade[] municipio;
	
	public Municipio(int tamanho) {
		this.municipio = new Cidade[tamanho];
	}
	
	public boolean insereCid(Cidade cidade) {
		for (int i = 0; i < this.municipio.length; i++) {
			if (this.municipio[i] == null) {
				this.municipio[i] = cidade;
				return true;
			}
		}
		
		return false;
	}
	
	public String mostraMunicipio() {
		String s = "Municípios: \n";
		
		for (int i = 0; i < this.municipio.length; i++) {
			if (this.municipio[i] != null) {
				s += "- " + this.municipio[i].getNome() + "\n";
			}
		}
		
		return s;
	}
	
	public void removeCid(String nome) {
		for (int i = 0; i < this.municipio.length; i++) {
			if (this.municipio[i] != null && this.municipio[i].getNome().equalsIgnoreCase(nome)) {
				this.municipio[i] = null;
				break;
			}
		}
	}
	
	public void gravaRelatorio(String nomeArquivo) {
		try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
			for (int i = 0; i < this.municipio.length; i++) {
				if (this.municipio[i] != null) {
					out.println("Cidade: " + this.municipio[i].getNome());
					out.println("Edificacoes: " + this.municipio[i].getEdificacoes());
					out.println("IPTU: " + this.municipio[i].calcIptuRegiao());
					out.println("");
				}
			}
			
		} catch (IOException e) {
			System.out.println("Erro na gravação do arquivo");
		}
	}
}
