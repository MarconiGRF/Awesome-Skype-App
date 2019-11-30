package hel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {


    public static void main(String[] args) throws IOException {
        try {
            ConnectionData data = new ConnectionData("","0.0.0.0", 0, 0, 1);
            loginForm login = new loginForm(data);
            while (data.name.equals("") || data.ip.equals("0.0.0.0") || data.serverPort == 0 || data.textPort == 0|| data.audioPort == 0) {
                Thread.sleep(100);
            }
            Socket clienteSocket = new Socket(data.ip, data.serverPort);

            BufferedWriter writeToServer = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));
            writeToServer.write(Integer.toString(data.textPort) + "\n");
            writeToServer.write(clienteSocket.getInetAddress().getHostAddress() + "\n");
            writeToServer.flush();

            System.out.println("Socket iniciado com o servidor.");
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            System.out.println("Buffer lido do servidor.");
            int portaCliente2 = Integer.parseInt(inFromServer.readLine());
            String ipCliente2 = inFromServer.readLine();
            Status_client client = new Status_client(clienteSocket);
            client.start();
            System.out.println("Porta do outro cliente: " + portaCliente2);
            System.out.println("Messagem lida do buffer");
            Node u1 = new Node(clienteSocket.getInetAddress(), data.textPort, portaCliente2, data.name);
            System.out.println("Conexao criada com outro cliente");

            //loginForm login = new loginForm();
            gui helgui = new gui(u1, portaCliente2, ipCliente2, clienteSocket);
            Thread r1 = new Receber(u1, 2, helgui);
            r1.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}