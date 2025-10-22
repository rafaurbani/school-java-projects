
public class Pedido {
	private int tempoDePreparo; //ciclos na cozinha, vai até 0
	private int id; //id para relacionar o pedido ao grupo
	private static int idCont = 1;
	
	public Pedido() {
		this.tempoDePreparo = (int)(Math.random() * 31 + 50); //tempo aleatório
		this.id = idCont++;
	}

	public int getTempoDePreparo() {
		return tempoDePreparo;
	}
	
	public int diminuiTempoDePreparo() {
		tempoDePreparo--;
		return getTempoDePreparo();
	}
	
	public int getId() {
		return id;
	}
}
