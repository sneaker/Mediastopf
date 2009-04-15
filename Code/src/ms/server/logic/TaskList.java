package ms.server.logic;

import java.util.ArrayList;
import java.util.Observable;

import ms.server.Server;
import ms.server.domain.Auftrag;


public class TaskList extends Observable {
	
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private Server server;
	
	public TaskList(Server server) {
		this.server = server;
		updateList();
	}

	private void updateList() {
		ArrayList<Auftrag> tasklist = server.getDataBase();
		for(Auftrag a: tasklist) {
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
