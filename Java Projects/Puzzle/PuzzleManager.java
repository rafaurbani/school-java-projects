import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PuzzleManager {
	Scanner scan = new Scanner(System.in);
	
	public void play() {
		imprimeImagens();
		System.out.printf("Escolha uma das imagens: ");
		int imagem = scan.nextInt();
		
		while (imagem < 1 || imagem > 3) {
			System.out.printf("Número inválido! Digite novamente: ");
			imagem = scan.nextInt();
		}
		
		
	}
	
	public void imprimeImagens() {
		System.out.println("Olá, escolha uma das imagens abaixo (1, 2 ou 3):");
		
		try (BufferedReader in = new BufferedReader(new FileReader("arq1.txt"))) {
			System.out.println("Imagem 1:");
			
			String linha;
			while((linha = in.readLine()) != null) {
				System.out.println(linha);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\nAperte enter para ver a imagem 2");
		scan.nextLine();
		
		try (BufferedReader in = new BufferedReader(new FileReader("arq2.txt"))) {
			System.out.println("Imagem 2:");
			
			String linha;
			while((linha = in.readLine()) != null) {
				System.out.println(linha);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("\nAperte enter para ver a imagem 3");
		scan.nextLine();
		
		try (BufferedReader in = new BufferedReader(new FileReader("arq3.txt"))) {
			System.out.println("Imagem 3:");
			
			String linha;
			while((linha = in.readLine()) != null) {
				System.out.println(linha);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
