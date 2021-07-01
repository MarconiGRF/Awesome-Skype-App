package hel;

import javax.swing.text.BadLocationException;
import java.io.IOException;

class Receber extends Thread{
    private Node node;
    int numNode;
    gui Gui;

    public Receber(Node node, int numNode, gui helgui) {
        this.node = node;
        this.numNode = numNode;
        this.Gui = helgui;
    }

    public void run() {
        while(true) {
            try {
                node.receber(numNode, Gui);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
