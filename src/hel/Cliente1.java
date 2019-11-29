package hel;

import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente1 {



	public static void main(String[] args) throws IOException{
		try {

		Socket clienteSocket = new Socket("localhost", 3001);

		BufferedWriter writeToServer = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));
		writeToServer.write(Integer.toString(3003)+"\n");
		writeToServer.flush();



		System.out.println("Socket iniciado com o servidor.");


		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		System.out.println("Buffer lido do servidor.");
		int portaCliente2 = Integer.parseInt(inFromServer.readLine());
//		Conecta_client client = new Conecta_client(clienteSocket);
//		client.start();
		System.out.println("Porta do outro cliente: " + portaCliente2);
		System.out.println("Messagem lida do buffer");
		Node u1 = new Node(InetAddress.getByName("localhost"),3003,portaCliente2);
		System.out.println("Conexao criada com outro cliente");

		//loginForm login = new loginForm();
		gui helgui = new gui(u1,portaCliente2);
		Thread r1 = new Receber(u1,1,helgui);
		r1.start();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}

	}

}










