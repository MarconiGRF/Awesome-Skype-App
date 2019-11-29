package hel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class Cliente2 {



	public static void main(String[] args) throws IOException{
	try {
		Socket clienteSocket = new Socket("localhost", 3002);
		BufferedWriter writeToServer = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));
		writeToServer.write(Integer.toString(3004)+"\n");
		writeToServer.flush();

		//Conecta_client client = new Conecta_client(clienteSocket);
		//client.start();
		System.out.println("Socket iniciado com o servidor.");
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		System.out.println("Buffer lido do servidor.");
		int portaCliente2 = Integer.parseInt(inFromServer.readLine());

		System.out.println("Porta do outro cliente: " + portaCliente2);
		System.out.println("Messagem lida do buffer");
		Node u1 = new Node(InetAddress.getByName("localhost"), 3004, portaCliente2);
		System.out.println("Conexao criada com outro cliente");

		//loginForm login = new loginForm();
		gui helgui = new gui(u1, portaCliente2);
		Thread r1 = new Receber(u1, 2, helgui);
		r1.start();
	}catch (Exception e){
		System.out.println(e.getMessage());
	}


	}

}