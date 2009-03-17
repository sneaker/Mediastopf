package ch.nomoresecrets.mediastopf.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import ch.nomoresecrets.mediastopf.database.*;


public class Order implements ActiveRecord {

	public String name, medientyp;
	public int anzahl;
	private int id = NOTINDB;
	

	public Order(String name, String medientyp, int anzahl) {
		this.name = name;
		this.medientyp = medientyp;
		this.anzahl = anzahl;
		
	}

	public Order(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getString("medientyp"), row.getInt("anzahl"));
		this.id = row.getInt("id");
	}

	
	
	

	@Override
	public int getID() {
		return id;
	}

	/**
	 * returns false if saving the {@link Order} was not successful.
	 */
	@Override
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Auftrag (name,medientyp,anzahl) values (?,?,?)",
								name, medientyp, Integer.toString(anzahl));
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET name = ?, medientyp = ?, anzahl = ? WHERE id = ?",
						name, medientyp, Integer.toString(anzahl), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Order} was not successful.
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
		if (obj instanceof Order) {
			Order myOrder = (Order) obj;
			
			if(isInDB()){
				return id == myOrder.getID();
			}
			else{
				return name.equals(myOrder.name) && medientyp.equals(myOrder.medientyp) && this.anzahl == myOrder.anzahl;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "ID: " + id + " Name: " + name + " Medientyp: " + medientyp + " Anzahl: " + anzahl;
	}

	public static List<Order> findAll() {
		String sql = "select * from Auftrag;";
		List<Order> lp = ActiveRecordManager.getObjectList(sql, Order.class);
		
		return lp;
	}

	public static Order findByID(int id) {
		String sql = "select * from Auftrag WHERE id = " + id + ";";
		List<Order> res = ActiveRecordManager.getObjectList(sql, Order.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	

}
