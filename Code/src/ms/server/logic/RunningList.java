package ms.server.logic;

import java.util.ArrayList;
import java.util.Observable;

import ms.common.logic.Task;
import ms.server.StartServer;


public class RunningList extends Observable {
	
	private ArrayList<Task> list = new ArrayList<Task>();
	
	public RunningList() {
		if(StartServer.DEBUG) {
			for(int i=10; i < 20; i++) {
				add(new Task(i, "test"));
			}
		}
	}
	
	public void add(Task o) {
		list.add(o);
		setChanged();
		notifyObservers();
	}
	
	public void remove(Task o) {
		list.remove(o);
		setChanged();
		notifyObservers();
	}
	
	public void remove(int index) {
		list.remove(index);
		setChanged();
		notifyObservers();
	}
	
	public Task get(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
