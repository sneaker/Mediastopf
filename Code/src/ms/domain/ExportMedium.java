package ms.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

// TODO: Wäre hier eine Methode schreiben() sinvoll, welche alle Daten dieses 
// 		 Mediums auf das ExportMedium schreibt? (MS, 5.5.09)
//	Hier wäre eine Methode alles löschen und richtig implementieren richtig!!!!!! TK, 13.2.10

/**
 * Repräsentiert einen Datenträger, auf welchem die gesammelten Kundenmedien für
 * die Auslieferung abgelegt werden. Je nach Grösse dieses Datenträgers muss der
 * verarbeitete Auftrag auf mehrere dieser Medien abgelegt werden.
 */
public class ExportMedium {

	protected String name;
	protected int Speicherkapazitaet;
	protected int fk_Auftrag;
	protected int fk_Container;
	protected int id = -1;
	public boolean abgeschlossen = false;

	public int getSpeicherkapazitaet() {
		return Speicherkapazitaet;
	}

	public void setSpeicherkapazitaet(int Speicherkapazitaet) {
		this.Speicherkapazitaet = Speicherkapazitaet;
	}

	public int getFk_Auftrag() {
		return fk_Auftrag;
	}

	public void setFk_Auftrag(int fk_Auftrag) {
		this.fk_Auftrag = fk_Auftrag;
	}

	public int getID() {
		return id;
	}
	
	public ExportMedium(String name, int Speicherkapazitaet, int fk_Auftrag, int fk_Container) {
		this.name = name;
		this.Speicherkapazitaet = Speicherkapazitaet;
		this.fk_Auftrag = fk_Auftrag;	
	}

	public ExportMedium(ResultSet row) throws SQLException {
		this.id = row.getInt("id");
		this.name = row.getString("name");
		this.Speicherkapazitaet = row.getInt("Speicherkapazitaet");
	}
	
	public boolean isInDB() {
		return id > -1;
	}
}