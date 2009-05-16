package ms.domain;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;

import ms.domain.Auftrag;

public class AuftragsListe extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Auftrag> list = new ArrayList<Auftrag>();
	private Class<?> network;

	public AuftragsListe(Class<?> network) {
		this.network = network;
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
		try {
			System.out.println(network);
			Method method = network.getDeclaredMethod("getTaskList", new Class[] {});
			this.list = (ArrayList<Auftrag>)method.invoke(null, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();
	}
}