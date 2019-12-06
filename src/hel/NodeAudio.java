package hel;


import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class NodeAudio {
    int portaNode;
    int portaSegundoNode;
    DatagramSocket nodeSocket;
    InetAddress nodeIP;
    String ip;

    public NodeAudio(InetAddress nodeIP, int portaNode, int portaSegundoNode, String ipCliente2) throws IOException {
        System.out.println(nodeIP);
        this.nodeIP = nodeIP;
        this.portaNode = portaNode;
        this.portaSegundoNode = portaSegundoNode;
        this.ip = ipCliente2;
        nodeSocket = new DatagramSocket(portaNode);
    }

    void receber(SourceDataLine audio_out) throws IOException, BadLocationException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
        nodeSocket.receive(receivePacket);
        receiveData = receivePacket.getData();
        byte[] slice = Arrays.copyOfRange(receiveData, 12, 1024);
        // Create the AudioData object from the byte array
        audio_out.write(slice, 0, slice.length);
    }

    void enviar(TargetDataLine line, int date, int sequence) throws IOException {
        byte[] buffer = new byte[1012];
        line.read(buffer, 0, buffer.length);
        RTPHeader packet = new RTPHeader();
        byte[] fullPacket = new byte[1012+12];
        System.arraycopy(packet.getHeader(), 0, fullPacket, 0, packet.getHeader().length);
        System.arraycopy(buffer, 0, fullPacket, packet.getHeader().length, buffer.length);
        DatagramPacket data = new DatagramPacket(fullPacket, fullPacket.length, InetAddress.getByName(ip), portaSegundoNode);
        nodeSocket.send(data);
    }




}
