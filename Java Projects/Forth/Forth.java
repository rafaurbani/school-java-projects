import java.util.*;

public class Forth {
	private Stack<Integer> pilha;
	//private Map<String, String> dicionario;
	
	public Forth() {
		this.pilha = new Stack<Integer>();
		//this.dicionario = new HashMap<String, String>();
	}
	
	public void read(String string) {
		String[] str = string.split(" ");
		for (int i = 0; i < str.length; i++) {
			try {
				Integer.valueOf(str[i]);
			} catch (NumberFormatException e) {
				operacao(str[i]);
				continue;
			}
			
			pilha.push(Integer.valueOf(str[i]));
		}		
	}
	
	public void operacao(String op) {
		switch(op) {
		//operadores comuns (+, -, *, /, %)
		case "+":
			pilha.push(pilha.pop() + pilha.pop());
			break;
		case "-":
			pilha.push((pilha.pop() - pilha.pop()) * -1);
			break;
		case "*":
			pilha.push(pilha.pop() * pilha.pop());
			break;
		case "/":
			int num = pilha.pop();
			pilha.push(pilha.pop() / num);
			break;
		case "%":
			pilha.push(pilha.pop() % pilha.pop());
			break;
		//outros operadores
		case "dup":
			pilha.push(pilha.peek());
			break;
		case "drop":
			pilha.pop();
			break;
		case "swap":
			int num1 = pilha.pop();
			int num2 = pilha.pop();
			pilha.push(num1);
			pilha.push(num2);
			break;
		case "rot":
			int n1 = pilha.pop();
			int n2 = pilha.pop();
			int n3 = pilha.pop();
			pilha.push(n1);
			pilha.push(n3);
			pilha.push(n2);
			break;
		case "over":
			pilha.push(pilha.get(pilha.size() - 2));
			break;
		case "tuck":
			pilha.add(pilha.size() - 2, pilha.peek());
			break;
		case ".":
			System.out.println(pilha.pop());
		}
	}
	
	
	public String toString() {
		try {
			return pilha.toString();
		} finally {
			pilha.clear();
		}
	}
	
}
