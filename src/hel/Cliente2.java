package hel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class Cliente2 {

	public static void main(String[] args) throws IOException {
		Scanner teclado = new Scanner(System.in);
		Socket clienteSocket = new Socket("localhost", 3000);
		System.out.println("Socket iniciado.");
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		System.out.println("Buffer lido do servidor.");
		int portaCliente1 = Integer.parseInt(inFromServer.readLine());
		System.out.println("Messagem lida do buffer");
		Node u2 = new Node(InetAddress.getByName("localhost"),3001,portaCliente1);
		System.out.println("Conexao criada com outro cliente");
		//loginForm login = new loginForm();
		gui helgui = new gui(u2,portaCliente1);

			Thread r2 = new Receber(u2,1,helgui);
			r2.start();
	}

}