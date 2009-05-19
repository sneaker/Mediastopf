package ms.domain;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ms.domain.Auftrag;
import ms.utils.AuftragslistenReceiver;

public class AuftragsListe extends Observable implements Observer {

	private ArrayList<Auftrag> list = new ArrayList<Auftrag>();

	private AuftragslistenReceiver rec;
	
	private static AuftragsListe _instance;

	public static AuftragsListe getInstance(AuftragslistenReceiver rec) {
		if (_instance == null)
			_instance = new AuftragsListe(rec);
		return _instance;
	}
	
	private AuftragsListe(AuftragslistenReceiver rec) {
		this.rec = rec;
		updateList();
	}

	public void add(Auftrag auftrag) {
		list.add(auftrag);
		setChanged();
		notifyObservers();
	}

	public void add(ArrayList<Auftrag> al) {
		list = al;
	}

	public void remove(Auftrag auftrag) {
		list.remove(auftrag);
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

	@SuppressWarnings("unchecked")
	public void updateList() {
		rec.updateTaskList();
		this.list = rec.getTaskList();
		setChanged();
		notifyObservers();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		this.list = (ArrayList<Auftrag>) arg;
		setChanged();
		notifyObservers();
	}
}