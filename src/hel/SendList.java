package hel;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class SendList extends Thread {
    private Node node;
    public boolean clientstatus;
    private int portacliente;
    private String ipCliente2;
    private LinkedList<String> unsent;
    private boolean unsentmsgs;

    public SendList(Node node, int portacliente, String ipCliente2){
        this.node = node;
        this.portacliente = portacliente;
        this.unsent = new LinkedList<>();
        this.ipCliente2 = ipCliente2;
    }

    void update(boolean statusupdate){
        this.clientstatus = statusupdate;
        if(clientstatus && unsentmsgs){
            while(!unsent.isEmpty()){
                Thread s1 = null;
                try {
                    s1 = new Enviar(node, InetAddress.getByName(ipCliente2),portacliente,unsent.poll());
                    s1.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    Thread.sleep(20);
                }
                catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            unsentmsgs = false;
        }
    }

    void sentolist(String texto, boolean statusupdate){
        this.clientstatus = statusupdate;
        if(clientstatus){
            Thread s1 = null;
            try {
                s1 = new Enviar(node, InetAddress.getByName(ipCliente2),portacliente,texto);
                s1.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            unsentmsgs = true;
            unsent.add(texto);
        }
    }


}