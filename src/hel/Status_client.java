package hel;

import java.io.*;
import java.net.Socket;

class Status_client extends Thread {
    Socket tempSocket;

    Status_client(Socket tempSocket) {
        this.tempSocket = tempSocket;
    }

    public void run() {
        try {
            BufferedWriter writeToServer = new BufferedWriter(new OutputStreamWriter(tempSocket.getOutputStream()));
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()));
            while (true) {
                writeToServer.write("on\n");
                writeToServer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
