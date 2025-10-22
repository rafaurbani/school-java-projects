
public class FileDoesNotExistException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String message = "ERRO: arquivo não existe";
	
	//excecao de arquivo não existe
	public FileDoesNotExistException() {
	}
	
	public String getMessage() {
		return message;
	}
}
