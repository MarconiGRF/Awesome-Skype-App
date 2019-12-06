package hel;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NodeAudio {
    int portaNode;
    int portaSegundoNode;
    DatagramSocket nodeSocket;
    byte[] buffer = new byte[512];
    InetAddress nodeIP;

    public NodeAudio(InetAddress nodeIP, int portaNode, int portaSegundoNode) throws IOException {
        System.out.println(nodeIP);
        this.nodeIP = nodeIP;
        this.portaNode = portaNode;
        this.portaSegundoNode = portaSegundoNode;
        nodeSocket = new DatagramSocket(portaNode);
    }

    void receber(SourceDataLine audio_out) throws IOException, BadLocationException {
        byte[] receiveData = new byte[882];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
        nodeSocket.receive(receivePacket);
        receiveData = receivePacket.getData();
        // Create the AudioData object from the byte array
        audio_out.write(receiveData, 0, receiveData.length);
    }

    void enviar(TargetDataLine line) throws IOException {
        byte[] buffer = new byte[894];

        RTPHeader header = new RTPHeader();
        byte[] bytedHeader = header.getHeader();
        buffer[0] = bytedHeader[0];
        buffer[1] = bytedHeader[1];
        buffer[2] = bytedHeader[2];
        buffer[3] = bytedHeader[3];
        buffer[4] = bytedHeader[4];
        buffer[5] = bytedHeader[5];
        buffer[6] = bytedHeader[6];
        buffer[7] = bytedHeader[7];
        buffer[8] = bytedHeader[8];
        buffer[9] = bytedHeader[9];
        buffer[10] = bytedHeader[10];
        buffer[11] = bytedHeader[11];

        line.read(buffer, 12, buffer.length);
        DatagramPacket data = new DatagramPacket(buffer, buffer.length, nodeIP, portaSegundoNode);

        //Increment function
        nodeSocket.send(data);
    }




}
