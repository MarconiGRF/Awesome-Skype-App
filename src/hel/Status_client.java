package hel;

import java.io.*;
import java.net.Socket;

class Status_client extends Thread {
    Socket tempSocket;
    boolean friend;

    Status_client(Socket tempSocket) {
        this.tempSocket = tempSocket;
    }

    public void run() {
        try {
            BufferedWriter writeToServer = new BufferedWriter(new OutputStreamWriter(tempSocket.getOutputStream()));
            BufferedReader readFromServer = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()));
            while (true) {
                writeToServer.write("on\n");
                writeToServer.flush();
                String friedStatus = readFromServer.readLine();
                if(friedStatus.equals("online")){
                    friend = true;
                }else{
                    friend = false;
                }
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
