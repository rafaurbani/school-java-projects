
public class Main {
	public static void main(String[] args) {
		Forth forth = new Forth();
		
		forth.read("10 2 - .");
		forth.read("40 11 5 6 * + + .");
		forth.read("40 11 5 6 * + + dup + .");
		forth.read("30 55 18 drop");
		System.out.println(forth);
		forth.read("30 55 18 swap");
		System.out.println(forth);
		forth.read("7 30 55 18 rot");
		System.out.println(forth);
		forth.read("7 30 55 18 over");
		System.out.println(forth);
		forth.read("7 30 55 18 tuck");
		System.out.println(forth);
		
	}
}
