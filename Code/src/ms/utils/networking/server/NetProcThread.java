package ms.utils.networking.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;
import ms.utils.networking.BasicNetIO;
import ms.utils.server.database.DbAuftragsManager;

public class NetProcThread extends BasicNetIO implements Runnable {

	private ArrayList<ImportMedium> received_cache = null;

	public NetProcThread(Socket clientSocket, ArrayList<ImportMedium> buffer) {
		commSocket = clientSocket;
		received_cache = buffer;
	}

	public void run() {
		String receivedMessage = null;
		while (true) {
			try {
				if (commSocket.isConnected()) {
					receivedMessage = receiveMessage();
				} else
					System.out.println("not connected");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (receivedMessage == null) {
				SocketAddress remoteaddr = commSocket.getRemoteSocketAddress();
				logger.info("Client " + remoteaddr.toString()
						+ " has disconnected");
				return;
			}

			if (receivedMessage.equals("INFO")) {
				sendTaskList();
			}

			if (receivedMessage.equals("TRANSFER")) {
				ImportMedium m = receiveImportMedium();
				received_cache.add(m);
			}
		}
	}
	
	private ImportMedium receiveImportMedium() {
		ImportMedium m = null;
		try {
			sendMessage("TRANSFER READY");
			
			InputStream is = commSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			m = (ImportMedium) ois.readObject();
			
			if (receiveMessage().equals("END TRANSFER"))
				sendMessage("END OK");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return m;
	}

	private void sendTaskList() {
		List<Auftrag> lp = DbAuftragsManager.getinstance(null).getAuftragList();
		sendObject(lp);
	}
}
