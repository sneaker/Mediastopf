package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ms.server.database.*;




public class ExportMedium implements ActiveRecord {

	private String name;
	private int Speicherkapazitaet, fk_Auftrag, fk_Container;
	private int id = NOTINDB;


	public ExportMedium(String name, int Speicherkapazitaet, int fk_Auftrag, int fk_Container) {
		this.name = name;
		this.Speicherkapazitaet = Speicherkapazitaet;
		this.fk_Auftrag = fk_Auftrag;
		this.setFk_Container(fk_Container);
		
	}

	public ExportMedium(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getInt("Speicherkapazitaet"), row.getInt("fk_Auftrag"), row.getInt("fk_Container"));
		this.id = row.getInt("id");
	}

	
	public int getSpeicherkapazitaet() {
		return Speicherkapazitaet;
	}

	public void setSpeicherkapazitaet(int Speicherkapazitaet) {
		this.Speicherkapazitaet = Speicherkapazitaet;
	}
	
	public int getFk_Auftrag() {
		return fk_Auftrag;
	}

	public void setFk_Auftrag(int fk_Auftrag) {
		this.fk_Auftrag = fk_Auftrag;
	}
	

	public int getID() {
		return id;
	}

	
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into ExportMedium (name,speicherkapazitaet,fk_Auftrag,fk_Container) values (?,?,?,?)",
								name, Integer.toString(Speicherkapazitaet), Integer.toString(fk_Auftrag), Integer.toString(fk_Container));
			else {
				ActiveRecordManager.execute(
						"UPDATE ExportMedium SET name = ?, Speicherkapazitaet = ?, fk_Auftrag = ?, fk_Container = ? WHERE id = ?",
						name, Integer.toString(Speicherkapazitaet), Integer.toString(fk_Auftrag), Integer.toString(fk_Container), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link ExportMedium} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM ExportMedium WHERE id=?;", Integer
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
		if (obj instanceof ExportMedium) {
			ExportMedium myExportMedium = (ExportMedium) obj;
			
			if(isInDB()){
				return id == myExportMedium.getID();
			}
			else{
				return name.equals(myExportMedium.name) && Speicherkapazitaet == myExportMedium.Speicherkapazitaet && this.fk_Auftrag == myExportMedium.fk_Auftrag && this.fk_Container == myExportMedium.fk_Container;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "ExportMedium-ID: " + id + " Name: " + name + " Speicherkapazitaet: " + Speicherkapazitaet + " Auftrag: " + fk_Auftrag + " Container: " + fk_Container;
	}

	public static List<ExportMedium> findAll() {
		String sql = "select * from ExportMedium;";
		List<ExportMedium> lp = ActiveRecordManager.getObjectList(sql, ExportMedium.class);
		
		return lp;
	}

	public static ExportMedium findByID(int id) {
		String sql = "select * from ExportMedium WHERE id = " + id + ";";
		List<ExportMedium> res = ActiveRecordManager.getObjectList(sql, ExportMedium.class);
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
