package ch.nomoresecrets.mediastopf.client.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class ServerConnection {
	
	public ServerConnection(String host, int port) throws UnknownHostException, IOException
	{
		mediastopfSocket = new Socket(host, port);
	}
	
	public void Send(String message) throws IOException {
		PrintWriter sender = new PrintWriter(mediastopfSocket.getOutputStream(), true);
		sender.println(message);
	}

	private Socket mediastopfSocket = null;
}
