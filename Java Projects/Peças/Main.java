
public class Main {
	public static void main(String[] args) {
		Cadastro cad = new Cadastro();
		
		cad.inserePeca(new Peca("Roupa", 100, 100.00));
		cad.inserePeca(new Peca("Roupa", 45, 130.00));
		cad.inserePeca(new Peca("Roupa", 453, 2130.00));
		cad.inserePeca(new Peca("Roupa", 1, 2130.00));
		
		cad.mostraPecas();
		cad.ordenaPecas();
		cad.mostraPecas();
	}
}
