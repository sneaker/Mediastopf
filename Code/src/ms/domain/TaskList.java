package ms.domain;

import java.util.ArrayList;
import java.util.Observable;

public class TaskList extends Observable {
	
	private ArrayList<Task> list = new ArrayList<Task>();
	
	public void add(Task auftrag) {
		list.add(auftrag);
		setChanged();
		notifyObservers();
	}

	public void add(ArrayList<Task> al) {
		list = al;
	}

	public void remove(Task auftrag) {
		list.remove(auftrag);
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
