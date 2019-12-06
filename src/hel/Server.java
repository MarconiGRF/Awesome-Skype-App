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
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    public static void main(String[] args) {
        try {
            BufferedWriter bufferedWriterCliente1, bufferedWriterCliente2;
            serverLog serverlog = new serverLog();

            ServerSocket tempSocket1 = new ServerSocket(3001);
            ServerSocket tempSocket2 = new ServerSocket(3002);


            Conecta c1 = new Conecta(tempSocket1, serverlog, 1);
            Conecta c2 = new Conecta(tempSocket2, serverlog, 2);
            c1.start();
            c2.start();
            while(true) {
                if(c1.status && c2.status && c1.first){
                    BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c1.saidaCliente)));
                    bufferedWriterCliente.write(Integer.toString(c2.textPort)+"\n");
                    bufferedWriterCliente.write(c2.ip+"\n");
                    bufferedWriterCliente.write(Integer.toString(c2.audioPort)+"\n");
                    bufferedWriterCliente.write(c2.nome);
                    bufferedWriterCliente.flush();
                    c1.first = false;
                }
                if(c1.status && c2.status && c2.first){
                    BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c2.saidaCliente)));
                    bufferedWriterCliente.write(Integer.toString(c1.textPort)+"\n");
                    bufferedWriterCliente.write(c1.ip+"\n");
                    bufferedWriterCliente.write(Integer.toString(c1.audioPort)+"\n");
                    bufferedWriterCliente.write(c1.nome);
                    bufferedWriterCliente.flush();
                    c2.first = false;
                }
                if(c1.status && !c1.first){
                    if(c2.status){
                        BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c1.saidaCliente)));
                        bufferedWriterCliente.write("online" +"\n");
                        bufferedWriterCliente.flush();
                    }else{
                        BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c1.saidaCliente)));
                        bufferedWriterCliente.write("offline" +"\n");
                        bufferedWriterCliente.flush();
                    }
                }

                if(c2.status && !c2.first){
                    if(c1.status){
                        BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c2.saidaCliente)));
                        bufferedWriterCliente.write("online" +"\n");
                        bufferedWriterCliente.flush();
                    }else{
                        BufferedWriter bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(c2.saidaCliente)));
                        bufferedWriterCliente.write("offline" +"\n");
                        bufferedWriterCliente.flush();
                    }
                }
                serverlog.changeColor(c1.status, c2.status);

                Thread.sleep(100);
            }
        } catch (BindException e) {
            System.out.println("Endere�o em uso");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro: " + e.getMessage());
        }

    }
}

