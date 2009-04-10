package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;

public class Item implements ActiveRecord {

	
	protected String Name, Importdatum, Speicherort;
	protected int fk_ImportMedium, fk_Container;
	private int id = NOTINDB;
	
	
	
	public Item(String name, String Importdatum, String Speicherort, int fk_ImportMedium, int fk_Container) {
		this.Name = name;
		this.Importdatum = Importdatum;
		this.Speicherort = Speicherort;
		this.fk_ImportMedium = fk_ImportMedium;
		this.fk_Container = fk_Container;
	}

	public Item(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getString("Importdatum"), row.getString("Speicherort"), row.getInt("fk_ImportMedium"), row.getInt("fk_Container"));
		this.id = row.getInt("id");
		
	}

	
	public String getImportdatum() {
		return Importdatum;
	}

	public void setImportdatum(String Importdatum) {
		this.Name = Importdatum;
	}
	
	public String getSpeicherort() {
		return Speicherort;
	}

	public void setSpeicherort(String Speicherort) {
		this.Speicherort = Speicherort;
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	/**
	 * returns false if saving the {@link Item} was not successful.
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Item (Name, Speicherort, Importdatum) values (?, ?, ?)", Name, Speicherort, Importdatum);
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET Name = ?, Speicherort = ?, Importdatum = ? WHERE id = ?", Name, Speicherort, Importdatum, Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Item} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Item WHERE id=?;", Integer.toString(id));
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
		if (obj instanceof Item) {
			Item myItem = (Item) obj;
			
			if(isInDB()){
				return id == myItem.getID();
			}
			else{
				return  Name.equals(myItem.Name) && Importdatum.equals(myItem.Importdatum) && Speicherort.equals(myItem.Speicherort);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Item-ID: " + id + " Name: " + Name + " Importdatum: " + Importdatum + " Speicherort: " + Speicherort;
	}

	public static List<Item> findAll() {
		String sql = "select * from Auftrag;";
		List<Item> lp = ActiveRecordManager.getObjectList(sql, Item.class);
		
		return lp;
	}

	public static Item findByID(int id) {
		String sql = "select * from Auftrag WHERE id = " + id + ";";
		List<Item> res = ActiveRecordManager.getObjectList(sql, Item.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	public void setFk_ImportMedium(int fk_ImportMedium) {
		this.fk_ImportMedium = fk_ImportMedium;
	}

	public int getFk_ImportMedium() {
		return fk_ImportMedium;
	}

	public void setFk_Container(int fk_Container) {
		this.fk_Container = fk_Container;
	}

	public int getFk_Container() {
		return fk_Container;
	}
}
