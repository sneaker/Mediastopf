package ms.server.logic;

import java.util.ArrayList;
import java.util.Observable;

import ms.server.StartServer;
import ms.server.domain.Auftrag;


public class RunningList extends Observable {
	
	private ArrayList<Auftrag> list = new ArrayList<Auftrag>();
	
	public RunningList() {
		if(StartServer.DEBUG) {
			for(int i=10; i < 20; i++) {
				add(new Auftrag("test", "CD", i));
			}
		}
	}
	
	public void add(Auftrag o) {
		list.add(o);
		setChanged();
		notifyObservers();
	}
	
	public void remove(Auftrag o) {
		list.remove(o);
		setChanged();
		notifyObservers();
	}
	
	public void remove(int index) {
		list.remove(index);
		setChanged();
		notifyObservers();
	}
	
	public Auftrag get(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
