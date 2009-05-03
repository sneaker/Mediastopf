package ms.common.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import ms.common.domain.AuftragsListe;


public class TaskComboBoxModel extends AbstractListModel implements Observer, ComboBoxModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AuftragsListe list;
	private int taskID = -1;
	
	public TaskComboBoxModel(AuftragsListe list) {
		this.list = list;
		list.addObserver(this);
	}

	public Object getSelectedItem() {
		return taskID;
	}

	public void setSelectedItem(Object anItem) {
		taskID = (Integer)anItem;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
	}

	public Object getElementAt(int index) {
		return list.get(index).getID();
	}

	public int getSize() {
		return list.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
	}

	public void update(Observable o, Object arg) {
		fireContentsChanged(this, 0, list.size());
	}
}
