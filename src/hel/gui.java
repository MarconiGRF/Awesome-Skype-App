package hel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.IOException;
import hel.Enviar;
import hel.Node;


public class gui {
    private JButton button1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton audioButton;


    public gui(Node u1, int portaCliente2){
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(600,480);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(button1.getActionCommand()));{
                    Thread s1 = null;
                    try {
                        s1 = new Enviar(u1, InetAddress.getByName("localhost"),portaCliente2,textField1.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    s1.start();
                    write("Você: " + textField1.getText());
                    textField1.setText("");
                }
            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(button1.getActionCommand()));{
                    Thread s1 = null;
                    try {
                        s1 = new Enviar(u1, InetAddress.getByName("localhost"),portaCliente2,textField1.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    s1.start();
                    write("Você: " + textField1.getText());
                    textField1.setText("");
                }
            }
        });
    }

    public void write(String thing){
        textArea1.append(thing + "\n");
    }

}
