package hel;

import java.io.IOException;
import java.net.InetAddress;
/*This class implements the thread
for send messages*/

class  Enviar extends Thread{
    private TextNode textNode;
    InetAddress segundoNodeIP;
    int portaSegundoNode;
    String texto;
    
    //Send constructor
    public Enviar(TextNode textNode, InetAddress segundoNodeIP, int portaSegundoNode, String texto) throws IOException {
        this.textNode = textNode;
        this.segundoNodeIP = segundoNodeIP;
        this.portaSegundoNode = portaSegundoNode;
        this.texto = texto;
    }

    //Overrides the run method of the thread class
    public void run(){
        try {
            //Call the node method send
            textNode.enviar(segundoNodeIP, portaSegundoNode, texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
