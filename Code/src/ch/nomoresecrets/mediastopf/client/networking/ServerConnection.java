package ch.nomoresecrets.mediastopf.client.networking;

import java.io.*;
import java.net.*;

public class ServerConnection {

	public ServerConnection(String host, int port) throws UnknownHostException,
			IOException {
		mediastopfSocket = new Socket(host, port);
	}

	public void sendMessage(String message) throws IOException {
		PrintWriter sender = new PrintWriter(
				mediastopfSocket.getOutputStream(), true);
		sender.println(message);
	}
	
	public void sendFile(String filename) throws IOException {
		File mediafile = new File(filename);
		byte[] filebuffer = new byte[(int)mediafile.length()];
		FileInputStream mediafilestream = new FileInputStream(mediafile);
		BufferedInputStream bis = new BufferedInputStream(mediafilestream);
		
		bis.read(filebuffer,0,filebuffer.length);
		
		OutputStream sender = mediastopfSocket.getOutputStream();
		System.out.println("Sending File: " + filename + "...");
		sender.write(filebuffer,0,filebuffer.length);
		sender.flush();
		sendMessage("TRANSFER finished");
	}

	public String receiveMessage() throws IOException {
		BufferedReader clientreceiver = new BufferedReader(
				new InputStreamReader(mediastopfSocket.getInputStream()));
		return clientreceiver.readLine();
	}

	private Socket mediastopfSocket = null;
}
