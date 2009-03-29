package ch.nomoresecrets.mediastopf.client.networking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import ch.nomoresecrets.mediastopf.client.log.Log;

public class ServerConnection {
	
	private Logger logger = Log.getLogger();

	public ServerConnection(String host, int port) throws UnknownHostException, IOException {
		mediastopfSocket = new Socket(host, port);
	}

	public void sendMessage(String message) throws IOException {
		PrintWriter sender = new PrintWriter(mediastopfSocket.getOutputStream(), true);
		sender.println(message);
	}
	
	public void sendFile(String filename) throws IOException {
		sendMessage("TRANSFER");
		File mediafile = new File(filename);
		byte[] filebuffer = new byte[(int)mediafile.length()];
		FileInputStream mediafilestream = new FileInputStream(mediafile);
		BufferedInputStream bis = new BufferedInputStream(mediafilestream);
		
		bis.read(filebuffer,0,filebuffer.length);
		
		OutputStream sender = mediastopfSocket.getOutputStream();
		logger.info("Sending File: " + filename + "...");
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
