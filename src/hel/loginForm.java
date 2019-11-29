package hel;

import javax.swing.*;

public class loginForm {
    private JTextField textField1;
    private JButton entrarButton;
    private JPanel panel1;

    public loginForm() {
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(600, 480);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}