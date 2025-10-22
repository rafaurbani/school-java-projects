import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Driver {
	
	RandomAccessFile file;
	int clusterSize;
	int quantClusters;
	
	//construtor do driver
	public Driver(int clusterSize, int quantClusters) {
		try {
			file = new RandomAccessFile("disco.txt", "rw"); //acessa disco
		} catch (Exception e) {
			System.out.println("ERRO na criação/leitura do arquivo do disco"); //caso ocorra algum erro, imprime erro
		}
		
		this.clusterSize = clusterSize;
		this.quantClusters = quantClusters;
		
		//cria um novo disco se o disco estiver vazio
		if (emptyDisk())
			createDisk();
	}
	
	//cria novo disco
	private void createDisk() {
		try {
			//escreve os bytes de "disco" no cluster 0
			file.seek(0);
			file.write((byte)'d');
			file.write((byte)'i');
			file.write((byte)'s');
			file.write((byte)'c');
			file.write((byte)'o');
			//nos outros bytes, escreve 0
		 	for (int i = 5; i < (clusterSize * quantClusters); i++)
			   file.write(0); 
		} catch (IOException e) {
			System.out.println("ERRO na criação do disco");
		}
	}
	
	//escreve o cluster do parametro na posicao do parametro
	public void writeCluster(int clusterNumber, byte[] cluster) {
		try {
			file.seek(clusterNumber * clusterSize);
			file.write(cluster);
		} catch (IOException e) {
			System.out.println("ERRO na gravação do arquivo do disco");
		}
	}
	
	//le e retorna cluster da posicao do parametro
	public byte[] readCluster(int clusterNumber) {
		byte [] b = new byte[clusterSize];
		
		try {
			file.seek(clusterNumber * clusterSize);
			file.read(b);
		} catch (IOException e) {
			System.out.println("ERRO na leitura do arquivo do disco");
		}
		
		return b;
	}
	
	//fecha disco
	public void closeDisk() {
		try {
			file.close();
		} catch (IOException e) {
			System.out.println("ERRO ao fechar disco");
		}
	}
	
	//retorna true se o disco estiver vazio, e false se já foi escrito a palavra "disco"
	private boolean emptyDisk() {		
		if (readCluster(0)[0] == 100)
			return false;
		else
			return true;
	}
	
	//retorna true se a fat está escrita no disco
	public boolean FATisWritten() {
		byte[] emptyCluster = new byte[clusterSize];
		
		if (Arrays.equals(readCluster(1), emptyCluster))
			return false;
		else
			return true;
	}
	
	//limpa disco (cria um novo disco em cima do já criado)
	public void clearDisk() {
		createDisk();
	}
}
