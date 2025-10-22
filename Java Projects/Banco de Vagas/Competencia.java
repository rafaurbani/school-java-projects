
public class Competencia {
	//atributos
	private String competencia;
	private int nivel;
	
	//construtor
	public Competencia(String competencia, int nivel) {
		this.competencia = competencia;
		this.nivel = nivel;
	}
	
	//getters e setters
	public String getCompetencia() {
		return competencia;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	//método toString que retorna String com a competência e o nível
	public String toString() {
		return String.format("- %s\n    - Nivel %d\n", this.competencia, this.nivel);
	}
}
