package ms.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.domain.Auftrag;
import ms.domain.AuftragsListe;
import ms.domain.Task;
import ms.domain.TaskList;
import ms.domain.Auftrag;
import ms.domain.MSListen;
import ms.utils.I18NManager;

public class TaskTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static I18NManager manager = I18NManager.getManager();
	private static final String[] columns = { manager.getString("Model.task"), manager.getString("Model.status") };
	
	private TaskList list;
//	private MSListen list;
	
	public TaskTableModel(TaskList list) {
//	public TaskTableModel(MSListen list) {
		this.list = list;
		list.addObserver(this);
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public int getRowCount() {
		return list.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Task auftrag = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return auftrag.getID();
		case 1:
			return auftrag.getStatus();
		default:
			return "";
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
