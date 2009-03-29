package ch.nomoresecrets.mediastopf.server.logic;

import java.util.ArrayList;
import java.util.Observable;

import ch.nomoresecrets.mediastopf.server.StartServer;
import ch.nomoresecrets.mediastopf.server.database.DbAdapter;
import ch.nomoresecrets.mediastopf.server.domain.Auftrag;

public class TaskList extends Observable {
	
	private ArrayList<String> list = new ArrayList<String>();
	
	public TaskList() {
		if(StartServer.DEBUG) {
			for(int i=10; i < 20; i++) {
				add(i + "");
			}
		} else {
			updateFromDB();
		}
	}

	private void updateFromDB() {
		ArrayList<Auftrag> tasklist = DbAdapter.getOrderList();
		for(Auftrag a: tasklist) {
			String mediatype = "Unknown";
			if(a.getMedientyp() != null) {
				mediatype = a.getMedientyp();
			}
			list.add("TaskID: " + a.getID() + " - MediaType: " + mediatype);
		}
		setChanged();
		notifyObservers();
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
