package hel;

import javax.swing.text.BadLocationException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        try {
            BufferedWriter bufferedWriterCliente1, bufferedWriterCliente2;
            serverLog serverlog = new serverLog();
            int listaClientes = 0;
            ServerSocket tempSocket1 = new ServerSocket(3001);
            ServerSocket tempSocket2 = new ServerSocket(3002);

            Conecta c1 = new Conecta(tempSocket1, serverlog, 1);
            Conecta c2 = new Conecta(tempSocket2, serverlog, 2);
            c1.start();
            c2.start();
            while(true) {
                if(c1.status && c2.status && c1.first){
                    BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c1.saidaCliente)));
                    bufferedWriterCliente.write(Integer.toString(c2.port)+"\n");
                    bufferedWriterCliente.flush();
                    c1.first = false;
                }
                if(c1.status && c2.status && c2.first){
                    BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c2.saidaCliente)));
                    bufferedWriterCliente.write(Integer.toString(c1.port)+"\n");
                    bufferedWriterCliente.flush();
                    c2.first = false;
                }
                Thread.sleep(1000);
            }
        } catch (BindException e) {
            System.out.println("Endere�o em uso");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }
}

