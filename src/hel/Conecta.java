package hel;

import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Conecta extends Thread {
    Socket cliente;
    ServerSocket tempSocket;
    boolean status, first;
    OutputStream saidaCliente;
    InputStream entradaCliente;

    BufferedReader in;
    BufferedReader inFromClient;

    serverLog serverlog;
    int numero, port;


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
                    inFromClient = new BufferedReader(new InputStreamReader(entradaCliente));
                    port = Integer.parseInt(inFromClient.readLine());
                    System.out.println(port);
                }
                try {
                    while(inFromClient.readLine() != null){
                        status = true;
                    }
                }catch (Exception e){
                    serverlog.write("Cliente " + numero + " se desconectou :(");
                    status = false;
                    first = true;
                }
                //Thread.sleep(3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


}