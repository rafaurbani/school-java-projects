public class Historico
{
	//atributos (array de consultas)
    private Consulta[] consultas = new Consulta[10];
    
    //método para adicionar consulta ao histórico
    public void adicionarConsulta(Consulta consulta) {
    	//boolean e loop para aumentar array se estiver cheia
		boolean cheio = true;
    	while(cheio) {
			for (int i = 0; i < this.consultas.length; i++) {
				if (this.consultas[i] == null) {
					//coloca a consulta do parâmetro do método na primeira posição nula do array
					this.consultas[i] = consulta;
					cheio = false;
					break;
				}
			}
			
			//criação da nova array se estiver cheia, repetindo o loop depois de criada
			if(cheio) {
				Consulta[] novaArray = new Consulta[this.consultas.length + 10];
				for (int i = 0; i < this.consultas.length; i++) {
					novaArray[i] = this.consultas[i];
				}
				this.consultas = novaArray;
			}
		}
    }
    
    //imprime todas as consultas do histórico do paciente de forma organizada
    public void imprimir() {
	   	for (int i = 0; i < this.consultas.length; i++) {
	   		if (this.consultas[i] != null) {
		       	System.out.printf("-------------------------------------------\n");
		   		System.out.printf("         Consulta n° %d\n", i+1);
		       	System.out.printf("-------------------------------------------\n");
		   		System.out.printf("  Motivo: %s\n", this.consultas[i].getMotivo());
		   		System.out.printf("  Exame feito: %s\n", this.consultas[i].getExame());
		   		System.out.printf("  Data do exame: %s\n", this.consultas[i].getData());
	   		}
	   	}
    }
    
    //getters e setters
    public Consulta[] getConsultas() {
    	return this.consultas;
    }
}
