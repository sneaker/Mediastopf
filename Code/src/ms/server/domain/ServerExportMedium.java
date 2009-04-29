package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;



import ms.common.domain.ExportMedium;
import ms.server.database.*;




public class ServerExportMedium extends ExportMedium implements ActiveRecord {

	public ServerExportMedium(String name, int Speicherkapazitaet, int fk_Auftrag, int fk_Container) {
		this.name = name;
		this.Speicherkapazitaet = Speicherkapazitaet;
		this.fk_Auftrag = fk_Auftrag;
		this.setFk_Container(fk_Container);
		
	}

	public ServerExportMedium(ResultSet row) throws SQLException {
		this(row.getString("name"), row.getInt("Speicherkapazitaet"), row.getInt("fk_Auftrag"), row.getInt("fk_Container"));
		this.id = row.getInt("id");
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
	 * returns false if deleting the {@link ServerExportMedium} was not successful.
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
		if (obj instanceof ServerExportMedium) {
			ServerExportMedium myExportMedium = (ServerExportMedium) obj;
			
			if(isInDB()){
				return id == myExportMedium.getID();
			}
			else{
				return name.equals(myExportMedium.name) && Speicherkapazitaet == myExportMedium.Speicherkapazitaet && this.fk_Auftrag == myExportMedium.fk_Auftrag && this.fk_Container == myExportMedium.fk_Container;
			}
		}
		return false;
	}



	public void setFk_Container(int fk_Container) {
		this.fk_Container = fk_Container;
	}

	public int getFk_Container() {
		return fk_Container;
	}

	

}
