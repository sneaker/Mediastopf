package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;

public class Auftrag implements ActiveRecord {

	
	private int status;
	private int id = NOTINDB;
	private List<Mediensammlung> mediensammlunglist;
	
	
	public Auftrag(int status) {
		this.status = status;
	}

	public Auftrag(ResultSet row) throws SQLException {
		this(row.getInt("status"));
		this.id = row.getInt("id");
		String sql = "select * from Mediensammlung where id = " + this.id;
		this.mediensammlunglist = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	/**
	 * returns false if saving the {@link Auftrag} was not successful.
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Auftrag (status) values (?)", Integer.toString(status));
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET status = ? WHERE id = ?", Integer.toString(status), Integer.toString(id));
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
				return  status == myOrder.getStatus();
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String myStr = "Auftrag-ID: " + id + " Status: " + status + "\n";
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
