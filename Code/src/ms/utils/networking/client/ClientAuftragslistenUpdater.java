package ms.utils.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ms.domain.Auftrag;
import ms.utils.AuftragslistenUpdater;

public class ClientAuftragslistenUpdater extends AbstractServerConnection implements AuftragslistenUpdater {
	
	public ClientAuftragslistenUpdater(String host, int port)
			throws UnknownHostException, IOException {
		super(host, port);
		connect();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Auftrag> updateList() {
		ArrayList list;
		try {
			sendMessage("INFO");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Receiving INFO data...");
		list = ((ArrayList) receiveObject());
		logger.info("INFO transfer finished");
		return list;
	}

	public void stop() {
		try {
			commSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
