package ch.nomoresecrets.mediastopf.server.networking;

import java.io.IOException;
import java.net.*;


public class NetworkServer implements Runnable {

	public NetworkServer(int port) {
		try {
			mediastop_ServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error: MediaStopf cannot bind to port: " + port);
			System.err.println("Exiting...");
			System.exit(-1);
		}
	}
	
	@Override
	public void run() {	
		//TODO: Is there a better exit condition ??
		while(true) {
			try {
				Socket clientSocket = mediastop_ServerSocket.accept();
				NetworkProcess clientRequest = new NetworkProcess(clientSocket);
				clientRequest.run();
				clientSocket.close();
			} catch (IOException e) {
				System.out.println("Error: Server could not accept connection on port: " + mediastop_ServerSocket.getLocalPort());
			}
		}
		//mediastop_ServerSocket.close();
	}

	private ServerSocket mediastop_ServerSocket;
	
	
}
