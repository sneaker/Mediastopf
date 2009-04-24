package ms.server.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.common.logic.Task;
import ms.common.utils.I18NManager;
import ms.server.logic.RunningList;


public class TableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static I18NManager manager = I18NManager.getServerManager();
	private static final String[] columns = { manager.getString("Model.task"), manager.getString("Model.status"), "" };
	
	private RunningList list;
	
	public TableModel() {
		this.list = new RunningList();
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
	
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
