
public class Cadastro {
	private Peca[] pecas;
	
	public Cadastro() {
		this.pecas = new Peca[100];
	}
	
	public void inserePeca(Peca peca) {
		inserePeca(peca, 0);
	}
	
	public void inserePeca(Peca peca, int i) {
		if (this.pecas[i] != null)
			inserePeca(peca, ++i);
		else
			this.pecas[i] = peca;
	}
	
	public void mostraPecas() {
		for (int i = 0; i < this.pecas.length; i++) {
			if (this.pecas[i] != null)
				System.out.println(this.pecas[i]);
		}
	}
	
	public void ordenaPecas() {
		for (int i = 0; i < this.pecas.length; i++) {
			if (this.pecas[i] == null) {
				for (int j = i+1; j < this.pecas.length; j++) {
					if (this.pecas[j] != null) {
						this.pecas[i] = this.pecas[j];
						this.pecas[j] = null;
						break;
					}
				}
				break;
			}
			
			int menorPos = i;
			
			for (int j = i + 1; j < this.pecas.length; j++) {
				if (this.pecas[j] != null) {
					if (this.pecas[j].getUnidsEmEstoque() < this.pecas[i].getUnidsEmEstoque())
						menorPos = j;
					else if (this.pecas[j].getUnidsEmEstoque() == this.pecas[i].getUnidsEmEstoque()) {
						if (this.pecas[j].getPrecoUnit() < this.pecas[i].getPrecoUnit())
							menorPos = j;
						else if (this.pecas[j].getPrecoUnit() == this.pecas[i].getPrecoUnit()) {
							if (this.pecas[j].getCod() < this.pecas[i].getCod())
								menorPos = j;
						}
					}
				}
			}
			
			Peca aux = this.pecas[i];
			this.pecas[i] = this.pecas[menorPos];
			this.pecas[menorPos] = aux;
		}
	}
}
