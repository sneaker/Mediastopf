package ms.client.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.client.logic.Task;
import ms.client.logic.TaskRunningList;


public class TaskTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] columns = { "Task", "Status", "" };
	
	private TaskRunningList list;
	
	public TaskTableModel(TaskRunningList list) {
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
			return task.getTasknum();
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
			task.setTasknum(Integer.valueOf(aValue));
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
