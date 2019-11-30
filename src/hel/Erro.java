package hel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Erro {
    private JPanel panel1;
    private JButton OKButton;
    private JLabel msg;

    public Erro(String texto) {
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(300, 150);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        msg.setText(texto);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals(OKButton.getActionCommand())){
                    janela.setVisible(false);
                    janela.dispose();
                }
            }
        });
    }
}