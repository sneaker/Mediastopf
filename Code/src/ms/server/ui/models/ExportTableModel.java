package ms.server.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.server.domain.Auftrag;
import ms.server.logic.ExportRunningList;


public class ExportTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] columns = { "Task", "Status", "" };
	
	private ExportRunningList list;
	
	public ExportTableModel(ExportRunningList list) {
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
		Auftrag task = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return task.getAnzahlMedienSammlung();
		case 1:
			return "";
		default:
			return "";
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}