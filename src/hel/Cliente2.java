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
		Socket clienteSocket = new Socket("localhost", 3002);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		int portaCliente1 = Integer.parseInt(inFromServer.readLine());
		clienteSocket.close();
		Node u2 = new Node(InetAddress.getByName("localhost"),3002,portaCliente1);
		gui helgui = new gui(u2,portaCliente1);

			Thread r2 = new Receber(u2,1,helgui);
			r2.start();


	}

}