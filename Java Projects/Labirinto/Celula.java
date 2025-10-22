
public class Celula {
	
	private char valor;
	private boolean visitada;
	
	public Celula(char valor) {
		this.valor = valor;
		this.visitada = false;
	}
	
	public Celula(char valor, boolean visitada) {
		this.valor = valor;
		this.visitada = visitada;
	}

	public char getValor() {
		return valor;
	}

	public void setValor(char valor) {
		this.valor = valor;
	}

	public boolean isVisitada() {
		return visitada;
	}

	public void setVisitada(boolean visitada) {
		this.visitada = visitada;
		if(visitada)
			this.valor = 'O';
	}
	
	
}
