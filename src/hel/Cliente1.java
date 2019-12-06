package hel;

import java.io.*;
import java.net.Socket;

public class Cliente1 {

    public static void main(String[] args) throws IOException {
        try {
            //Creates a object for all data needed for connection
            ConnectionData data = new ConnectionData("","0.0.0.0", 0, 0, 1);

            //Creates the login form
            loginForm login = new loginForm(data);

            //Stays on this loop until the data object is modified on the login form
            while (data.name.equals("") || data.ip.equals("0.0.0.0") || data.serverPort == 0 || data.textPort == 0|| data.audioPort == 0) {
                Thread.sleep(100);
            }

            //Creates a stream socket and connects it to the specified port number at the specified IP address
            Socket clienteSocket = new Socket(data.ip, data.serverPort);

            //Creates a buffered character-output stream to the server that uses a default-sized output buffer.
            BufferedWriter writeToServer = new BufferedWriter(new OutputStreamWriter(clienteSocket.getOutputStream()));

            //Write two lines for the server, one containing the port and another containing the ip
            writeToServer.write(Integer.toString(data.textPort) + "\n");
            writeToServer.write(clienteSocket.getLocalAddress().getHostAddress() + "\n");
            writeToServer.write(Integer.toString(data.audioPort) + "\n");
            writeToServer.write(data.name + "\n");
            writeToServer.flush();

            //Creates a buffering character-input stream to the server that uses a default-sized input buffer.
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));

			/*Reads two lines of text coming from server
			ont the port and the other the ip of the another client*/
            int portaTextoCliente2 = Integer.parseInt(inFromServer.readLine());
            String ipCliente2 = inFromServer.readLine();
            int portaAudioCliente2 = Integer.parseInt(inFromServer.readLine());
            String nomeCliente2 = inFromServer.readLine();


            System.out.println(portaTextoCliente2);
            System.out.println(ipCliente2);

            //Creates the object node which make the connection p2p between clients
            TextNode textNode = new TextNode(clienteSocket.getInetAddress(), data.textPort, portaTextoCliente2, nomeCliente2);
            NodeAudio audioNode = new NodeAudio(clienteSocket.getInetAddress(), data.audioPort, portaAudioCliente2, ipCliente2);

            //Creates the client form
            gui helgui = new gui(textNode, audioNode, portaTextoCliente2, ipCliente2, clienteSocket);

            //Creates the Thread which the client will be receiving messages
            Thread r1 = new Receber(textNode, 1, helgui);
            r1.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
