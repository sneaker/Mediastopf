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
	@Override
	public ArrayList<Auftrag> updateList() {
		ArrayList list;
		try {
			sendMessage("INFO");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Receiving Info data...");
		
		list = ((ArrayList) receiveObject());
		
		//debug
		if (list != null){
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
		logger.info("INFO transfer finished");
		return list;
	}
	
}