import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Teste {
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
				
				tempo += achaSoma(x, y);
				line = in.readLine();
			}

			System.out.println(tempo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro.");
		}
	}
	
	public static long achaSoma(ArrayList<Integer> x, ArrayList<Integer> y) {
		long start = System.nanoTime();
		int n = x.size();
		BigInteger i = BigInteger.ZERO;
		BigDecimal limiteAux = new BigDecimal(Math.pow(2, n));
		BigInteger limite = limiteAux.toBigInteger();
		
		do {
			
			String str = i.toString(2); //converte o BI pra uma string com o valor em binario
			while (str.length() < n) str = "0" + str; //coloca 0s na frente ate ter tamanho n
			char[] inverte = str.toCharArray();
			int somaSup = 0;
			int somaInf = 0;
			
			// faz as somas invertendo onde tiver '1' no array de char
			for (int j = 0; j < n; j++) {
				if (inverte[j] == '0')
					somaSup += x.get(j);
				else
					somaSup += y.get(j);
			} for (int j = 0; j < n; j++) {
				if (inverte[j] == '0')
					somaInf += y.get(j);
				else
					somaInf += x.get(j);
			}
			
			if (somaSup == somaInf) {
				System.out.println(somaSup + " nenhum domino descartado");
				long end = System.nanoTime();
				return (end - start);
			}
			
			i = i.add(BigInteger.ONE);
			
		} while (i.compareTo(limite) < 0);
		
		ArrayList<String> descartes = new ArrayList<>();
		ArrayList<Integer> somas = new ArrayList<>();
		
		for (int j = 0; j < n; j++) {
			ArrayList<Integer> novoX = (ArrayList<Integer>) x.clone();
			ArrayList<Integer> novoY = (ArrayList<Integer>) y.clone();
			String parRemovido = novoX.remove(j) + " " + novoY.remove(j);
			int soma = achaSomaComRemocao(novoX, novoY);
			if (soma != -1) {
				descartes.add(parRemovido);
				somas.add(soma);
			}
		}
		
		if (somas.isEmpty()) {
			System.out.println("impossivel");
			long end = System.nanoTime();
			return (end - start);
		}
		
		int maior = 0;
		String parRemovido = "";
		for (int j = 0; j < somas.size(); j++) {
			if (somas.get(j) >= maior) {
				maior = somas.get(j);
				parRemovido = descartes.get(j);
			}
		}
		
		System.out.println(maior + " descartando o domino " + parRemovido);
		long end = System.nanoTime();
		return (end - start);
	}
	
	public static int achaSomaComRemocao(ArrayList<Integer> x, ArrayList<Integer> y) {
		int n = x.size();
		BigInteger i = BigInteger.ZERO;
		BigDecimal limiteAux = new BigDecimal(Math.pow(2, n));
		BigInteger limite = limiteAux.toBigInteger();
		
		do {
			
			String str = i.toString(2); //converte o BI pra uma string com o valor em binario
			while (str.length() < n) str = "0" + str; //coloca 0s na frente ate ter tamanho n
			char[] inverte = str.toCharArray();
			int somaSup = 0;
			int somaInf = 0;
			
			for (int j = 0; j < n; j++) {
				if (inverte[j] == '0')
					somaSup += x.get(j);
				else
					somaSup += y.get(j);
			} for (int j = 0; j < n; j++) {
				if (inverte[j] == '0')
					somaInf += y.get(j);
				else
					somaInf += x.get(j);
			}
			
			if (somaSup == somaInf) {
				return somaSup;
			}
			
			i = i.add(BigInteger.ONE);
			
		} while (i.compareTo(limite) < 0);
		
		return -1;
	}
}
