
public class Data {
	//atributos
	private int dia;
	private int mes;
	private int ano;
	
	//construtor da data, com dia, mes e ano
	public Data(int dia, int mes, int ano) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}

	//getters da data
	public int getDia() {
		return dia;
	}

	public int getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}

	//método toString para retornar a data no formato certo
	public String toString() {
		return dia + "/" + mes + "/" + ano;
	}
	
	
}
