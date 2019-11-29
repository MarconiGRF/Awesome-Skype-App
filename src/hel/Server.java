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
            ServerSocket tempSocket = new ServerSocket(3000);
            Conecta c1 = new Conecta(tempSocket, serverlog, 1);
            Conecta c2 = new Conecta(tempSocket, serverlog, 2);
            c1.start();
            c2.start();

            while(true) {
                System.out.println(c1.status + "|" + c1.first);
                System.out.println(c2.status + "|" + c2.first);

                if(c1.status && c2.status && c1.first){
                    c1.outToClient.write(c2.cliente.getLocalPort());
                    c1.first = false;
                }
                if(c1.status && c2.status && c2.first){
                    c2.outToClient.write(c1.cliente.getLocalPort());
                    c2.first = false;
                }
                Thread.sleep(3000);
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
    DataInputStream inFromClient;
    DataOutputStream outToClient;
    BufferedReader in;

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
                    inFromClient =  new DataInputStream(entradaCliente);
                    outToClient =   new DataOutputStream(saidaCliente);
                }
                try {
                    String clientLogin = inFromClient.readUTF();
                    if(clientLogin.equals("login")){
                        String data = null;
                        in = new BufferedReader(new InputStreamReader(inFromClient));
                    }
                }catch (Exception e){
                    serverlog.write("Cliente " + numero + " se desconectou :(");
                    status = false;
                    first = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }


}