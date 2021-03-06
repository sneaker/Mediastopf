package ms.ui.models;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import ms.application.client.ClientController;
import ms.domain.Auftrag;
import ms.domain.MediaStopfListe;
import ms.utils.I18NManager;

public class TaskTableModel extends AbstractTableModel implements Observer {

	private static final long serialVersionUID = 1L;
	private static I18NManager manager = I18NManager.getManager();
	private static final String[] columns = { manager.getString("Model.task"),
			manager.getString("Model.status") };

	private MediaStopfListe liste;

	public TaskTableModel(MediaStopfListe list) {
		this.liste = list;
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
		return liste.size();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Auftrag auftrag = liste.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return auftrag.getID();
		case 1:
			String status = auftrag.getStatusMessage();
			String updatetext = "";

			if (ClientController.getClientController() != null) {
				if (ClientController.getClientController().dirPollers != null
						&& ClientController.getClientController().dirPollers.get(auftrag.getID()) != null) {
					updatetext = ", in Bearbeitung ("
							+ ClientController.getClientController().dirPollers.get(auftrag.getID())
									.getRemainingTime() + " Seconds)";
				}
			}
			return status + updatetext;
		default:
			return "";
		}
	}

	public void update(Observable arg0, Object arg1) {
		fireTableDataChanged();
	}
}
