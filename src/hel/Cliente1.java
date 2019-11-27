package hel;

import javax.swing.text.BadLocationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente1 {



	public static void main(String[] args) throws IOException{

		Socket clienteSocket = new Socket("localhost", 3001);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		int portaCliente2 = Integer.parseInt(inFromServer.readLine());
		clienteSocket.close();
		Node u1 = new Node(InetAddress.getByName("localhost"),3001,portaCliente2);
		loginForm login = new loginForm();
		gui helgui = new gui(u1,portaCliente2);


			Thread r1 = new Receber(u1,2,helgui);
			r1.start();


	}

}
class Node {
	int portaNode;
	int portaSegundoNode;
	DatagramSocket nodeSocket;
	gui helgui;

	public Node(InetAddress nodeIP, int portaNode, int portaSegundoNode) throws IOException{
		System.out.println(nodeIP);
		this.portaNode = portaNode;
		this.portaSegundoNode = portaSegundoNode;
		nodeSocket = new DatagramSocket(portaNode);
	}

	void enviar(InetAddress segundoNodeIP, int portaSegundoNode, String texto) throws IOException {
		byte[] out = texto.getBytes();
		Object lock1 = new Object();
		DatagramPacket sendPacket = new DatagramPacket(out, out.length, segundoNodeIP, portaSegundoNode);
		nodeSocket.send(sendPacket);
		//System.out.println("Enviado!");
		//nodeSocket.close();
	}

	void receber(int numNode, gui helgui) throws IOException, BadLocationException {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
		nodeSocket.receive(receivePacket);
		String texto = new String(receivePacket.getData());
		String letter = ("Cliente "+numNode+": "+texto);
		helgui.write(letter);
		//nodeSocket.close();
	}
}


class  Enviar extends Thread{
	private Node node;
	InetAddress segundoNodeIP;
	int portaSegundoNode;
	String texto;

	public Enviar(Node node, InetAddress segundoNodeIP, int portaSegundoNode, String texto) throws IOException {
		this.node = node;
		this.segundoNodeIP = segundoNodeIP;
		this.portaSegundoNode = portaSegundoNode;
		this.texto = texto;
	}

	public void run(){
		try {
			node.enviar(segundoNodeIP, portaSegundoNode, texto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


class Receber extends Thread{
	private Node node;
	int numNode;
	gui Gui;

	public Receber(Node node, int numNode, gui helgui) {
		this.node = node;
		this.numNode = numNode;
		this.Gui = helgui;
	}

	public void run() {
		while(true) {
			try {
				node.receber(numNode, Gui);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
