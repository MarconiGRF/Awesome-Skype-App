package hel;

import java.io.IOException;
import java.net.InetAddress;

class  Enviar extends Thread{
    private Node node;
    InetAddress segundoNodeIP;
    int portaSegundoNode;
    String texto;

    public Enviar(Node node, InetAddress segundoNodeIP, int portaSegundoNode, String texto) throws IOException {
        this.node = node;
        this.segundoNodeIP = segundoNodeIP;
        this.portaSegundoNode = portaSegundoNode;
        this.texto = texto;
    }

    public void run(){
        try {
            node.enviar(segundoNodeIP, portaSegundoNode, texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}