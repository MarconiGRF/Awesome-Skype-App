package hel;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

class ReceiveAudio extends Thread{

    NodeAudio node;
    Recorder rec;

    ReceiveAudio(NodeAudio node, Recorder rec){
        this.node = node;
        this.rec = rec;
    }

    public void run() {
        while(true) {
            try {
                node.receber(rec.audio_out);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}