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

    public NodeAudio(InetAddress nodeIP, int portaNode, int portaSegundoNode) throws IOException {
        System.out.println(nodeIP);
        this.nodeIP = nodeIP;
        this.portaNode = portaNode;
        this.portaSegundoNode = portaSegundoNode;
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
        RTPPacket packet = new RTPPacket(11, sequence, date, buffer, 1012);
        byte[] fullPacket = new byte[1012+12];
        packet.getpacket(fullPacket);
        DatagramPacket data = new DatagramPacket(fullPacket, fullPacket.length, nodeIP, portaSegundoNode);
        nodeSocket.send(data);
    }




}
