package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class ImportMedium implements ActiveRecord {

	
	private String Name;
	private int fk_Mediensammlung, fk_Einlesegeraet;
	private int id = NOTINDB;

	
	public ImportMedium(String Name, int fk_Mediensammlung, int fk_Einlesegeraet) {
		this.setName(Name);
		this.fk_Mediensammlung = fk_Mediensammlung;
		this.fk_Einlesegeraet = fk_Einlesegeraet;
	}

	public ImportMedium(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getInt("fk_Mediensammlung"), row.getInt("fk_Einlesegeraet"));
		this.id = row.getInt("id");
	}

	public int getID() {
		return id;
	}
	
	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	/**
	 * returns false if saving the {@link ImportMedium} was not successful.
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into ImportMedium (name, fk_Mediensammlung, fk_Einlesegeraet) values (?,?,?)",Name, Integer.toString(fk_Mediensammlung), Integer.toString(fk_Einlesegeraet));
			else {
				ActiveRecordManager.execute(
						"UPDATE Mediensammlung SET name = ?, fk_Mediensammlung = ?, fk_Einlesegeraet = ? WHERE id = ?", Name, Integer.toString(fk_Mediensammlung), Integer.toString(fk_Einlesegeraet), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link ImportMedium} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM ImportMedium WHERE id=?;", Integer
					.toString(id));
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
		if (obj instanceof ImportMedium) {
			ImportMedium myMediensammlung = (ImportMedium) obj;
			
			if(isInDB()){
				return id == myMediensammlung.getID();
			}
			
		}
		return false;
	}

	@Override
	public String toString() {
		return "ID: " + id + " Name: " + Name + " fk_Mediensammlung: " + fk_Mediensammlung + " fk_Einlesegeraet: " + fk_Einlesegeraet;
	}

	public static List<ImportMedium> findAll() {
		String sql = "select * from Mediensammlung;";
		List<ImportMedium> lp = ActiveRecordManager.getObjectList(sql, ImportMedium.class);
		
		return lp;
	}

	public static ImportMedium findByID(int id) {
		String sql = "select * from Mediensammlung WHERE id = " + id + ";";
		List<ImportMedium> res = ActiveRecordManager.getObjectList(sql, ImportMedium.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
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
