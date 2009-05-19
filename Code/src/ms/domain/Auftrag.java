package ms.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Repräsentiert einen Kunden-Auftrag, welcher aus einzelnen ImportMedien
 * besteht.
 * 
 */
public class Auftrag implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int status = -1;
	protected int id = -1;
	protected ArrayList<ImportMedium> ListImportMedium;
	protected ArrayList<ExportMedium> ListExportMedium;

	public Auftrag(int newstatus) {
		status = newstatus;
		ListImportMedium = new ArrayList<ImportMedium>();
		ListExportMedium = new ArrayList<ExportMedium>();
	}

	public Auftrag(ResultSet row) throws SQLException {
		this(row.getInt("status"));
		this.id = row.getInt("id");
	}

	/**
	 * Neues Importmedium hinzufügen, welches für diesen Auftrag verarbeitet
	 * werden soll.
	 * 
	 * @param newSammlung
	 *            das neue Medium 
	 * @return true, falls die Datenbank das neue Medium aufnehmen konnte.
	 */
	public boolean addImportMedium(ImportMedium newSammlung) {
		return ListImportMedium.add(newSammlung);
	}

	public ImportMedium getImportMedium(int Index) {
		return ListImportMedium.get(Index);
	}

	public ImportMedium removeImportMedium(int Index) {
		return ListImportMedium.remove(Index);
	}

	public boolean addExportMedium(ExportMedium newMedium) {
		return ListExportMedium.add(newMedium);
	}

	public ExportMedium getExportMedium(int Index) {
		return ListExportMedium.get(Index);
	}

	public ExportMedium removeExportMedium(int Index) {
		return ListExportMedium.remove(Index);
	}


	public Auftrag(int newstatus, ArrayList<ImportMedium> newIMList,
			ArrayList<ExportMedium> newEMList) {
		status = newstatus;
		ListImportMedium = newIMList;
		ListExportMedium = newEMList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getID() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Auftrag) {
			Auftrag task = (Auftrag) obj;
			return ((task.getID() == this.id));
		}
		return false;
	}

	public void setID(int id) {
		this.id = id;
	}

}
