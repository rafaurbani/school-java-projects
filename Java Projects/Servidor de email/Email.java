
public class Email {
	private String remetente, destinatario, assunto, corpoEmail;
	
	public Email(String destinatario, String assunto, String corpoEmail) {
		this.destinatario = destinatario;
		this.assunto = assunto;
		this.corpoEmail = corpoEmail;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpoEmail() {
		return corpoEmail;
	}

	public void setCorpoEmail(String corpoEmail) {
		this.corpoEmail = corpoEmail;
	}
	
	
}
