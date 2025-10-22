public class DirEntry {
	byte[] nome = new byte[16];
	byte[] ext = new byte[3];
	char tipo;
	short cluster1;
	short tamanho;
	
	public DirEntry(String nome, String ext, char tipo, int cluster1, int tamanho) {
		if (nome.length() > 16)
			nome = nome.substring(0, 16); //diminui a string do nome se passar de 16 caracteres
		
		if (ext != null && ext.length() > 3)
			ext = ext.substring(0, 3); //diminui a string da ext se passar de 3 caracteres
		
		byte[] bytesNome = nome.getBytes();
		
		//passa os bytes para as arrays de bytes (se for menor que o maximo, o resto fica com bytes 0)
		for (int i = 0; i < bytesNome.length; i++) {
			this.nome[i] = bytesNome[i];
		}
		
		//passa os bytes do ext apenas se não for nulo
		if (ext != null) {
			byte[] bytesExt = ext.getBytes();
		
			for (int i = 0; i < bytesExt.length; i++) {
				this.ext[i] = bytesExt[i];
			}
		}
		
		this.tipo = tipo;
		this.cluster1 = (short)cluster1;
		this.tamanho = (short)tamanho;
	}

	public DirEntry() {
		//inicialização nula
	}

	public byte[] getBytesNome() {
		return nome;
	}
	
	//retorna os bytes do nome em forma de string
	public String getNome() {
		String s = "";
		for (byte b : nome) {
			if (b != 0) s += (char)b;
		}
		return s;
	}

	public byte[] getBytesExt() {
		return ext;
	}

	//retorna os bytes da ext em forma de string
	public String getExt() {
		String s = "";
		for (byte b : ext) {
			if (b != 0) s += (char)b;
		}
		return s;
	}
	
	//retorna nome + ext (se houver)
	public String getNomeExt() {
		if (tipo == 'A')
			return String.format("%s.%s", getNome(), getExt());
		else
			return getNome();
	}

	public char getTipo() {
		return tipo;
	}

	public short getCluster1() {
		return cluster1;
	}

	public short getTamanho() {
		return tamanho;
	}
	
	//retorna uma array de todos os bytes do direntry
	public byte[] getAllBytes() {
		byte[] allBytes = new byte[24];
		
		for (int i = 0; i < nome.length; i++) {
			allBytes[i] = nome[i];
		}
		
		for (int i = 0; i < ext.length; i++) {
			allBytes[i + 16] = ext[i];
		}
		
		allBytes[19] = (byte)tipo;
		
		allBytes[20] = (byte)(cluster1 / 256);
		allBytes[21] = (byte)(cluster1 % 256);
		
		allBytes[22] = (byte)(tamanho / 256);
		allBytes[23] = (byte)(tamanho % 256);
		
		return allBytes;
	}

	public void setNome(byte[] nome) {
		this.nome = nome;
	}
	
	public void setByteNome(int pos, byte byteNome) {
		this.nome[pos] = byteNome;
	}

	public void setExt(byte[] ext) {
		this.ext = ext;
	}

	public void setByteExt(int pos, byte byteExt) {
		this.ext[pos] = byteExt;
	}
	
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public void setCluster1(short cluster1) {
		this.cluster1 = cluster1;
	}

	public void setTamanho(short tamanho) {
		this.tamanho = tamanho;
	}

	public String toString() {
		if (tipo == 'A')
			return String.format("%16s | %3s | %4d | %6d ", getNome(), getExt(), getCluster1(), getTamanho());
		else
			return String.format("%16s | DIR | %4d |        ", getNome(), getCluster1());
	}
	
	
}
