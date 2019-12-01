package hel;
/*This class is only for pass the data 
connection for reference between classes*/

public class ConnectionData {
    String name, ip;
    int serverPort, textPort, audioPort;

    ConnectionData(String name, String ip, int serverPort, int textPort, int audioPort){
        this.name = name;
        this.ip = ip;
        this.serverPort = serverPort;
        this.textPort = textPort;
        this.audioPort = audioPort;
    }
}
