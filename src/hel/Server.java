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
            serverLog serverlog = new serverLog();
            int listaClientes = 0;
            ServerSocket tempSocket = new ServerSocket(3000);


            Conecta c1 = new Conecta(tempSocket, serverlog, 1);
            Conecta c2 = new Conecta(tempSocket, serverlog, 2);
            c1.start();
            c2.start();
            while (true/*c1.status || c2.status*/) {
                System.out.println(c1.status + "|" + c1.first + "|||" + c2.status + "|" + c2.first);
                if (c1.status && c2.status && c1.first) {
                    try {
                        c1.bufferedWriterCliente.write(Integer.toString(c2.cliente.getLocalPort() + 2));
                        System.out.println("Buffer escrito no cliente 1");
                        c1.bufferedWriterCliente.flush();
                        //bufferedWriterCliente1.close();
                        c1.first = false;
                    } catch (Exception e) {
                        c1.first = true;
                        c1.status = false;
                    }
                }
                if (c2.status && c1.status && c2.first) {
                    try {
                        c2.bufferedWriterCliente.write(Integer.toString(c1.cliente.getLocalPort() + 1));
                        System.out.println("Buffer escrito no cliente 2");
                        c2.bufferedWriterCliente.flush();
                        //bufferedWriterCliente2.close();
                        c2.first = false;
                    } catch (Exception e) {
                        c2.first = true;
                        c2.status = false;
                    }
                }
                Thread.sleep(5000);
            }
        } catch (BindException e) {
            System.out.println("Endere�o em uso");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }
}

class Conecta extends Thread {
    Socket cliente;
    ServerSocket tempSocket;
    boolean status, first;
    OutputStream saidaCliente;
    InputStream entradaCliente;
    BufferedWriter bufferedWriterCliente;
    serverLog serverlog;
    int numero;


    Conecta(ServerSocket tempSocket, serverLog serverlog, int numero) {
        this.tempSocket = tempSocket;
        this.serverlog = serverlog;
        this.numero = numero;
        status = false;
        first = true;
    }

    public void run() {
        try {
            while (true) {
                if (!status) {
                    serverlog.write("Esperando pelo cliente " + numero);
                    cliente = tempSocket.accept();
                    serverlog.write("Cliente " + numero + " se conectou.");
                    status = true;
                    saidaCliente = cliente.getOutputStream();
                    entradaCliente = cliente.getInputStream();
                    serverlog.write("OutputStream do cliente " + numero + " criado.");
                    bufferedWriterCliente = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(saidaCliente)));
                    serverlog.write("Buffer do cliente " + numero + " criado.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}