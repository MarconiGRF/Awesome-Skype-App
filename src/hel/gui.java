package hel;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;


public class gui {
    private JButton button1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton audioButton;
    private JTextPane textArea;


    public gui(TextNode textNode, NodeAudio audioNode, int portaCliente2, String ipCliente2, Socket clienteSocket) throws LineUnavailableException {
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(600,480);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Status_client client = new Status_client(clienteSocket);
        client.start();
        Recorder rec = new Recorder();
        SendList armazenar = new SendList(textNode, portaCliente2, ipCliente2);
        final SendAudio[] audio = new SendAudio[1];
        final ReceiveAudio[] receive = new ReceiveAudio[1];


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(button1.getActionCommand()));{
                    if(!textField1.getText().equals("")) {
                        armazenar.update(client.friend);
                        armazenar.sentolist(textField1.getText(), client.friend);
                        try {
                            write("Você: " + textField1.getText(), true);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                        textField1.setText("");
                    }
                }
            }
        });


        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(button1.getActionCommand()));{
                    if(!textField1.getText().equals("")) {
                        armazenar.update(client.friend);
                        armazenar.sentolist(textField1.getText(), client.friend);
                        try {
                            write("Você: " + textField1.getText(), true);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                        textField1.setText("");
                    }
                }
            }
        });

        audioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(button1.getActionCommand()));{
                    if(audioButton.getText().equals("Start")){
                        receive[0] = new ReceiveAudio(audioNode, rec);
                        receive[0].start();
                        audio[0] = new SendAudio(audioNode, rec);
                        audio[0].start();
                        audioButton.setText("Stop");
                    }else{
                        receive[0].stop();
                        audio[0].stop();
                        audioButton.setText("Start");
                    }
                }
            }
        });
    }

    public void write(String thing, boolean self) throws BadLocationException {
        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(left, Color.RED);

        SimpleAttributeSet right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(right, Color.BLUE);
        StyledDocument doc = textArea.getStyledDocument();
        if(!self){
            doc.insertString(doc.getLength(), "\n"+thing, right);
            doc.setParagraphAttributes(doc.getLength(), 1, right, false);
        }else {
            doc.insertString(doc.getLength(), "\n" + thing, left);
            doc.setParagraphAttributes(doc.getLength(), 1, left, false);
        }
    }
}
