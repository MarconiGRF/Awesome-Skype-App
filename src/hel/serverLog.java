package hel;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class serverLog {
    private JTextPane textPane1;
    private JPanel panel1;
    private JLabel cliente1_status;
    private JPanel panel1_status;
    private JPanel panel2_status;
    private JPanel panel_status;
    private JLabel cliente2_status;
    Icon red, green;

    public serverLog(){
        JFrame window = new JFrame();
        window.setContentPane(panel1);
        window.setVisible(true);
        window.pack();
        window.setSize(400,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String currentRelativePath = Paths.get(".").toAbsolutePath().normalize().toString();
        String path_Red = FileSystems.getDefault().getPath("/src/hel/images/red.png").toString();
        String path_Green = FileSystems.getDefault().getPath("/src/hel/images/green.png").toString();
        red = new ImageIcon(currentRelativePath + path_Red);
        green = new ImageIcon(currentRelativePath + path_Green);

    }

    public void changeColor(boolean c1, boolean c2){
        if(c1){
            cliente1_status.setIcon(green);
        }else{
            cliente1_status.setIcon(red);

        }
        if(c2){
            cliente2_status.setIcon(green);
        }else{
            cliente2_status.setIcon(red);
        }
    }

    public void write(String thing) throws BadLocationException {
        //textArea1.append(thing + "\n");
        Document doc = textPane1.getDocument();
        doc.insertString(doc.getLength(), thing + "\n", null);
    }
}