package hel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*Class that set up the error message form
which has a label with the message and
a OK button*/

public class Erro {
    private JPanel panel1;
    private JButton OKButton;
    private JLabel msg;

    public Erro(String texto) {
        
        //Creates the window frame and set up all his elements
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(300, 150);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        msg.setText(texto);
        
        //OK button listener
        OKButton.addActionListener(new ActionListener() {
            
            //Method executed when the button is pressed
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals(OKButton.getActionCommand())){
                    
                    //Set not visible and close the GUI
                    janela.setVisible(false);
                    janela.dispose();
                }
            }
        });
    }
}
