package hel;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

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

    public NodeAudio(InetAddress nodeIP, int portaNode, int portaSegundoNode) throws IOException {
        System.out.println(nodeIP);
        this.portaNode = portaNode;
        this.portaSegundoNode = portaSegundoNode;
        nodeSocket = new DatagramSocket(portaNode);
    }

    void receber() throws IOException, BadLocationException {
        byte[] receiveData = new byte[512];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
        nodeSocket.receive(receivePacket);
        // Create the AudioData object from the byte array
        AudioData audiodata = new AudioData(receiveData);
        // Create an AudioDataStream to play back
        AudioDataStream audioStream = new AudioDataStream(audiodata);
        // Play the sound
        AudioPlayer.player.start(audioStream);
    }

    void enviar(byte[] buffer, TargetDataLine line, InetAddress ip, int port) throws IOException {
        int read = line.read(buffer, 0, buffer.length);
        DatagramPacket data = new DatagramPacket(buffer, buffer.length, ip, port);
        nodeSocket.send(data);
    }




}
