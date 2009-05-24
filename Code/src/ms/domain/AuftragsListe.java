package ms.domain;

import java.util.ArrayList;
import java.util.Observable;

import ms.utils.AuftragslistenReceiver;

public class AuftragsListe extends MSListen {

	private AuftragslistenReceiver rec;
	
	public  AuftragsListe(AuftragslistenReceiver rec) {
		this.rec = rec;
		updateList();
	}

	public void add(ArrayList<Auftrag> al) {
		list = al;
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