
public class CaixaPostal {
	private String remetente;
	private Email[] caixaDeSaida;
	private Email[] caixaDeEntrada;
	
	public CaixaPostal(String remetente) {
		this.remetente = remetente;
		this.caixaDeSaida = new Email[20];
		this.caixaDeEntrada = new Email[20];
	}
	
	public void send(Email email) {
		for (int i = 0; i < this.caixaDeSaida.length; i++) {
			if (this.caixaDeSaida[i] == null) {
				this.caixaDeSaida[i] = email;
				break;
			}
		}
	}
	
	public void send(String destinatario, String assunto, String corpoEmail) {
		for (int i = 0; i < this.caixaDeSaida.length; i++) {
			if (this.caixaDeSaida[i] == null) {
				this.caixaDeSaida[i] = new Email(destinatario, assunto, corpoEmail);
				break;
			}
		}
	}
	
	public void receive(Email email) {
		for (int i = 0; i < this.caixaDeEntrada.length; i++) {
			if (this.caixaDeEntrada[i] == null) {
				this.caixaDeEntrada[i] = email;
				break;
			}
		}
	}
	
	public void showInbox() {
		System.out.println("INBOX " + this.remetente + ":");
		int contador = 1;
		
		for (int i = 0; i < this.caixaDeEntrada.length; i++) {
			if (this.caixaDeEntrada[i] != null) {
				System.out.println("E-mail n° " + contador++);
				System.out.println("- Assunto: " + this.caixaDeEntrada[i].getAssunto());
				System.out.println("- Corpo do email: " + this.caixaDeEntrada[i].getCorpoEmail());
				System.out.println();
			}
		}
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public Email[] getCaixaDeSaida() {
		return caixaDeSaida;
	}

	public void setCaixaDeSaida(Email[] caixaDeSaida) {
		this.caixaDeSaida = caixaDeSaida;
	}

	public Email[] getCaixaDeEntrada() {
		return caixaDeEntrada;
	}

	public void setCaixaDeEntrada(Email[] caixaDeEntrada) {
		this.caixaDeEntrada = caixaDeEntrada;
	}
	
}
