package ms.domain;

import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Repraesentiert ein digitalisiertes Medium eines Kunden, welches nach dem
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
	 * Vorbereitung, damit die einzelnen extrahierten Dateien hinzugefuegt werden
	 * koennen.
	 */
	public ImportMedium() {
		super();
		items = new ArrayList<File>();
	}
	
	public ImportMedium(ResultSet row) throws SQLException {
		this.Name = row.getString("Name");
		this.id = row.getInt("id");
	}


	public boolean isInDB() {
		return id > -1;
	}

	public void setFk_Mediensammlung(int fk_Mediensammlung) {
		this.fk_Mediensammlung = fk_Mediensammlung;
	}

	public int getFk_Mediensammlung() {
		return fk_Mediensammlung;
	}

	public void setFk_Einlesegeraet(int fk_Einlesegeraet) {
		this.fk_Einlesegeraet = fk_Einlesegeraet;
	}

	public int getFk_Einlesegeraet() {
		return fk_Einlesegeraet;
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
	 * FÃ¼ge einen neu digitalisierten Track, ein Bild oder eben ein neues
	 * TeilstÃ¼ck des Mediums hinzu.
	 * 
	 * @param item
	 *            Das neu eingelesene und digitalisierte TeilstÃ¼ck des Mediums,
	 *            welches dem Import hinzugefÃ¼gt werden soll.
	 */
	public void addItem(File item) {
		items.add(item);
	}

	/**
	 * Liefert alle bis zum aktuellen Zeitpunkt importierten Dateien zurÃ¼ck.
	 * 
	 * @return Liste mit allen bisher importierten Dateien dieses Mediums
	 */
	public ArrayList<File> getItemsbyFile() {
		return items;
	}
}