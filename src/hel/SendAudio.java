package hel;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;

public class SendAudio extends Thread {
    public TargetDataLine line = null;
    public DatagramSocket socket;
    public InetAddress ip;
    public int port;
    NodeAudio node;

    SendAudio(NodeAudio node, Recorder rec){
        this.node = node;
        this.socket = node.nodeSocket;
        this.ip = node.nodeSocket.getInetAddress();
        this.port = node.portaSegundoNode;
        this.line = rec.audio_in;
    }

    @Override
    public void run(){
        int sequence = 0;
        while(true){
            try {
                int date =(int) new Date().getTime();
                node.enviar(line, date, sequence);
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
            sequence++;
        }
        line.drain();
        line.close();
    }
}
