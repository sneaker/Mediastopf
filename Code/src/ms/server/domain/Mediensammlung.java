package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class Mediensammlung implements ActiveRecord {

	private String name;
	private int typ, fk_Auftrag;
	private int id = NOTINDB;


	public Mediensammlung(String name, int typ, int fk_Auftrag) {
		this.name = name;
		this.typ = typ;
		this.fk_Auftrag = fk_Auftrag;
		
	}

	public Mediensammlung(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getInt("typ"), row.getInt("fk_Auftrag"));
		this.id = row.getInt("id");
	}

	
	public int getTyp() {
		return typ;
	}

	public void setTyp(int typ) {
		this.typ = typ;
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

	
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Mediensammlung (name,typ,fk_Auftrag) values (?,?,?)",
								name, Integer.toString(typ), Integer.toString(fk_Auftrag));
			else {
				ActiveRecordManager.execute(
						"UPDATE Mediensammlung SET name = ?, typ = ?, fk_Auftrag = ? WHERE id = ?",
						name, Integer.toString(typ), Integer.toString(fk_Auftrag), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Mediensammlung} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Mediensammlung WHERE id=?;", Integer
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
		if (obj instanceof Mediensammlung) {
			Mediensammlung myMediensammlung = (Mediensammlung) obj;
			
			if(isInDB()){
				return id == myMediensammlung.getID();
			}
			else{
				return name.equals(myMediensammlung.name) && typ == myMediensammlung.typ && this.fk_Auftrag == myMediensammlung.fk_Auftrag;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "ID: " + id + " Name: " + name + " Typ: " + typ + " Auftrag: " + fk_Auftrag;
	}

	public static List<Mediensammlung> findAll() {
		String sql = "select * from Mediensammlung;";
		List<Mediensammlung> lp = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
		
		return lp;
	}

	public static Mediensammlung findByID(int id) {
		String sql = "select * from Mediensammlung WHERE id = " + id + ";";
		List<Mediensammlung> res = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	

}
