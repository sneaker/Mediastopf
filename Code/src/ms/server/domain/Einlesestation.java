package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class Einlesestation implements ActiveRecord {

	private String name, Netzwerkadresse;
	private int fk_Sammelstation;
	private int id = NOTINDB;


	public Einlesestation(String name, int fk_Sammelstation, String Netzwerkadresse) {
		this.name = name;
		this.Netzwerkadresse = Netzwerkadresse;
		this.fk_Sammelstation = fk_Sammelstation;
	}

	public Einlesestation(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getInt("fk_Sammelstation"), row.getString("Netzwerkadresse"));
		this.id = row.getInt("id");
	}

	
	public int getFk_Sammelstation() {
		return fk_Sammelstation;
	}

	public void setFk_Sammelstation(int fk_Sammelstation) {
		this.fk_Sammelstation = fk_Sammelstation;
	}
	

	public int getID() {
		return id;
	}

	
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Einlesestation (name,fk_Sammelstation, Netzwerkadresse) values (?,?,?)",
								name, Integer.toString(fk_Sammelstation), Netzwerkadresse);
			else {
				ActiveRecordManager.execute(
						"UPDATE Einlesestation SET name = ?, fk_Sammelstation = ?, Netzwerkadresse = ? WHERE id = ?",
						name, Integer.toString(fk_Sammelstation), Netzwerkadresse, Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Einlesestation} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Einlesestation WHERE id=?;", Integer
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
		if (obj instanceof Einlesestation) {
			Einlesestation myEinlesestation = (Einlesestation) obj;
			
			if(isInDB()){
				return id == myEinlesestation.getID();
			}
			else{
				return name.equals(myEinlesestation.name) && this.fk_Sammelstation == myEinlesestation.fk_Sammelstation && this.Netzwerkadresse == myEinlesestation.Netzwerkadresse;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Einlesestation-ID: " + id + " Name: " + name + " fk_Sammelstation: " + fk_Sammelstation + " Netzwerkadresse: " + Netzwerkadresse;
	}

	public static List<Einlesestation> findAll() {
		String sql = "select * from Einlesestation;";
		List<Einlesestation> lp = ActiveRecordManager.getObjectList(sql, Einlesestation.class);
		
		return lp;
	}

	public static Einlesestation findByID(int id) {
		String sql = "select * from Einlesestation WHERE id = " + id + ";";
		List<Einlesestation> res = ActiveRecordManager.getObjectList(sql, Einlesestation.class);
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
