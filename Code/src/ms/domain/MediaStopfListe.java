package ms.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public abstract class MediaStopfListe extends Observable implements Observer {

	protected ArrayList<Auftrag> list = new ArrayList<Auftrag>();

	public synchronized void add(Auftrag auftrag) {
		list.add(auftrag);
		setChanged();
		notifyObservers();
	}

	public void remove(Auftrag auftrag) {
		remove(auftrag.getID());
	}

	public synchronized void remove(int index) {
		list.remove(index);
		setChanged();
		notifyObservers();
	}

	public Auftrag get(int index) {
		return list.get(index);
	}
	
	public Auftrag getbyAuftragsNr(int nr)
	{
		Iterator<Auftrag> it = list.iterator();
		while(it.hasNext()) {
			Auftrag auftrag = it.next();
			if (auftrag.getID() == nr)
				return auftrag;
		}
		return null;
	}

	public int size() {
		return list.size();
	}

	public void removebyId(int taskID) {
		Auftrag auftrag = getbyAuftragsNr(taskID);
		remove(auftrag);
	}
}
