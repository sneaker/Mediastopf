package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class ImportMedium implements ActiveRecord {

	

	private int id = NOTINDB;
	private List<Item> itemlist;
	
	
	

	public ImportMedium() {
		
		
	}

	public ImportMedium(ResultSet row) throws SQLException {
		this.id = row.getInt("id");
	}

	
	
	

	public int getID() {
		return id;
	}

	/**
	 * returns false if saving the {@link ImportMedium} was not successful.
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into ImportMedium () values ()","");
			else {
				ActiveRecordManager.execute(
						"UPDATE Mediensammlung SET  WHERE id = ?", Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link ImportMedium} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM ImportMedium WHERE id=?;", Integer
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
		if (obj instanceof ImportMedium) {
			ImportMedium myMediensammlung = (ImportMedium) obj;
			
			if(isInDB()){
				return id == myMediensammlung.getID();
			}
			
		}
		return false;
	}

	@Override
	public String toString() {
		return "ID: " + id;
	}

	public static List<ImportMedium> findAll() {
		String sql = "select * from Mediensammlung;";
		List<ImportMedium> lp = ActiveRecordManager.getObjectList(sql, ImportMedium.class);
		
		return lp;
	}

	public static ImportMedium findByID(int id) {
		String sql = "select * from Mediensammlung WHERE id = " + id + ";";
		List<ImportMedium> res = ActiveRecordManager.getObjectList(sql, ImportMedium.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	

}
