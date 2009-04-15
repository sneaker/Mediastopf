package ms.server.domain;


import java.sql.ResultSet;
import java.sql.SQLException;
import ms.server.database.ActiveRecordManager;

public class BildItem extends Item {
	
	private int Aufloesung, Breite, Hoehe, fk_Item;
	private String Aufnahmeort;
	private int id = NOTINDB;

	public boolean isInDB() {
		return id > NOTINDB;
	}
	
	public BildItem(String name, String Importdatum, String Speicherort, int Aufloesung, String Aufnahmeort, int fk_ImportMedium, int fk_Container, int Breite, int Hoehe) {
		super(name, Importdatum, Speicherort, fk_ImportMedium, fk_Container);
		this.setAufloesung(Aufloesung);
		this.setAufnahmeort(Aufnahmeort);
		this.setBreite(Breite);
		this.setHoehe(Hoehe);
		fk_Item = this.getID();
	}
	
	public BildItem(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getString("Importdatum"), row.getString("Speicherort"), row.getInt("Aufloesung"), row.getString("Aufnahmeort"), row.getInt("fk_ImportMedium"), row.getInt("fk_Container"), row.getInt("Breite"), row.getInt("Hoehe"));
		this.id = row.getInt("id");
	}

	public void setAufloesung(int Aufloesung) {
		this.Aufloesung = Aufloesung;
	}

	public int getAufloesung() {
		return Aufloesung;
	}

	public void setAufnahmeort(String Aufnahmeort) {
		this.Aufnahmeort = Aufnahmeort;
	}

	public String getAufnahmeort() {
		return Aufnahmeort;
	}

	public void setBreite(int breite) {
		Breite = breite;
	}

	public int getBreite() {
		return Breite;
	}

	public void setHoehe(int hoehe) {
		Hoehe = hoehe;
	}

	public int getHoehe() {
		return Hoehe;
	}
	
	@Override
	public String toString() {
		return "Bilditem-ID: " + id + " Name: " + Name + " Importdatum: " + Importdatum + " Speicherort: " + Speicherort + " Auflösung: " + Aufloesung + " Hoehe: " + Hoehe + " Breite: " + Breite;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public boolean save() {
		try {
			if (!isInDB())
				fk_Item = ActiveRecordManager.executeInsert(
								"insert into Item (Name, Speicherort, Importdatum, fk_ImportMedium, fk_Container) values (?, ?, ?, ?, ?)", Name, Speicherort, Importdatum, Integer.toString(fk_ImportMedium), Integer.toString(fk_Container));
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET Name = ?, Speicherort = ?, Importdatum = ?, fk_ImportMedium = ?, fk_Container = ? WHERE id = ?", Name, Speicherort, Importdatum, Integer.toString(fk_ImportMedium), Integer.toString(fk_Container), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		
		try {
			if (!isInDB() && fk_Item != 0)
				id = ActiveRecordManager.executeInsert(
								"insert into BildItem (fk_Item, Aufloesung, Breite, Hoehe, Aufnahmeort) values (?, ?, ?, ?, ?)", Integer.toString(fk_Item), Integer.toString(Aufloesung), Integer.toString(Breite), Integer.toString(Hoehe), Aufnahmeort);
			else {
				ActiveRecordManager.execute(
						"UPDATE BiltItem SET fk_Item = ?, Aufloesung = ?, Breite = ?, Hoehe = ?, Aufnahmeort = ? WHERE id = ?", Integer.toString(fk_Item), Integer.toString(Aufloesung), Integer.toString(Breite), Integer.toString(Hoehe), Aufnahmeort, Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Item WHERE id=?;", Integer.toString(fk_Item));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM BildItem WHERE id=?;", Integer.toString(id));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BildItem) {
			BildItem myItem = (BildItem) obj;
			
			if(isInDB()){
				return id == myItem.getID();
			}
			else{
				return  Name.equals(myItem.Name) && Importdatum.equals(myItem.Importdatum) && Speicherort.equals(myItem.Speicherort) && Aufnahmeort.equals(myItem.Aufnahmeort) && Aufloesung == myItem.Aufloesung && Breite == myItem.Breite && Hoehe == myItem.Hoehe;
			}
		}
		return false;
	}


}
