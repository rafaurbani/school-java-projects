
public class Main {

	public static void main(String[] args) {
		/*Cidade poa = new Cidade(10, 200, "POA");
		
		poa.setQuadra(3,2,new Casa(1,"alvenaria",10,true,true));
	    poa.setQuadra(4,4,new Casa(2,"madeira",12,true,false)); 
	    poa.setQuadra(5,5,new Edificio(5,"cimento",30,2,20));
	    poa.setQuadra(7,6,new Edificio(7,"cimento",20,3,10));
	    
	    System.out.println(poa.statusMapa());*/
	    
	    Cidade vi = new Cidade(10,80,"Viamao");
	    Cidade poa = new Cidade(10,80,"Poa");
	    Municipio mu = new Municipio(5); //terá 5 cidades

	    mu.insereCid(poa);
	    mu.insereCid(new Cidade(10,80,"Alvorada"));
	    mu.insereCid(vi);
	    mu.insereCid(new Cidade(10,80,"Gravatai"));

	    System.out.println(mu.mostraMunicipio()); //mostra todas cidades

	    mu.removeCid("Alvorada");

	    System.out.println("Alvorada removida\n\n"+mu.mostraMunicipio());

		vi.setQuadra(1,2,new Casa(1,"alvenaria",10,true,true));
		vi.setQuadra(3,6,new Casa(2,"madeira",12,true,false)); //sem piscina...
		vi.setQuadra(4,4,new Edificio(5,"cimento",30,2,20,30));
		vi.setQuadra(8,2,new Edificio(7,"cimento",20,3,10,40));

		Sala auxS1 = new SalaComercial("Pilates S.A.", 90.3);
		Sala auxS2 = new SalaComercial("Psicologo ME", 88.5);

		((Edificio)vi.getQuadra(8,2).getEdificacao()).ocupaSala(auxS1);
		((Edificio)vi.getQuadra(8,2).getEdificacao()).ocupaSala(auxS2);
		((Edificio)vi.getQuadra(8,2).getEdificacao()).ocupaSala(new SalaComercial("Advogados LTDA", 43.2));
		((Edificio)vi.getQuadra(8,2).getEdificacao()).ocupaSala(new ApResidencial("Joao Gouveia", 75.5));

		System.out.println("\nCondominio:\n"+((Edificio)vi.getQuadra(8,2).getEdificacao()).mostraSalas());
	    
		vi.salva("dados.db");
		
		vi.gravaMapaCidade("vi.txt");
		System.out.println(vi.statusMapa());
		
		mu.gravaRelatorio("relatorio.txt");
	    
	}
}
