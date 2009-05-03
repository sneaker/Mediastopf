package ms.common.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.common.domain.AuftragsListe;
import ms.common.domain.Auftrag;
import ms.common.utils.I18NManager;

public class TaskTableModel extends AbstractTableModel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static I18NManager manager = I18NManager.getManager();
	private static final String[] columns = { manager.getString("Model.task"), manager.getString("Model.status") };
	
	private AuftragsListe list;
	
	public TaskTableModel(AuftragsListe list) {
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
		Auftrag auftrag = list.get(rowIndex);
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
