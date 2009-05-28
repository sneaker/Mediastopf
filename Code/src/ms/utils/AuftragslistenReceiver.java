package ms.utils;

import java.util.ArrayList;
import java.util.Observable;

public class AuftragslistenReceiver extends Observable implements Runnable{

	public static final int TIMEOUT = 30000; // set the timeout when to refresh the Auftragsliste
	
	@SuppressWarnings("unchecked")
	// local cache of the list
	private ArrayList list;
	private AuftragslistenUpdater updater;
	
	@SuppressWarnings("unchecked")
	public AuftragslistenReceiver(AuftragslistenUpdater updater) {
		list = new ArrayList();
		this.updater = updater; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList getTaskList() {
		return list;
	}
	
	public void updateTaskList() {
		list = updater.updateList();
	}

	public void run() {
		while(true) {
			try {
				updateTaskList();
				setChanged();
				notifyObservers();
				Thread.sleep(TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		updater.stop();
	}
}
