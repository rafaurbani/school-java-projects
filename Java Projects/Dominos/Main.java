import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		// mudar o parametro do FileReader para mudar o arquivo usado.
		try (BufferedReader in = new BufferedReader(new FileReader("in3"))){
			long tempo = 0;
			
			String line = in.readLine();
			while (!line.equals("0")) {
				int n = Integer.parseInt(line);
				ArrayList<Integer> x = new ArrayList<>(n);
				ArrayList<Integer> y = new ArrayList<>(n);
				
				for (int i = 0; i < n; i++) {
					String[] numbers = in.readLine().split(" ");
					x.add(Integer.parseInt(numbers[0]));
					y.add(Integer.parseInt(numbers[1]));
				}
				
				//ordenaDecresc(x, y);
				//System.out.println(x);
				//System.out.println(y);
				tempo += achaSoma(x, y);
				line = in.readLine();
			}
			
			System.out.println(tempo);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro.");
		}
	}

	static long achaSoma(ArrayList<Integer> x, ArrayList<Integer> y) {
		long start = System.nanoTime();
		int soma;
		if (somaTodos(x, y) % 2 == 0) {
			soma = achaSomaRec2(x, y, 0, 0, 0);
			if (soma != -1) {
				System.out.println(soma + " nenhum domino descartado");
				long end = System.nanoTime();
				return (end - start);
			}
		}
		
		int size = x.size();
		ArrayList<String> descartes = new ArrayList<>();
		ArrayList<Integer> somas = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int descartadoX = x.remove(i);
			int descartadoY = y.remove(i);
			//System.out.println("Testando descarte " + descartadoX + " " + descartadoY);
			if (somaTodos(x,y) % 2 == 0) {
				if ((soma = achaSomaRec2(x, y, 0, 0, 0)) != -1) {
					somas.add(soma);
					descartes.add(descartadoY + " " + descartadoX);
				}
			}
			x.add(i, descartadoX);
			y.add(i, descartadoY);
		}
		
		int maior = -1;
		String domino = "";
		for (int i = 0; i < somas.size(); i++) {
			if (somas.get(i) > maior) {
				maior = somas.get(i);
				domino = descartes.get(i);
			}
		}
		
		if (maior != -1) {
			System.out.println(maior + " descartado o domino " + domino);
		} else {
			System.out.println("impossivel");
		}
		long end = System.nanoTime();
		return (end - start);
	}
	
	static int achaSomaRec(ArrayList<Integer> x, ArrayList<Integer> y, int i, int somaSup, int somaInf) {
		if (i == x.size()) {
			if (somaSup == somaInf) {
				return somaSup;
			}
			
			return -1;
		}
		
		int soma = achaSomaRec(x, y, i+1, somaSup + x.get(i), somaInf + y.get(i));
		if (soma != -1) {
			return soma;
		} 
		
		return achaSomaRec(x, y, i+1, somaSup + y.get(i), somaInf + x.get(i));
	}
	
	static int achaSomaRec2(ArrayList<Integer> x, ArrayList<Integer> y, int i, int somaSup, int somaInf) {
		if (i == x.size()) {
			if (somaSup == somaInf) {
				return somaSup;
			}
			
			return -1;
		}
		
		int proximoX = x.get(i);
		int proximoY = y.get(i);
		int soma;
		if (proximoX >= proximoY) {
			if (somaSup <= somaInf) {
				if ((soma = achaSomaRec2(x, y, i+1, somaSup + proximoX, somaInf + proximoY)) != -1)
					return soma;
				return achaSomaRec2(x, y, i+1, somaSup + proximoY, somaInf + proximoX);
			} else {
				if ((soma = achaSomaRec2(x, y, i+1, somaSup + proximoY, somaInf + proximoX)) != -1)
					return soma;
				return achaSomaRec2(x, y, i+1, somaSup + proximoX, somaInf + proximoY);
			}
		} else {
			if (somaSup <= somaInf) {
				if ((soma = achaSomaRec2(x, y, i+1, somaSup + proximoY, somaInf + proximoX)) != -1)
					return soma;
				return achaSomaRec2(x, y, i+1, somaSup + proximoX, somaInf + proximoY);
			} else {
				if ((soma = achaSomaRec2(x, y, i+1, somaSup + proximoX, somaInf + proximoY)) != -1)
					return soma;
				return achaSomaRec2(x, y, i+1, somaSup + proximoY, somaInf + proximoX);
			}
		}
	}
	
	static void ordenaDecresc(ArrayList<Integer> x, ArrayList<Integer> y) {
		int size = x.size();
		for (int i = 1; i < size; i++) {
			int[] domino = new int[2];
			domino[0] = x.get(i);
			domino[1] = y.get(i);
			int max = Math.max(domino[0], domino[1]);
			int j = i-1;
			while (j >= 0 && max > Math.max(x.get(j), y.get(j))) {
				x.set(j + 1, x.get(j));
				y.set(j + 1, y.get(j));
				j--;
			}
			
			x.set(j + 1, domino[0]);
			y.set(j + 1, domino[1]);
		}
	}
	
	static int somaTodos(ArrayList<Integer> x, ArrayList<Integer> y) {
		int soma = 0;
		for (Integer a : x) {
			soma += a;
		}
		for (Integer a : y) {
			soma += a;
		}
		
		return soma;
	}
}