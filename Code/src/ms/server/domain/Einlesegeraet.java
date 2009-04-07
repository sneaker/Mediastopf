package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class Einlesegeraet implements ActiveRecord {

	private String name;
	private int fk_Einlesestation;
	private int id = NOTINDB;


	public Einlesegeraet(String name, int fk_Einlesestation) {
		this.name = name;
		this.fk_Einlesestation = fk_Einlesestation;
		
	}

	public Einlesegeraet(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getInt("fk_Einlesestation"));
		this.id = row.getInt("id");
	}

	
	public int getFk_Einlesestation() {
		return fk_Einlesestation;
	}

	public void setFk_Einlesestation(int fk_Einlesestation) {
		this.fk_Einlesestation = fk_Einlesestation;
	}
	

	public int getID() {
		return id;
	}

	
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Einlesegeraet (name,fk_Einlesestation) values (?,?)",
								name, Integer.toString(fk_Einlesestation));
			else {
				ActiveRecordManager.execute(
						"UPDATE Einlesegeraet SET name = ?, fk_Einlesestation = ? WHERE id = ?",
						name, Integer.toString(fk_Einlesestation), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Einlesegeraet} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Einlesegeraet WHERE id=?;", Integer
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
		if (obj instanceof Einlesegeraet) {
			Einlesegeraet myEinlesegeraet = (Einlesegeraet) obj;
			
			if(isInDB()){
				return id == myEinlesegeraet.getID();
			}
			else{
				return name.equals(myEinlesegeraet.name) && this.fk_Einlesestation == myEinlesegeraet.fk_Einlesestation;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Einlesegeraet-ID: " + id + " Name: " + name + " fk_Einlesestation: " + fk_Einlesestation;
	}

	public static List<Einlesegeraet> findAll() {
		String sql = "select * from Einlesegeraet;";
		List<Einlesegeraet> lp = ActiveRecordManager.getObjectList(sql, Einlesegeraet.class);
		
		return lp;
	}

	public static Einlesegeraet findByID(int id) {
		String sql = "select * from Einlesegeraet WHERE id = " + id + ";";
		List<Einlesegeraet> res = ActiveRecordManager.getObjectList(sql, Einlesegeraet.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	

}
