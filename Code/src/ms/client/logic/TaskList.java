package ms.client.logic;

import java.util.ArrayList;
import java.util.Observable;

import ms.client.Client;
import ms.client.StartClient;
import ms.logic.Task;

public class TaskList extends Observable {

	private ArrayList<Task> list = new ArrayList<Task>();

	public TaskList() {
		if (StartClient.DEBUG) {
			add(new Task(1111, "Warten"));
			add(new Task(2222, "Fertig"));
			add(new Task(3333, "Senden"));
		}
		updateList();
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

	public void updateList() {
		this.list = Client.getTaskList();
		setChanged();
		notifyObservers();
	}
}
