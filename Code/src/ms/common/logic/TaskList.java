package ms.common.logic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Observable;

public class TaskList extends Observable {

	private ArrayList<Task> list = new ArrayList<Task>();
	private Class<?> network;

	public TaskList(Class<?> network) {
		this.network = network;
		updateList();
	}
	
	public void add(Task task) {
		list.add(task);
		setChanged();
		notifyObservers();
	}

	public void remove(Task task) {
		list.remove(task);
		setChanged();
		notifyObservers();
	}

	public void remove(int index) {
		list.remove(index);
		setChanged();
		notifyObservers();
	}

	public Task get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}
	
	@SuppressWarnings("unchecked")
	public void updateList() {
		try {
			Method method = network.getDeclaredMethod("getTaskList", new Class[] {});
			this.list = (ArrayList<Task>)method.invoke(null, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();
	}
}
