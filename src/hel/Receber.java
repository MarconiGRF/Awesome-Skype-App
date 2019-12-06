package hel;

import javax.swing.text.BadLocationException;
import java.io.IOException;

class Receber extends Thread{
    private TextNode textNode;
    int numNode;
    gui Gui;

    public Receber(TextNode textNode, int numNode, gui helgui) {
        this.textNode = textNode;
        this.numNode = numNode;
        this.Gui = helgui;
    }

    public void run() {
        while(true) {
            try {
                textNode.receber(numNode, Gui);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
