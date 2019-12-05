package hel;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NodeAudio {
    int portaNode;
    int portaSegundoNode;
    DatagramSocket nodeSocket;

    public NodeAudio(InetAddress nodeIP, int portaNode, int portaSegundoNode) throws IOException {
        System.out.println(nodeIP);
        this.portaNode = portaNode;
        this.portaSegundoNode = portaSegundoNode;
        nodeSocket = new DatagramSocket(portaNode);
    }


}
