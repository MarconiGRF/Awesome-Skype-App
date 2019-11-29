package hel;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Node {
    int portaNode;
    int portaSegundoNode;
    DatagramSocket nodeSocket;
    gui helgui;

    public Node(InetAddress nodeIP, int portaNode, int portaSegundoNode) throws IOException {
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