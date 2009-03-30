package ch.nomoresecrets.mediastopf.server.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ch.nomoresecrets.mediastopf.server.domain.Auftrag;
import ch.nomoresecrets.mediastopf.server.logic.ImportRunningList;

public class ImportTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] columns = { "Task", "Status", "" };
	
	private ImportRunningList list;
	
	public ImportTableModel(ImportRunningList list) {
		this.list = list;
		list.addObserver(this);
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
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
	
	@Override
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
