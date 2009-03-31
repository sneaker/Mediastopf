package ms.server.logic;

import java.util.ArrayList;
import java.util.Observable;

import ms.server.Server;
import ms.server.StartServer;
import ms.server.domain.Auftrag;


public class TaskList extends Observable {
	
	private ArrayList<String> list = new ArrayList<String>();
	private Server server;
	
	public TaskList(Server server) {
		this.server = server;
		
		if(StartServer.DEBUG) {
			for(int i=10; i < 20; i++) {
				add(i + "");
			}
		} else {
			updateList();
		}
	}

	private void updateList() {
		ArrayList<Auftrag> tasklist = server.getDataBase();
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
