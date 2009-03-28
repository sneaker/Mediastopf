package ch.nomoresecrets.mediastopf.server.logic;

import java.util.ArrayList;
import java.util.Observable;

public class BeispielListe extends Observable {
	
	private ArrayList<Object> liste = new ArrayList<Object>();
	
	public void add(Object o) {
		liste.add(o);
		setChanged();
		notifyObservers();
	}
	
	public void remove(Object o) {
		liste.remove(o);
		setChanged();
		notifyObservers();
	}
	
	public void remove(int index) {
		liste.remove(index);
		setChanged();
		notifyObservers();
	}
	
	public Object get(int position) {
		return liste.get(position);
	}
	
	public int size() {
		return liste.size();
	}
}
