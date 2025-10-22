public class GrupoClientes {
	private int tamanho;
	private Pedido pedido;
	private int numCicloPagamento; //ciclo do momento que o grupo esta no caixa, e diminui a cada ciclo
	private int numCicloRefeicao; //ciclo de quando comecam a comer, e vai até 0
	private boolean estaComendo; //serve para se for true, diminui o ciclo de refeicao
	
	public GrupoClientes(int tamanho) {
		if (tamanho > 0 && tamanho < 5)
			this.tamanho = tamanho;
		
		this.pedido = new Pedido();
		
		this.numCicloPagamento = (int)(Math.random() * 16 + 5); // sorteia de 5 a 20
		this.numCicloRefeicao = (int)(Math.random() * 41 + 80); // sorteia de 80 a 120
		this.estaComendo = false;
	}
	
	//cria um grupo de tamanho aleatorio
	public GrupoClientes() {
		this((int)(Math.random() * 4 + 1));
	}

	public int getTamanho() {
		return tamanho;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	public int getIdPedido() {
		return pedido.getId();
	}

	public boolean isEstaComendo() {
		return estaComendo;
	}

	public void setEstaComendo(boolean estaComendo) {
		this.estaComendo = estaComendo;
	}

	public int getNumCicloPagamento() {
		return numCicloPagamento;
	}
	
	public int diminuiCicloPagamento() {
		this.numCicloPagamento--;
		return this.numCicloPagamento;
	}

	public int getNumCicloRefeicao() {
		return numCicloRefeicao;
	}
	
	public int diminuiCicloRefeicao() {
		if (isEstaComendo())
			this.numCicloRefeicao--;
		
		return this.numCicloRefeicao;
	}
}
