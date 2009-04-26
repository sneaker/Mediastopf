package ms.client.networking;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ms.common.logic.Task;
import ms.common.networking.BasicNetIO;

public class ServerConnection extends BasicNetIO {

	private final String OK = "OK";
	private int port = 0;
	private String host = null;

	public ServerConnection(String host, int port) throws UnknownHostException,
			IOException {
		this.port = port;
		this.host = host;
	}

	private void newConnection() throws UnknownHostException, IOException {
		commSocket = new Socket(host, port);
	}

	private void terminateConnection() throws IOException {
		sendMessage("END");
		if (receiveMessage().equals("END OK"))
			commSocket.close();
	}

	public void sendFile(String filename) throws IOException {
		newConnection();
		File file = new File(filename);
		if (!file.exists())
			return;

		String reply;
		sendMessage("TRANSFER");
		reply = receiveMessage();
		if (!reply.equals("TRANSFER READY"))
			return;
		sendMessage(filename);
		reply = receiveMessage();
		if (!reply.equals("TRANSFER NAME OK"))
			return;
		Long size = file.length();
		sendMessage(size.toString());
		reply = receiveMessage();
		if (!reply.equals("TRANSFER SIZE OK"))
			return;

		byte[] filebuffer = new byte[size.intValue()];
		FileInputStream mediafilestream = new FileInputStream(filename);
		BufferedInputStream bis = new BufferedInputStream(mediafilestream);

		bis.read(filebuffer, 0, filebuffer.length);

		logger.info("Sending File: " + filename + "...");
		OutputStream sender = commSocket.getOutputStream();
		sender.write(filebuffer, 0, filebuffer.length);
		sender.flush();

		reply = receiveMessage();
		if (reply.equals("ENDTRANSFER"))
			terminateConnection();
	}

	public ArrayList<Task> getTaskList() throws IOException {
		newConnection();
		ArrayList<Task> list = new ArrayList<Task>();
		sendMessage("INFO");
		logger.info("Receiving Info data...");
		String reply;
		while (!(reply = receiveMessage()).equals("ENDINFO")) {
			list.add(new Task(Integer.valueOf(reply), ""));
			logger.info(reply);
			sendMessage(OK);
		}
		terminateConnection();
		logger.info("INFO transfer finished");
		return list;
	}
}
