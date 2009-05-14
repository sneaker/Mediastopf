package ms.domain.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import ms.domain.ImportMedium;
import ms.server.database.*;
import ms.utils.server.database.ActiveRecord;
import ms.utils.server.database.ActiveRecordManager;

/**
 * Ein {@link ImportMedium}, welches persistent gesichert werden kann.
 * 
 */
public class ServerImportMedium extends ImportMedium implements ActiveRecord {

	private static final long serialVersionUID = 1L;

	public ServerImportMedium(String name, int fk_Mediensammlung,
			int fk_Einlesegeraet) {
		this.setName(name);
		this.fk_Mediensammlung = fk_Mediensammlung;
		this.fk_Einlesegeraet = fk_Einlesegeraet;
	}

	public ServerImportMedium(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getInt("fk_Mediensammlung"), row
				.getInt("fk_Einlesegeraet"));
		this.id = row.getInt("id");
	}

	/**
	 * Legt die Daten des Objektes persistent ab.
	 * 
	 * @return Erfolgsstatus nach dem Sichern des {@link ServerImportMedium}s
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager
						.executeInsert(
								"insert into ImportMedium (name, fk_Mediensammlung, fk_Einlesegeraet) values (?,?,?)",
								Name, Integer.toString(fk_Mediensammlung),
								Integer.toString(fk_Einlesegeraet));
			else {
				ActiveRecordManager
						.execute(
								"UPDATE Mediensammlung SET name = ?, fk_Mediensammlung = ?, fk_Einlesegeraet = ? WHERE id = ?",
								Name, Integer.toString(fk_Mediensammlung),
								Integer.toString(fk_Einlesegeraet), Integer
										.toString(id));
			}

		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}

	/**
	 * returns false if deleting the {@link ServerImportMedium} was not
	 * successful.
	 */
	public boolean delete() {
		if (!isInDB())
			return true;
		try {
			ActiveRecordManager.execute("DELETE FROM ImportMedium WHERE id=?;",
					Integer.toString(id));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}

	public boolean isInDB() {
		return id > NOTINDB;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServerImportMedium) {
			ServerImportMedium myMediensammlung = (ServerImportMedium) obj;

			if (isInDB()) {
				return id == myMediensammlung.getID();
			}

		}
		return false;
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

}
