package ms.utils.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;

import ms.domain.AuftragsListe;

public class AuftragslistenReceiver extends AbstractServerConnection implements Runnable{

	AuftragsListe list = null;;
	
	public AuftragslistenReceiver(String host, int port)
			throws UnknownHostException, IOException {
		super(host, port);
	}

	public AuftragsListe getTaskList() throws IOException {
		updateTaskList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private void updateTaskList() throws IOException {
		connect();
		sendMessage("INFO");
		logger.info("Receiving Info data...");
		
		list = (AuftragsListe) receiveObject();
		
		disconnect();
		logger.info("INFO transfer finished");		
	}

	@Override
	public void run() {
		while(true) {
			try {
				updateTaskList();
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
