package ms.domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Repräsentiert ein digitalisiertes Medium eines Kunden, welches nach dem
 * Import aus einzelnen Dateien besteht.
 */
public class ImportMedium implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String Name;
	protected int fk_Mediensammlung;
	protected int fk_Einlesegeraet;
	protected int id = -1;
	protected ArrayList<File> items;

	/**
	 * Vorbereitung, damit die einzelnen extrahierten Dateien hinzugefügt werden
	 * können.
	 */
	public ImportMedium() {
		super();
		items = new ArrayList<File>();
	}

	/**
	 * @return ID, mit welcher das reale Import-Medium gekennzeichnet ist, damit
	 *         es eindeutig einem Auftrag zugeordnet werden kann.
	 */
	public int getID() {
		return id;
	}

	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return Nicht zwingend eindeutiger Name des importierten Mediums.
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Füge einen neu digitalisierten Track, ein Bild oder eben ein neues
	 * Teilstück des Mediums hinzu.
	 * 
	 * @param item
	 *            Das neu eingelesene und digitalisierte Teilstück des Mediums,
	 *            welches dem Import hinzugefügt werden soll.
	 */
	public void addItem(File item) {
		items.add(item);
	}

	/**
	 * Liefert alle bis zum aktuellen Zeitpunkt importierten Dateien zurück.
	 * 
	 * @return Liste mit allen bisher importierten Dateien dieses Mediums
	 */
	public ArrayList<File> getItemsbyFile() {
		return items;
	}
}