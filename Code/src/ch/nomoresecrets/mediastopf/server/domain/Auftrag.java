package ch.nomoresecrets.mediastopf.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import ch.nomoresecrets.mediastopf.server.database.*;


public class Auftrag implements ActiveRecord {

	private String name, medientyp;
	private int anzahlMedienSammlung, anzahlCompletedSammlung;
	private int id = NOTINDB;
	private List<Mediensammlung> mediensammlunglist;
	
	
	

	public Auftrag(String name, String medientyp, int anzahl) {
		this.name = name;
		this.medientyp = medientyp;
		this.anzahlMedienSammlung = anzahl;
		
	}

	public Auftrag(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getString("medientyp"), row.getInt("anzahl"));
		this.id = row.getInt("id");
		String sql = "select * from Mediensammlung where id = " + this.id;
		this.mediensammlunglist = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
	}

	
	
	

	@Override
	public int getID() {
		return id;
	}

	/**
	 * returns false if saving the {@link Auftrag} was not successful.
	 */
	@Override
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Auftrag (name,medientyp,anzahl) values (?,?,?)",
								name, medientyp, Integer.toString(anzahlMedienSammlung));
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET name = ?, medientyp = ?, anzahl = ? WHERE id = ?",
						name, medientyp, Integer.toString(anzahlMedienSammlung), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Auftrag} was not successful.
	 */
	@Override
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Anzahl WHERE id=?;", Integer
					.toString(id));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean isInDB() {
		return id > NOTINDB;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Auftrag) {
			Auftrag myOrder = (Auftrag) obj;
			
			if(isInDB()){
				return id == myOrder.getID();
			}
			else{
				return name.equals(myOrder.name) && medientyp.equals(myOrder.medientyp) && this.anzahlMedienSammlung == myOrder.anzahlMedienSammlung;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String myStr = "Auftrag-ID: " + id + " Name: " + name + " Medientyp: " + medientyp + " Anzahl: " + anzahlMedienSammlung + "\nenthält volgende Mediensammlungen:\n";
		List<Mediensammlung> lp = ActiveRecordManager.getObjectList("select * from Mediensammlung where fk_Auftrag = " + id, Mediensammlung.class);
		for (Mediensammlung Sammlung: lp) myStr = myStr + " " + Sammlung.toString() + "\n";
		return myStr;
	}

	public static List<Auftrag> findAll() {
		String sql = "select * from Auftrag;";
		List<Auftrag> lp = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		
		return lp;
	}

	public static Auftrag findByID(int id) {
		String sql = "select * from Auftrag WHERE id = " + id + ";";
		List<Auftrag> res = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	

}
