
public class Servidor {
	private CaixaPostal[] vetorCaixasPostais;
	
	public Servidor() {
		this.vetorCaixasPostais = new CaixaPostal[15];
	}
	
	public void add(CaixaPostal cp) {
		for (int i = 0; i < this.vetorCaixasPostais.length; i++) {
			if (this.vetorCaixasPostais[i] == null) {
				this.vetorCaixasPostais[i] = cp;
				break;
			}
		}
	}
	
	public void sendReceive() {
		loop:for (int c = 0; c < this.vetorCaixasPostais.length; c++) {
			if (this.vetorCaixasPostais[c] != null) {
				for (int s = 0; s < this.vetorCaixasPostais[c].getCaixaDeSaida().length; s++) {
					if (this.vetorCaixasPostais[c].getCaixaDeSaida()[s] != null) {
						Email email = this.vetorCaixasPostais[c].getCaixaDeSaida()[s];
						for (int d = 0; d < this.vetorCaixasPostais.length; d++) {
							if (this.vetorCaixasPostais[d].getRemetente() == email.getDestinatario()) {
								this.vetorCaixasPostais[d].receive(email);
								this.vetorCaixasPostais[c].getCaixaDeSaida()[s] = null;
								continue loop;
							}
						}
					}
				}
			}
		}
	}
}
