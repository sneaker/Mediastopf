package ms.utils.networking.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;
import ms.utils.networking.BasicNetIO;
import ms.utils.server.database.SqlDbAdapter;

public class NetProcThread extends BasicNetIO implements Runnable {

	public NetProcThread(Socket clientSocket) {
		commSocket = clientSocket;
	}

	public void run() {
		String receivedMessage = null;
		while (true) {
			try {
				receivedMessage = receiveMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (receivedMessage == null) {
				SocketAddress remoteaddr = commSocket.getRemoteSocketAddress();
				logger.info("Client " + remoteaddr.toString() + " has disconnected");
				return;
			}

			if (receivedMessage.equals("INFO")) {
				sendTaskList();
			}

			if (receivedMessage.equals("TRANSFER")) {
				ImportMedium m = (ImportMedium) receiveObject();
			}
		}
	}

	private void sendTaskList() {
		List<Auftrag> lp = SqlDbAdapter.getOrderList();
		sendObject(lp);
	}
}
