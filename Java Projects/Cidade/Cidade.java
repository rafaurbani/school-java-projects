import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cidade {
	private Terreno[][] mapa;
	private double area;
	private String nome;

	public Cidade(int matriz, double area, String nome) {
		inicializaCidade(matriz, area, nome);
	}
	
	public void inicializaCidade(int matriz, double area, String nome) {
		this.mapa = new Terreno[matriz][matriz];
		for (int i = 0; i < matriz; i++) {
			for (int j = 0; j < matriz; j++) {
				this.mapa[i][j] = new Terreno(area);
			}
		}
		this.area = area;
		this.nome = nome;
	}
	
	public void setQuadra(int l, int c, Edificacao edificacao) {
		mapa[l][c].setEdificacao(edificacao);
	}

	public Terreno getQuadra(int l, int c) {
		return this.mapa[l][c];
	}

	public String statusMapa() {
		String s = "";

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (this.mapa[i][j].getEdificacao() instanceof Casa) {
					s += "c ";
				} else if (this.mapa[i][j].getEdificacao() instanceof Edificacao) {
					s += "e ";
				} else
					s += "- ";
			}
			s += "\n";
		}

		return s;
	}

	public double calcIptuRegiao(int linhaI, int colunaI, int linhaF, int colunaF) {
		double iptu = 0.0;

		for (int i = linhaI; i <= linhaF; i++) {
			for (int j = colunaI; j <= colunaF; j++) {
				Terreno terreno = this.mapa[i][j];

				if (terreno.getEdificacao() instanceof Casa) {
					Casa casa = (Casa) terreno.getEdificacao();

					double precoMat = 0.0;
					double precoPisc = 0.0;

					if (casa.getMaterial().equals("alvenaria"))
						precoMat = 1.12;
					else if (casa.getMaterial().equals("madeira"))
						precoMat = 1.04;

					if (casa.getComPiscina())
						precoPisc = 2.3;

					iptu += (terreno.getArea() * 1.5) + (casa.getAreaConst() * precoMat) + precoPisc;
				} else if (terreno.getEdificacao() instanceof Edificio) {
					Edificio edificio = (Edificio) terreno.getEdificacao();

					iptu += (terreno.getArea() * 1.4) + (edificio.getQuantAndares() * 0.3)
							+ (edificio.getAreaConst() * 1.3) + (edificio.getQuantosElevadores() * 0.2)
							+ (edificio.getQuantasVagasBox() * 0.15);
				} else {
					iptu += (terreno.getArea() * 1.75);
				}
			}
		}

		return iptu;
	}
	
	public double calcIptuRegiao() {
		return calcIptuRegiao(0, 0, (this.mapa.length - 1), (this.mapa[0].length - 1));
	}

	public void removeEd(int l, int c) {
		this.mapa[l][c].setEdificacao(null);
	}
	
	public void salva(String nomeArquivo) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
			os.writeInt(this.mapa.length);
			os.writeDouble(this.area);
			os.writeObject((Object)nome);
			
			for (int i = 0; i < this.mapa.length; i++) {
				for (int j = 0; j < this.mapa[i].length; j++) {
					os.writeObject((Object) this.mapa[i][j]);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro na gravação do arquivo");
		}
				
	} 
	
	public void le(String nomeArquivo) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
			int qtQuadras = ois.readInt();
			double tamArea = ois.readDouble();
			String nomeCidade = (String)ois.readObject();
			
			inicializaCidade(qtQuadras, tamArea, nomeCidade);
			
			for (int i = 0; i < this.mapa.length; i++) {
				for (int j = 0; j < this.mapa.length; j++) {
					this.mapa[i][j] = (Terreno)ois.readObject();
				}
			}
			
		} catch (EOFException e) {
			System.out.println("Arquivo chegou ao fim");
		} catch (IOException e) {
			System.out.println("Erro na leitura do arquivo");
		} catch (ClassNotFoundException e) {
			System.out.println("Classe não encontrada");
		}
	}
	
	public void gravaMapaCidade(String nomeArquivo) {
		try (FileOutputStream fos = new FileOutputStream(nomeArquivo)) {
			byte[] mapa = statusMapa().getBytes();
			fos.write(mapa);
		} catch (IOException e) {
			System.out.println("Erro na gravação do arquivo");
		}
	}
	
	public String getEdificacoes() {
		int edificacoes = 0;
		int edificios = 0;
		int casas = 0;
		
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (this.mapa[i][j] != null && this.mapa[i][j].getEdificacao() instanceof Edificio) {
					edificacoes++;
					edificios++;
				} else if (this.mapa[i][j] != null && this.mapa[i][j].getEdificacao() instanceof Casa) {
					edificacoes++;
					casas++;
				}
			}
		}
		
		return "Edificacoes: " + edificacoes + "\n\tEdificios: " + edificios + "\n\tCasas:" + casas;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
