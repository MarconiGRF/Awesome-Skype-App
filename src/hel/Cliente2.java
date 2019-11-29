package hel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class Cliente2 {



	public static void main(String[] args) throws IOException{

		Socket clienteSocket = new Socket("localhost", 3000);
		Conecta_client client = new Conecta_client(clienteSocket);
		client.start();
		System.out.println("Socket iniciado com o servidor.");
		DataInputStream inFromServer =  new DataInputStream(clienteSocket.getInputStream());

		//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		System.out.println("Buffer lido do servidor.");
		int portaCliente2 = inFromServer.read();
		System.out.println("Porta do outro cliente: " + portaCliente2);
		System.out.println("Messagem lida do buffer");
		Node u1 = new Node(InetAddress.getByName("localhost"),3002,portaCliente2);
		System.out.println("Conexao criada com outro cliente");

		//loginForm login = new loginForm();
		gui helgui = new gui(u1,portaCliente2);
		Thread r1 = new Receber(u1,2,helgui);
		r1.start();


	}

}