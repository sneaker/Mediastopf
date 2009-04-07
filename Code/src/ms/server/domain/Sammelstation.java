package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class Sammelstation implements ActiveRecord {

	private String name, Netzwerkadresse;
	private int id = NOTINDB;


	public Sammelstation(String name, String Netzwerkadresse) {
		this.name = name;
		this.Netzwerkadresse = Netzwerkadresse;
	}

	public Sammelstation(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getString("Netzwerkadresse"));
		this.id = row.getInt("id");
	}

	
	public int getID() {
		return id;
	}

	
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Sammelstation (name, Netzwerkadresse) values (?,?)",
								name, Netzwerkadresse);
			else {
				ActiveRecordManager.execute(
						"UPDATE Sammelstation SET name = ?, Netzwerkadresse = ? WHERE id = ?",
						name, Netzwerkadresse, Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Sammelstation} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Sammelstation WHERE id=?;", Integer
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
		if (obj instanceof Sammelstation) {
			Sammelstation mySammelstation = (Sammelstation) obj;
			
			if(isInDB()){
				return id == mySammelstation.getID();
			}
			else{
				return name.equals(mySammelstation.name) && this.Netzwerkadresse == mySammelstation.Netzwerkadresse;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Sammelstation-ID: " + id + " Name: " + name + " Netzwerkadresse: " + Netzwerkadresse;
	}

	public static List<Sammelstation> findAll() {
		String sql = "select * from Sammelstation;";
		List<Sammelstation> lp = ActiveRecordManager.getObjectList(sql, Sammelstation.class);
		
		return lp;
	}

	public static Sammelstation findByID(int id) {
		String sql = "select * from Sammelstation WHERE id = " + id + ";";
		List<Sammelstation> res = ActiveRecordManager.getObjectList(sql, Sammelstation.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	public void setNetzwerkadresse(String netzwerkadresse) {
		Netzwerkadresse = netzwerkadresse;
	}

	public String getNetzwerkadresse() {
		return Netzwerkadresse;
	}

	

}
