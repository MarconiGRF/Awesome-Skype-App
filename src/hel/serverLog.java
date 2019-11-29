package hel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class serverLog {
    private JTextPane textPane1;
    private JPanel panel1;

    public serverLog(){
        JFrame window = new JFrame();
        window.setContentPane(panel1);
        window.setVisible(true);
        window.pack();
        window.setSize(400,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void write(String thing) throws BadLocationException {
        //textArea1.append(thing + "\n");
        Document doc = textPane1.getDocument();
        doc.insertString(doc.getLength(), thing + "\n", null);
    }
}