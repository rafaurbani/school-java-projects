public class Main {
	public static void main(String[] args) {
		/*SLinkedList<Character> list = new SLinkedList<Character>();

		list.add('z');
		list.add('y');
		list.add('x');			
		list.add('f');
		list.add('e');
		list.add('d');
		list.add('c');
		list.add('b');
		list.add('a');
		
		System.out.println(list);
		list.duplica(5);
		list.duplica(6);
		list.duplica(1);
		System.out.println(list);
		list.removeDuplicados();
		System.out.println(list);
		list.remove(0);
		System.out.println(list);
		
		SLinkedList<Character> outraLista = new SLinkedList<Character>();
		
		outraLista.add('z');
		outraLista.add('y');
		outraLista.add('x');
		
		list.insereLista(outraLista, 2);
		System.out.println(list); */
		
		SLinkedList<Pessoa> listaPessoas = new SLinkedList<Pessoa>();
		
		listaPessoas.add(new Pessoa("Rafael", "RS", 18));
		listaPessoas.add(new Pessoa("Paulo", "PR", 27));
		listaPessoas.add(new Pessoa("Jonas", "SC", 51));
		listaPessoas.add(new Pessoa("Lucas", "SP", 22));
		listaPessoas.add(new Pessoa("Karina", "AM", 21));
		listaPessoas.add(new Pessoa("João", "RN", 19));
		listaPessoas.add(new Pessoa("Giselle", "MG", 19));
		listaPessoas.add(new Pessoa("Irene", "MT", 30));
		
		System.out.println(listaPessoas);

		//listaPessoas.sort(new SortNome());
		//listaPessoas.sort(new SortIdade());
		listaPessoas.sort(new SortEstado());
		
		System.out.println(listaPessoas);
				
	}
}
