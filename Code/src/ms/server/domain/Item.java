package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;

public class Item implements ActiveRecord {

	
	private String Name, Importdatum, Speicherort;
	private int id = NOTINDB;
	private List<Mediensammlung> mediensammlunglist;
	
	
	public Item(String name, String Importdatum, String Speicherort) {
		this.Name = name;
		this.Importdatum = Importdatum;
		this.Speicherort = Speicherort;
	}

	public Item(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getString("Importdatum"), row.getString("Speicherort"));
		this.id = row.getInt("id");
		String sql = "select * from Mediensammlung where id = " + this.id;
		this.mediensammlunglist = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
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
		String myStr = "Item-ID: " + id + " Name: " + Name + " Importdatum: " + Importdatum + " Speicherort: " + Speicherort + "\n";
		List<Mediensammlung> lp = ActiveRecordManager.getObjectList("select * from Mediensammlung where fk_Auftrag = " + id, Mediensammlung.class);
		for (Mediensammlung Sammlung: lp) myStr = myStr + " " + Sammlung.toString() + "\n";
		return myStr;
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
}
