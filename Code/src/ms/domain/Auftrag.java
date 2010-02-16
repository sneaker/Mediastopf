package ms.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Repraesentiert einen Kunden-Auftrag, welcher aus ImportMedien besteht.
 */
public class Auftrag implements Serializable {

	private static final long serialVersionUID = -3877325340298178575L;

	private int id = -1;
	
	private ArrayList<ImportMedium> importmedia;
	private ArrayList<ExportMedium> exportmedia;

	public Auftrag(int id) {
		this.id = id;
		importmedia = new ArrayList<ImportMedium>();
		exportmedia = new ArrayList<ExportMedium>();
	}

	public Auftrag(ResultSet row) throws SQLException {
		this(row.getInt("status"));
		this.id = row.getInt("id");
	}
	
	public Auftrag(int id, int status) {
		this.id = id;
		this.status = status;
	}

	private int status = -1; 
	
	public static enum _state { NEU, IMPORTBEREIT, SENDEBEREIT, EXPORTBEREIT, ABGESCHLOSSEN, UNBEKANNT };
	
	private static String[] statusmsg = { 
		"Neu", 
		"Bereit für Import", 
		"Medium wird importiert",
		"Medium sendebereit", 
		"Auftrag Exportbereit", 
		"Auftrag Abgeschlossen", 
		"Unbekannt" 
	};
	
	public String getStatusMessage() {
		if (status < 0 || status > statusmsg.length)
			return statusmsg[5];
		
		return statusmsg[status];
	}
	
	public void setStatus(int status) {
		this.status = status;
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
		return importmedia.add(newSammlung);
	}

	public ImportMedium getImportMedium(int Index) {
		return importmedia.get(Index);
	}

	public ImportMedium removeImportMedium(int Index) {
		return importmedia.remove(Index);
	}

	public boolean addExportMedium(ExportMedium newMedium) {
		return exportmedia.add(newMedium);
	}

	public ExportMedium getExportMedium(int Index) {
		return exportmedia.get(Index);
	}

	public ExportMedium removeExportMedium(int Index) {
		return exportmedia.remove(Index);
	}

	public int getStatus() {
		return status;
	}
	

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
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
}
