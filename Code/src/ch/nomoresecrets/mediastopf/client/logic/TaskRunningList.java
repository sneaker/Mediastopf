package ch.nomoresecrets.mediastopf.client.logic;

import java.util.ArrayList;
import java.util.Observable;

import ch.nomoresecrets.mediastopf.client.StartClient;

public class TaskRunningList extends Observable {
	
	private ArrayList<Task> list = new ArrayList<Task>();
	
	public TaskRunningList() {
		if(StartClient.DEBUG) {
			for(int i=0; i < 10; i++) {
				add(new Task(i, "Test"));
			}
			add(new Task(1111, "Waiting"));
			add(new Task(2222, "Ready"));
			add(new Task(3333, "Sending"));
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
