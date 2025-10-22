import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Teste4 {
	public static void main(String[] args) {
		// mudar o parametro do FileReader para mudar o arquivo usado.
		try (BufferedReader in = new BufferedReader(new FileReader("in"))){
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

	static long achaSoma(ArrayList<Integer> x, ArrayList<Integer> y) {
		long start = System.nanoTime();
		
		int somaTotal = somaTodos(x, y);
		if (somaTotal % 2 == 0) {
			if (progDinamica(x, y, somaTotal/2)) {
				System.out.println(somaTotal/2 + " nenhum domino descartado");
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
			if ((somaTotal = somaTodos(x,y)) % 2 == 0) {
				if (progDinamica(x, y, somaTotal/2)) {
					somas.add(somaTotal/2);
					descartes.add(descartadoX + " " + descartadoY);
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

		return (System.nanoTime() - start);
	}
	
	// recebe os arrays e a metade da soma total, que eh o valor procurado
	static boolean progDinamica(ArrayList<Integer> x, ArrayList<Integer> y, int soma) {
		// inicializa a matriz de boolean com false em todas as posicoes
		boolean[][] parciais = new boolean[x.size() + 1][soma + 1];
		
		// caso base
		parciais[0][0] = true;
		for (int j = 1; j <= soma; j++) {
			// nao eh possivel obter um valor nao nulo quando o numero de elementos eh nulo
			parciais[0][j] = false;
		}
		
		for (int i = 1; i <= x.size(); i++) {
			for (int j = 0; j <= soma; j++) {
				
				int valor = x.get(i-1);
				
				// verifica se o valor x do domino em questao eh menor que o valor da soma
				// se nao for, ignora, mantendo o valor de somas[i][j] como false
				if (valor <= j)
					parciais[i][j] = parciais[i-1][j - valor];
				
				
				valor = y.get(i-1);
				if (valor <= j)
					//aqui se faz um OR entre o valor parcial (que pode ter sido setado pra true no if anterior)
					//e o valor que se obteria usando o valor do array y.
					parciais[i][j] = parciais[i][j] || parciais[i-1][j - valor];
			}
		}
		
		return parciais[parciais.length-1][parciais[0].length-1];
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
