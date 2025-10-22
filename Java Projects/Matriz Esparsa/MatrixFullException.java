//classe da exceção de quando passar da porcentagem de não zeros permitidos (padrão: 1%)

@SuppressWarnings("serial")
public class MatrixFullException extends Exception {
	
	private int porcentagem;
	
	public MatrixFullException() {
		this.porcentagem = 1;
	}
	
	public MatrixFullException(int p) {
		this.porcentagem = p;
	}
	
	public String toString() {
		return "Matriz cheia: valor passou de " + this.porcentagem + "%";
	}
}
