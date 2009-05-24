package ms.domain;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class MSListen extends Observable implements Observer {

	protected ArrayList<Auftrag> list = new ArrayList<Auftrag>();

	public void add(Auftrag auftrag) {
		list.add(auftrag);
		setChanged();
		notifyObservers();
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
	
	public Auftrag getbyAuftragsNr(int nr)
	{
		for(Auftrag a : list){
			if (a.id == nr)
				return a;
		}
		return null;
	}
	
	public boolean removebyId(int id)
	{
		for(Auftrag a: list) {
			if (a.id == id) {
				remove(a);
				return true;
			}
		}
		return false;
	}

	public int size() {
		return list.size();
	}
}
