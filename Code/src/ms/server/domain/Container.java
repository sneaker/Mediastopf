package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;

public class Container implements ActiveRecord {

	
	private String Name;
	private int fk_Container;
	private int id = NOTINDB;
	
	
	
	public Container(String name, int fk_Container) {
		this.Name = name;
		this.fk_Container = fk_Container;
	}

	public Container(ResultSet row) throws SQLException {
		this(row.getString("Name"), row.getInt("fk_Container"));
		this.id = row.getInt("id");
		
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
	 * returns false if saving the {@link Container} was not successful.
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Container (Name, fk_Container) values (?, ?)", Name, Integer.toString(fk_Container));
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET Name = ?, fk_Container = ? WHERE id = ?", Name, Integer.toString(fk_Container), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link Container} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Container WHERE id=?;", Integer.toString(id));
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
		if (obj instanceof Container) {
			Container myItem = (Container) obj;
			
			if(isInDB()){
				return id == myItem.getID();
			}
			else{
				return  Name.equals(myItem.Name) && fk_Container == myItem.fk_Container;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Container-ID: " + id + " Name: " + Name + " fk_Container: " + fk_Container;
	}

	public static List<Container> findAll() {
		String sql = "select * from Container;";
		List<Container> lp = ActiveRecordManager.getObjectList(sql, Container.class);
		
		return lp;
	}

	public static Container findByID(int id) {
		String sql = "select * from Container WHERE id = " + id + ";";
		List<Container> res = ActiveRecordManager.getObjectList(sql, Container.class);
		if (res.isEmpty())
			return null;
		else {
			
			return res.get(0);
		}
	}

	public void setFk_Container(int fk_Container) {
		this.fk_Container = fk_Container;
	}

	public int getFk_Container() {
		return fk_Container;
	}
}
