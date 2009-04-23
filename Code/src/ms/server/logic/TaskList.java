package ms.server.logic;

import java.util.ArrayList;
import java.util.Observable;

import ms.common.logic.Task;
import ms.server.Server;

public class TaskList extends Observable {

	private ArrayList<Integer> list = new ArrayList<Integer>();

	public TaskList() {
		updateList();
	}

	private void updateList() {
		ArrayList<Task> tasklist = Server.getDataBase();
		for (Task a : tasklist) {
			list.add(a.getID());
		}
		setChanged();
		notifyObservers();
	}

	public void add(int task) {
		list.add(task);
		setChanged();
		notifyObservers();
	}

	public void remove(String task) {
		list.remove(task);
		setChanged();
		notifyObservers();
	}

	public void remove(int index) {
		list.remove(index);
		setChanged();
		notifyObservers();
	}

	public int get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}
}
