
public class InvalidCommandException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String message = "ERRO: comando inválido";
	
	//excecao de comando inválido
	public InvalidCommandException() {
	}
	
	public String getMessage() {
		return message;
	}
}
