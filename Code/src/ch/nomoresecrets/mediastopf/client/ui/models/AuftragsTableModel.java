package ch.nomoresecrets.mediastopf.client.ui.models;

import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import ch.nomoresecrets.mediastopf.client.logic.AuftragsListe;

public class AuftragsTableModel extends AbstractTableModel implements Observer {

//		public int getColumnCount() {return 10;}
//		public int getRowCount() {return 10;}
//		public Object getValueAt(int row, int col) {return new Integer(row * col);}
	
	private static final long serialVersionUID = 3650597747261304480L;

	private static final String[] columns = { "ID", "Kunde", "Anzahl Medien", "Priorität" };
	
	private AuftragsListe liste;
	
	public AuftragsTableModel(AuftragsListe liste) {
		this.liste = liste;
		liste.addObserver(this);
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public int getRowCount() {
		return liste.size();
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		//FIXME Martin until So, 29.03.09

		// Zugriff auf DB / ActiveRecord 
		return "blah";
//		Object o = liste.get(rowIndex);
//		switch (columnIndex) {
//		// TODO
//		case 0:
//			return null;
//		case 1:
//			return null;
//		case 2:
//			return null;
//		case 3:
//			return null;
//		default:
//			return null;
//		}
	}
	
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
