package ms.utils.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ms.domain.Auftrag;

public class AuftragslistenReceiver extends AbstractServerConnection implements Runnable{

	ArrayList<Auftrag> list = null;;
	
	public AuftragslistenReceiver(String host, int port)
			throws UnknownHostException, IOException {
		super(host, port);
	}

	public ArrayList<Auftrag> getTaskList() throws IOException {
		updateTaskList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private void updateTaskList() throws IOException {
		connect();
		sendMessage("INFO");
		logger.info("Receiving Info data...");
		
		list = (ArrayList<Auftrag>) receiveObject();
		
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
