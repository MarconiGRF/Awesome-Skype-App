package hel;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

public class SendAudio extends Thread {
    public TargetDataLine line = null;
    public DatagramSocket socket;
    byte[] buffer = new byte[512];
    public InetAddress ip;
    public int port;
    NodeAudio node;

    SendAudio(NodeAudio node, Recorder rec){
        this.node = node;
        this.socket = node.nodeSocket;
        this.ip = node.nodeSocket.getInetAddress();
        this.port = node.nodeSocket.getPort();
        this.line = rec.line;
    }

    @Override
    public void run(){
        while(true){
            try {
                node.enviar(buffer, line, ip, port);
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
        line.drain();
        line.close();
    }
}
