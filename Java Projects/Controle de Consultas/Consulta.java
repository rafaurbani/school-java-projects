public class Consulta
{
	//atributos (com contador de consultas para definir o id)
    private static int contadorConsulta = 1;
    private int idConsulta;
    private int idPaciente;
    private String motivo;
    private String exame;
    private String data;
    
    //métodos
    public Consulta(int idPaciente, String motivo, int numExame, String data) {
    	this.idConsulta = contadorConsulta++;
    	this.idPaciente = idPaciente;
    	this.motivo = motivo;
    	this.exame = Utilitario.exames[numExame - 1];
    	this.data = data;
    }

    //imprime a consulta de forma organizada
	public void imprimir() {
    	System.out.printf("         Consulta n° %d\n", this.idConsulta);
    	System.out.printf("-------------------------------------------\n");
    	System.out.printf("  ID do paciente: %d\n", this.idPaciente);
    	System.out.printf("  Motivo da consulta: \n    %s\n", this.motivo);
    	System.out.printf("  Exame a ser realizado: \n    %s\n", this.exame);
    	System.out.printf("  Data: \n    %s\n", this.data);
    	System.out.printf("-------------------------------------------\n\n");
    }

	//getters e setters
	public int getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getExame() {
		return exame;
	}

	public void setExame(String exame) {
		this.exame = exame;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
    
    
}
