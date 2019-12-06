package hel;

import javax.swing.text.BadLocationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/* Listen class implements the Thread which
the server will be waiting for the client to
connect, if the client disconect, the server
will start to waiting for the client connect
again */

class Conecta extends Thread {
    Socket cliente;
    ServerSocket tempSocket;
    boolean status, first;
    OutputStream saidaCliente;
    InputStream entradaCliente;
    BufferedReader in;
    BufferedReader inFromClient;
    serverLog serverlog;
    int numero, textPort, audioPort;
    String ip, nome;

    // Listen constructor
    Conecta(ServerSocket tempSocket, serverLog serverlog, int numero) {
        this.tempSocket = tempSocket;
        this.serverlog = serverlog;
        this.numero = numero;
        status = false;
        first = true;
    }

    //Method that overrides the method of the thread class
    public void run() {
        try {
            
            //Loop that keep waiting for the client connection
            while (true) {
                if (!status) {
                    serverlog.write("Esperando pelo cliente " + numero);
                    
                    //Listens for a connection to be made to this socket and accepts it.
                    cliente = tempSocket.accept();
                    serverlog.write("Cliente " + numero + " se conectou.");
                    
                    //Set the status of the client to true(connected)
                    status = true;
                    
                    //Creates the output stream for this client socket
                    saidaCliente = cliente.getOutputStream();
                    
                    //Creates the input stream for this client socket
                    entradaCliente = cliente.getInputStream();
                   
                    //Creates a buffered character-input stream to the client that uses a default-sized output buffer
                    inFromClient = new BufferedReader(new InputStreamReader(entradaCliente));
                    
                    /*Reads two lines of text coming from the client
			        one is the textPort and the other the ip of this client*/
                    textPort = Integer.parseInt(inFromClient.readLine());
                    ip = inFromClient.readLine();
                    audioPort = Integer.parseInt(inFromClient.readLine());
                    nome = inFromClient.readLine();


                }
                try {
                    
                    /*Loop to check if the client it's connected
                    Keep reading lines of text comming from the client*/
                    while(inFromClient.readLine() != null){
                        status = true;
                    }
                    
                //If a exception is caught, the client has disconnected
                }catch (Exception e){
                    serverlog.write("Cliente " + numero + " se desconectou :(");
                    
                    //Set the status of the client to false(disconnected)
                    status = false;
                    
                    //Set the firts connection to true
                    first = true;
                }
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/ catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
