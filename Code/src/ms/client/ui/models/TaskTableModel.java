package ms.client.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.client.logic.TaskList;
import ms.client.utils.I18NManager;
import ms.common.logic.Task;


public class TaskTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static I18NManager manager = I18NManager.getManager();
	private static final String[] columns = { manager.getString("Model.task"), manager.getString("Model.status"), "" };
	
	private TaskList list;
	
	public TaskTableModel(TaskList list) {
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
		Task task = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return task.getID();
		case 1:
			return task.getStatus();
		default:
			return "";
		}
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Task task = list.get(rowIndex);
		switch(columnIndex) {
		case 1:
			String aValue = (String) value;
			task.setID(Integer.valueOf(aValue));
			break;
		case 2:
			task.setStatus((String) value);
			break;
		default:
			break;
		}
    }
	
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
