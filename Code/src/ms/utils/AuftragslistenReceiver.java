package ms.utils;

import java.util.ArrayList;


public class AuftragslistenReceiver implements Runnable{

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

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("Threads updates itself");
				updateTaskList();
				Thread.sleep(TIMEOUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
