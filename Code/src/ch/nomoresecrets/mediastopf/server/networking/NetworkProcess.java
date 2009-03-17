package ch.nomoresecrets.mediastopf.server.networking;

import java.net.*;
import java.io.*;

public class NetworkProcess implements Runnable {

	public NetworkProcess(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try {
			getInput();
		} catch (IOException e) {
			System.out.println("Error: Cannot get Input");
			e.printStackTrace();
		}
		
	}

	private void getInput() throws IOException
	{
		BufferedReader receiver = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String inputMessage;
		
		while((inputMessage = receiver.readLine()) != null) {
			System.out.println("A message from a client: \n");
			System.out.println(inputMessage);
		}
			
		receiver.close();
	}
	
	private Socket clientSocket;
}
