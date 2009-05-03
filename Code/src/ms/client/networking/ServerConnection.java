package ms.client.networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ms.common.domain.Auftrag;
import ms.common.domain.ImportMedium;
import ms.common.networking.BasicNetIO;

public class ServerConnection extends BasicNetIO {

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
		commSocket.close();
	}

	public void sendImportMedium(ImportMedium m) throws IOException {
		newConnection();
		sendMessage("TRANSFER");
		sendObject(m);
		terminateConnection();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Auftrag> getTaskList() throws IOException {
		newConnection();
		ArrayList<Auftrag> list = null;
		sendMessage("INFO");
		logger.info("Receiving Info data...");
		
		list = (ArrayList<Auftrag>) receiveObject();
		
		terminateConnection();
		logger.info("INFO transfer finished");
		return list;
	}
}
