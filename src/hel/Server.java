package hel;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
			try {
				serverLog serverlog = new serverLog();
				int listaClientes = 0;
				ServerSocket tempSocket1 = new ServerSocket(3001);
				serverlog.write("Esperando pelo primeiro cliente.");
				Socket cliente1 = tempSocket1.accept();
				OutputStream saidaCliente1 = cliente1.getOutputStream();
				System.out.println("Conectado primeiro cliente!");
				ServerSocket tempSocket2 = new ServerSocket(3002);
				serverlog.write("Esperando pelo segundo cliente.");
				Socket cliente2 = tempSocket2.accept();
				OutputStream saidaCliente2 = cliente2.getOutputStream();
				System.out.println("Conectado segundo cliente!");
				BufferedWriter bufferedWriterCliente1 = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(saidaCliente1)));
				bufferedWriterCliente1.write(Integer.toString(cliente2.getLocalPort()));
				bufferedWriterCliente1.flush();
				bufferedWriterCliente1.close();
				BufferedWriter bufferedWriterCliente2 = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(saidaCliente2)));
				bufferedWriterCliente2.write(Integer.toString(cliente1.getLocalPort()));
				bufferedWriterCliente2.flush();
				bufferedWriterCliente2.close();
				//while(true){
					//passagem de audio,monitoramento dos clientes e talz
				//}
				cliente1.close();
				cliente2.close();
			} catch (BindException e) {
				System.out.println("Endere�o em uso");
			} catch (Exception e) {
				System.out.println("Erro: " + e);
			}

	}
}
