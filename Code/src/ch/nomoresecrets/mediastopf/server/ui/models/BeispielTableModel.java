package ch.nomoresecrets.mediastopf.server.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ch.nomoresecrets.mediastopf.client.logic.TaskRunningList;

public class BeispielTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String[] columns = { "ID", "Produkt", "Preis (CHF)", "Anzahl" };
	
	private TaskRunningList liste;
	
	public BeispielTableModel(TaskRunningList liste) {
		this.liste = liste;
		liste.addObserver(this);
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
		return liste.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = liste.get(rowIndex);
		switch (columnIndex) {
		// TODO
		case 0:
			return null;
		case 1:
			return null;
		case 2:
			return null;
		case 3:
			return null;
		default:
			return null;
		}
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Object o = liste.get(rowIndex);
		switch(columnIndex) {
		case 1:
			// TODO
			break;
		case 2:
			// TODO
			break;
		case 3:
			// TODO
			break;
		default:
			// TODO
			break;
		}
    }
	
	@Override
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
