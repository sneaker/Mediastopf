package ms.common.domain;

// TODO: Wäre hier eine Methode schreiben() sinvoll, welche alle Daten dieses 
// 		 Mediums auf das ExportMedium schreibt? (MS, 5.5.09)

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

}