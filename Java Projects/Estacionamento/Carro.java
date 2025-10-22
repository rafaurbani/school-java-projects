
public class Carro {
	
	private int id;
	private static int idCont = 0;
	private int duracaoPermanencia;
	
	public Carro() {
		this.id = ++idCont;
		this.duracaoPermanencia = (int)(Math.random() * 31 + 10);
	}

	public int getId() {
		return id;
	}

	public int getDuracaoPermanencia() {
		return duracaoPermanencia;
	}
	
	public void setDuracaoPermanencia(int duracaoPermanencia) {
		this.duracaoPermanencia = duracaoPermanencia;
	}
}
