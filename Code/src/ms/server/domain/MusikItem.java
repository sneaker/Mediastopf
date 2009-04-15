package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import ms.server.database.ActiveRecordManager;

public class MusikItem extends Item {
	
	private int fk_Item, Dauer; //in seconds
	private String Interpret;
	private int id = NOTINDB;

	public boolean isInDB() {
		return id > NOTINDB;
	}
	
	public MusikItem(String name, String Importdatum, String Speicherort, int Dauer, String Interpret, int fk_ImportMedium, int fk_Container) {
		super(name, Importdatum, Speicherort, fk_ImportMedium, fk_Container);
		this.setDauer(Dauer);
		this.setInterpret(Interpret);
	}
	
	public MusikItem(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getString("Importdatum"), row.getString("Speicherort"), row.getInt("Dauer"), row.getString("Interpret"), row.getInt("fk_ImportMedium"), row.getInt("fk_Container"));
		this.id = row.getInt("id");
	}

	public void setDauer(int dauer) {
		Dauer = dauer;
	}

	public int getDauer() {
		return Dauer;
	}

	public void setInterpret(String interpret) {
		Interpret = interpret;
	}

	public String getInterpret() {
		return Interpret;
	}
	
	@Override
	public String toString() {
		return "Musikitem-ID: " + id + " Name: " + Name + " Importdatum: " + Importdatum + " Speicherort: " + Speicherort + " Dauer: " + Dauer + " Interpret: " + Interpret;
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
								"insert into MusikItem (fk_Item, Dauer, Interpret) values (?, ?, ?)", Integer.toString(fk_Item), Integer.toString(Dauer), Interpret);
			else {
				ActiveRecordManager.execute(
						"UPDATE MusikItem SET fk_Item = ?, Dauer = ?, Interpret = ? WHERE id = ?", Integer.toString(fk_Item), Integer.toString(Dauer), Interpret, Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		
		return true;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public boolean delete() {
		try {
			
							ActiveRecordManager.execute("DELETE FROM Item WHERE id=?;", Integer.toString(fk_Item));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		try {
			
							ActiveRecordManager.execute("DELETE FROM MusikItem WHERE id=?;", Integer.toString(id));
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MusikItem) {
			MusikItem myItem = (MusikItem) obj;
			
			if(isInDB()){
				return id == myItem.getID();
			}
			else{
				return  Name.equals(myItem.Name) && Importdatum.equals(myItem.Importdatum) && Speicherort.equals(myItem.Speicherort) && Interpret.equals(myItem.Interpret) && Dauer == myItem.Dauer;
			}
		}
		return false;
	}

}
