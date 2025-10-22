
public class DirDoesNotExistException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String message = "ERRO: pasta não existe";
	
	//excecao de arquivo/pasta não existe
	public DirDoesNotExistException() {
	}
	
	public String getMessageDir() {
		return message;
	}
}
