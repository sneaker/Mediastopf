package ch.nomoresecrets.mediastopf.client.logic;

import java.util.ArrayList;
import java.util.Observable;

public class AuftragsListe extends Observable {
	
	private ArrayList<Auftrag> liste = new ArrayList<Auftrag>();
	
	public void add(Auftrag auftrag) {
		liste.add(auftrag);
		setChanged();
		notifyObservers();
	}
	
	public void remove(Auftrag s) {
		liste.remove(s);
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
