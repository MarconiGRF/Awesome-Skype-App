package hel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class loginForm {
    private JTextField serverIP;
    private JButton entrarButton;
    private JPanel panel1;
    private JTextField serverPort;
    private JTextField nome;
    private JTextField textPort;
    private JTextField audioPort;
    String name, ip, port_server, port_text, port_audio;

    public loginForm(ConnectionData data) {
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(300, 250);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(entrarButton.getActionCommand()));{
                    name = nome.getText();
                    ip = serverIP.getText();
                    port_server = serverPort.getText();
                    port_text = textPort.getText();
                    port_audio = audioPort.getText();
                    if(name.equals("") || ip.equals("") || port_server.equals("") || port_text.equals("") || port_audio.equals("")){
                        Erro erro = new Erro("Todos campos são obrigatórios");
                    }else{
                        try {
                            if(InetAddress.getByName(ip).isReachable(1000)){
                                data.name = name;
                                data.ip = ip;
                                data.serverPort = Integer.parseInt(port_server);
                                data.textPort = Integer.parseInt(port_text);
                                data.audioPort = Integer.parseInt(port_audio);
                                janela.setVisible(false);
                                janela.dispose();
                            }else{
                                Erro erro = new Erro("Erro de conexão!");
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }



}