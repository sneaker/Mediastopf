package ms.utils.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ms.domain.Auftrag;
import ms.domain.AuftragsListe;

public class AuftragslistenReceiver extends AbstractServerConnection implements Runnable{

	public static final int TIMEOUT = 10000;
	
	public AuftragsListe list;
	
	public AuftragslistenReceiver(String host, int port, Class<?> controller)
			throws UnknownHostException, IOException {
		super(host, port);
		list = new AuftragsListe(controller);
	}

	public ArrayList<Auftrag> getTaskList() throws IOException {
		updateTaskList();
		ArrayList<Auftrag> result = new ArrayList<Auftrag>();
		for(int i = 0; i < list.size(); ++i)
		{
			result.add(list.get(i));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void updateTaskList() throws IOException {
		sendMessage("INFO");
		logger.info("Receiving Info data...");
		
		list.add((ArrayList<Auftrag>) receiveObject());
		if (list != null){
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
		
		logger.info("INFO transfer finished");		
	}

	@Override
	public void run() {
		while(true) {
			try {
				updateTaskList();
				Thread.sleep(TIMEOUT);
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