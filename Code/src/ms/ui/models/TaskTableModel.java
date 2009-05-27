package ms.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.application.client.ClientController;
import ms.domain.Auftrag;
import ms.domain.AuftragsListe;
import ms.utils.I18NManager;

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
			return auftrag.getStatusText();
// TODO
//			String text = "";
//			if (ClientController.dirPollers != null && ClientController.dirPollers.get(auftrag.getID()) != null) {
//				text = ", in Bearbeitung (" + ClientController.dirPollers.get(auftrag.getID()).getRemainingTime() + " Seconds)";
//			}
//			String status = "unknown";
//			System.out.println(" auftragsid bei switch: " + auftrag.getStatus());
//			switch (auftrag.getStatus()) {
//				case -1:
//				case 0:
//					status = "Neu";
//					break;
//				case 1:
//					status = "Bereit fuer Import";
//					break;
//				case 2:
//					status = "Auftrag importiert, sendebereit";
//					break;
//				case 3:
//					status = "Auftrag abgeschlossen";
//					break;
//				case 4:
//					status = "Auftrag Exportbereit";
//					break;
//				default:
//					status = "unknown " + auftrag.getStatus();
//					break;
//			}
//			return status + text;
		default:
			return "";
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
