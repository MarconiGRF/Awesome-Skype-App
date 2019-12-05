package hel;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendAudio extends Thread {
    public TargetDataLine line = null;
    public DatagramSocket socket;
    byte[] buffer = new byte[512];
    public InetAddress ip;
    public int port;

    SendAudio(){

    }

    @Override
    public void run(){
        Long pack = 0l;
        while(true){
            try {
                int read = line.read(buffer, 0, buffer.length);
                DatagramPacket data = new DatagramPacket(buffer, buffer.length, ip, port);
                socket.send(data);
                if(true){
                    break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        line.drain();
        line.close();

    }
}
