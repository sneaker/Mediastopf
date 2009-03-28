package ch.nomoresecrets.mediastopf.client.logic;

import java.util.ArrayList;
import java.util.Observable;

import ch.nomoresecrets.mediastopf.client.StartClient;

public class TaskList extends Observable {
	
	private ArrayList<String> list = new ArrayList<String>();
	
	public TaskList() {
		if(!StartClient.DEBUG) {
			for(int i=0; i < 10; i++) {
				add("test" + i);
			}
		}
	}
	
	public void add(String task) {
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
	
	public String get(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
