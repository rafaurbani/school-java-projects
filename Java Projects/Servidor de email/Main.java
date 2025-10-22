
public class Main {

	public static void main(String[] args) {
		CaixaPostal cpCarlos = new CaixaPostal("carlos@outlook.br");
		CaixaPostal cpEduardo = new CaixaPostal("eduardo@outlook.br");
		CaixaPostal cpJoao = new CaixaPostal("joao@gmail.com");
		CaixaPostal cpMarta = new CaixaPostal("marta@outlook.br");
		CaixaPostal cpLucia = new CaixaPostal("lucia@hotmail.com");
		CaixaPostal cpJeanne = new CaixaPostal("jeanne@gmail.com");

		Servidor ser = new Servidor();

		ser.add(cpCarlos);
		ser.add(cpEduardo);
		ser.add(cpJoao);
		ser.add(cpMarta);
		ser.add(cpLucia);
		ser.add(cpJeanne);

		cpCarlos.send("eduardo@outlook.br", "janta", "testando...");
		cpCarlos.send("joao@gmail.com", "janta", "ok");
		cpJoao.send("carlos@outlook.br", "onde tu estas?", "me diz");
		cpJeanne.send("eduardo@outlook.br", "oi", "e ai?");
		cpLucia.send("jeanne@gmail.com", "estudar", "vamos pegar cinema?");
		cpLucia.send("carlos@outlook.br", "estudar", "vamos pegar cinema?");
		cpMarta.send("eduardo@outlook.br", "oba", "o que foi?");
		cpMarta.send("marta@outlook.br", "lembrete", "eu");
		cpMarta.send("luiz@outlook.br", "quem?", "nao sei..");

		cpCarlos.showInbox();
		cpEduardo.showInbox();
		cpJoao.showInbox();
		cpMarta.showInbox();
		cpLucia.showInbox();
		cpJeanne.showInbox();
		
		ser.sendReceive();

		cpCarlos.showInbox();
		cpEduardo.showInbox();
		cpJoao.showInbox();
		cpMarta.showInbox();
		cpLucia.showInbox();
		cpJeanne.showInbox();

	}

}
