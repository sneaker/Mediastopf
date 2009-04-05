package ms.client.networking;

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
import java.util.ArrayList;

import ms.client.log.Log;
import ms.client.logic.Task;

import org.apache.log4j.Logger;


public class NetworkClient {
	
	private Logger logger = Log.getLogger();
	private Socket mediastopfSocket = null;
	private final String OK = "OK";
	
	public NetworkClient(String host, int port) throws UnknownHostException, IOException {
		mediastopfSocket = new Socket(host, port);
	}

	private void sendMessage(String message) throws IOException {
		logger.info(message);
		PrintWriter sender = new PrintWriter(mediastopfSocket.getOutputStream(), true);
		sender.println(message);
	}

	private String receiveMessage() throws IOException {
		BufferedReader clientreceiver = new BufferedReader(
				new InputStreamReader(mediastopfSocket.getInputStream()));
		return clientreceiver.readLine();
	}

	public void sendFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists())
			return;
		
		sendMessage("TRANSFER");
		if (!receiveMessage().equals("TRANSFER READY"))
			return;
		sendMessage(filename);
		if (!receiveMessage().equals("TRANSFER NAME OK"))
			return;
		Long size = file.length();
		sendMessage(size.toString());
		if (!receiveMessage().equals("TRANSFER SIZE OK"))
			return;
		
		byte[] filebuffer = new byte[size.intValue()];
		FileInputStream mediafilestream = new FileInputStream(filename);
		BufferedInputStream bis = new BufferedInputStream(mediafilestream);
		
		bis.read(filebuffer,0,filebuffer.length);
		
		OutputStream sender = mediastopfSocket.getOutputStream();
		logger.info("Sending File: " + filename + "...");
		sender.write(filebuffer,0,filebuffer.length);
		sender.flush();
		
		sendMessage("ENDTRANSFER");
	}

	public ArrayList<Task> getTaskList() throws IOException {
		ArrayList<Task> list = new ArrayList<Task>();
		sendMessage("INFO");
		logger.info("Receiving Info data...");
		String reply;
		while(true) {
			reply = receiveMessage();
			if (reply.equals("ENDINFO"))
				break;
			else {
				list.add(new Task(0, reply));
				logger.info(reply);
				sendMessage(OK);
			}
		}
		logger.info("INFO transfer finished");
		return list;
	}
}
