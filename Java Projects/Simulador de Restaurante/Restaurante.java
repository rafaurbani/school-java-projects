import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class Restaurante {
	//cozinha
	private Queue<Pedido> pedidosEspera;
	private List<Pedido> pedidosEmPreparo;
	private Queue<Pedido> pedidosProntos;

	//filas de pagamento
	private Queue<GrupoClientes> filaPagamento1;
	private Queue<GrupoClientes> filaPagamento2;
	
	//caixas
	private GrupoClientes caixa1;
	private GrupoClientes caixa2;
	
	//filas para mesas/balcao
	private Queue<GrupoClientes> filaBalcao;
	private Queue<GrupoClientes> filaMesasPara2;
	private Queue<GrupoClientes> filaMesasPara4;
	
	//mesas/balcao
	private List<GrupoClientes> balcao;
	private List<GrupoClientes> mesasPara2;
	private List<GrupoClientes> mesasPara4;
	
	public Restaurante() {
		this.pedidosEspera = new ArrayDeque<Pedido>();
		this.pedidosEmPreparo = new ArrayList<Pedido>();
		this.pedidosProntos = new ArrayDeque<Pedido>();
		
		this.filaPagamento1 = new ArrayDeque<GrupoClientes>();
		this.filaPagamento2 = new ArrayDeque<GrupoClientes>();
		
		this.filaBalcao = new ArrayDeque<GrupoClientes>();
		this.filaMesasPara2 = new ArrayDeque<GrupoClientes>();
		this.filaMesasPara4 = new ArrayDeque<GrupoClientes>();
		
		this.balcao = new ArrayList<GrupoClientes>();
		this.mesasPara2 = new ArrayList<GrupoClientes>();
		this.mesasPara4 = new ArrayList<GrupoClientes>();
	}
	
	//faz o ciclo completo e retorna as informacoes para impressa
	public String ciclo(GrupoClientes grupo) {
		if (grupo != null)
			entraFilaPagamento(grupo);
		
		cicloCaixas();
		entraMesas();
		cicloPedidos();
		saiDaMesa();
		diminuiCiclos();
		
		return impressaoCiclo();
	}
	
	//quando o grupo chega no restaurante, coloca na fila do pagamento
	public void entraFilaPagamento(GrupoClientes grupo) {
		System.out.println("-> Um novo grupo chegou no restaurante");
		if (this.filaPagamento1.size() <= this.filaPagamento2.size() && this.caixa2 != null) {
			this.filaPagamento1.add(grupo);
		} else {
			this.filaPagamento2.add(grupo);
		}
	}
	
	//faz o ciclo dos caixas - se o ciclo do pagamento for 0, mada para as filas das mesas
	//se o caixa estiver vazio, coloca o proximo da fila de pagamento
	public void cicloCaixas() {
		if (caixa1 != null && caixa1.getNumCicloPagamento() == 0) {
			entraFilaMesas(caixa1);
			System.out.println("-> Caixa 1: Pagamento concluído");
			caixa1 = null;
		}
		
		if (caixa2 != null && caixa2.getNumCicloPagamento() == 0) {
			entraFilaMesas(caixa2);
			System.out.println("-> Caixa 2: Pagamento concluído");
			caixa2 = null;
		}
		
		if (caixa1 == null)
			caixa1 = this.filaPagamento1.poll();
		if (caixa2 == null) 
			caixa2 = this.filaPagamento2.poll();
	}
	
	//coloca os grupos que pagaram nas filas das mesas, e envia o pedido para a lista dos pedidos em espera
	public void entraFilaMesas(GrupoClientes grupo) {
		if (grupo.getTamanho() == 1)
			this.filaBalcao.add(grupo);
		else if (grupo.getTamanho() == 2)
			this.filaMesasPara2.add(grupo);
		else if (grupo.getTamanho() == 3 || grupo.getTamanho() == 4)
			this.filaMesasPara4.add(grupo);
		
		this.pedidosEspera.add(grupo.getPedido());
	}
	
	//coloca os grupos das filas nas mesas, com os limites estabelecidos
	public void entraMesas() {		
		while (this.balcao.size() < 6) {
			GrupoClientes grupo = this.filaBalcao.poll();
			
			if (grupo == null)
				break;
			else {
				this.balcao.add(grupo);
				System.out.println("-> Uma pessoa sentou no balcão");
			}
		}
		
		while (this.mesasPara2.size() < 4) {
			GrupoClientes grupo = this.filaMesasPara2.poll();
			
			if (grupo == null)
				break;
			else {
				this.mesasPara2.add(grupo);
				System.out.println("-> Um grupo sentou na mesa de 2 pessoas");
			}
		}
		
		while (this.mesasPara4.size() < 4) {
			GrupoClientes grupo = this.filaMesasPara4.poll();
			
			if (grupo == null)
				break;
			else {
				this.mesasPara4.add(grupo);
				System.out.println("-> Um grupo sentou na mesa de 4 pessoas");
			}
		}		
	}
	
	//faz o ciclo dos pedidos
	public void cicloPedidos() {
		//iteracao dos pedidos em preparo
		Iterator<Pedido> it = this.pedidosEmPreparo.iterator(); 
		
		//verifica os pedidos que estao no ciclo 0. se sim, manda para a fila dos pedidos prontos
		while (it.hasNext()) {
			Pedido pedido = it.next();
			if (pedido.getTempoDePreparo() == 0) {
				System.out.println("-> Um novo pedido está pronto para ser entregue");
				this.pedidosProntos.add(pedido);
				it.remove();
			}
		}
		
		//adiciona os pedidos na fila para os pedidos em preparo, com limite de 3
		while (this.pedidosEmPreparo.size() < 3) {
			Pedido pedido = this.pedidosEspera.poll();
			if (pedido == null)
				break;
			else
				this.pedidosEmPreparo.add(pedido);
		}
		
		//iteracao dos pedidos e das mesas/balcao para entregar o pedido na mesa
		//se os ids sao iguais, coloca o está comendo em true para comecar a contagem do ciclo
		Iterator<Pedido> itPedidos = this.pedidosProntos.iterator();
		Iterator<GrupoClientes> itBalcao = this.balcao.iterator();
		Iterator<GrupoClientes> itM2 = this.mesasPara2.iterator();
		Iterator<GrupoClientes> itM4 = this.mesasPara4.iterator();
		
		//procura ate nao achar mais pedidos para serem entregues
		while (itPedidos.hasNext()) {
			Pedido pedido = itPedidos.next();
			
			while (itBalcao.hasNext()) {
				GrupoClientes grupo = itBalcao.next();
				if (grupo.getIdPedido() == pedido.getId()) {
					System.out.println("-> Uma pessoa no balcão começou a refeição");
					grupo.setEstaComendo(true);
					itPedidos.remove();
				}
			}
			
			while (itM2.hasNext()) {
				GrupoClientes grupo = itM2.next();
				if (grupo.getIdPedido() == pedido.getId()) {
					System.out.println("-> Um grupo na mesa de 2 pessoas começou a refeição");
					grupo.setEstaComendo(true);
					itPedidos.remove();
				}
			}
			
			while (itM4.hasNext()) {
				GrupoClientes grupo = itM4.next();
				if (grupo.getIdPedido() == pedido.getId()) {
					System.out.println("-> Um grupo na mesa de 4 pessoas começou a refeição");
					grupo.setEstaComendo(true);
					itPedidos.remove();
				}
			}
		}
	}
	
	//verifica os grupos com o ciclo no 0 e retira da mesa/balcao
	public void saiDaMesa() {
		//faz um iterator de todos
		Iterator<GrupoClientes> itBalcao = this.balcao.iterator();
		Iterator<GrupoClientes> itM2 = this.mesasPara2.iterator();
		Iterator<GrupoClientes> itM4 = this.mesasPara4.iterator();
		
		//whiles pra procurar os grupos com o ciclo no 0 e remove os que tem
		while (itBalcao.hasNext()) {
			GrupoClientes grupo = itBalcao.next();
			if (grupo.getNumCicloRefeicao() == 0) {
				System.out.println("-> Uma pessoa saiu do balcão");
				itBalcao.remove();
			}
		}
		
		while (itM2.hasNext()) {
			GrupoClientes grupo = itM2.next();
			if (grupo.getNumCicloRefeicao() == 0) {
				System.out.println("-> Um grupo saiu da mesa de 2 pessoas");
				itM2.remove();
			}
		}
		
		while (itM4.hasNext()) {
			GrupoClientes grupo = itM4.next();
			if (grupo.getNumCicloRefeicao() == 0) {
				System.out.println("-> Um grupo saiu da mesa de 4 pessoas");
				itM4.remove();
			}
		}
	}
	
	//diminui todos os ciclos (caixas, pedidos e refeicoes)
	public void diminuiCiclos() {
		if (caixa1 != null)
			caixa1.diminuiCicloPagamento();
		if (caixa2 != null)
			caixa2.diminuiCicloPagamento();
		
		//diminui pedidos com for indo em cada item da arraylist
		for (Pedido pedido : this.pedidosEmPreparo) {
			pedido.diminuiTempoDePreparo();
		}
		
		for (GrupoClientes grupo : this.balcao) {
			grupo.diminuiCicloRefeicao();
		}
		
		for (GrupoClientes grupo : this.mesasPara2) {
			grupo.diminuiCicloRefeicao();
		}
		
		for (GrupoClientes grupo : this.mesasPara4) {
			grupo.diminuiCicloRefeicao();
		}
	}
	
	//retorna as informacoes para impressao
	public String impressaoCiclo() {
		String s = "\n";
		
		s += "CAIXAS: \n";
		s += "\tCaixa 1: ";
		if (caixa1 == null)
			s += "VAZIO\n";
		else
			s += "OCUPADO\n";
		
		s += "\tCaixa 2: ";
		if (caixa2 == null)
			s += "VAZIO\n\n";
		else
			s += "OCUPADO\n\n";
		
		s += "TAMANHO DAS FILAS: \n";
		s += "Filas dos caixas:\n";
		s += "\tFila do caixa 1: " + this.filaPagamento1.size() + " grupo(s)\n";
		s += "\tFila do caixa 2: " + this.filaPagamento2.size() + " grupo(s)\n\n";
		
		s += "Fila das mesas/balcão:\n";
		s += "\tFila do balcão: " + this.filaBalcao.size() + " grupo(s)\n";
		s += "\tFila das mesas para 2 pessoas: " + this.filaMesasPara2.size() + " grupo(s)\n";
		s += "\tFila das mesas para 4 pessoas: " + this.filaMesasPara4.size() + " grupo(s)\n\n";
		
		s += "GRUPOS NAS MESAS/BALCÃO:\n";
		s += "\tBalcão: " + this.balcao.size() + " grupo(s)\n";
		s += "\tMesas para 2 pessoas: " + this.mesasPara2.size() + " grupo(s)\n";
		s += "\tMesas para 4 pessoas: " + this.mesasPara4.size() + " grupo(s)\n\n";
		
		s += "PEDIDOS:\n";
		s += "\tPedidos na fila: " + this.pedidosEspera.size() + " pedido(s)\n";
		s += "\tPedidos em preparo: " + this.pedidosEmPreparo.size() + " pedido(s)\n";
		s += "\tPedidos prontos: " + this.pedidosProntos.size() + " pedido(s)\n\n";
		
		return s;
	}
	
}
