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

	public synchronized void remove(Auftrag auftrag) {
		list.remove(auftrag);
		setChanged();
		notifyObservers();
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
			if (auftrag.id == nr)
				return auftrag;
		}
		return null;
	}

	public int size() {
		return list.size();
	}
}
