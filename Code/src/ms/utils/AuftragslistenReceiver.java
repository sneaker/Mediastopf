package ms.utils;

import java.util.ArrayList;
import java.util.Observable;


public class AuftragslistenReceiver extends Observable implements Runnable{

	public static final int TIMEOUT = 10000;
	
	@SuppressWarnings("unchecked")
	private ArrayList list;
	AuftragslistenUpdater updater;
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		updater.stop();
	}
}
