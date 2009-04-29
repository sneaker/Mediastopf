package ms.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import ms.common.domain.MedienSammlung;
import ms.server.database.*;


public class ServerMedienSammlung extends MedienSammlung implements ActiveRecord {
	protected int fk_Auftrag;
	
	public int getFk_Auftrag() {
		return fk_Auftrag;
	}

	public void setFk_Auftrag(int fk_Auftrag) {
		this.fk_Auftrag = fk_Auftrag;
	}

	public ServerMedienSammlung(int newtyp, int newstatus, String newname) {
		super(newtyp, newstatus, newname); 
	}

	public ServerMedienSammlung(ResultSet row) throws SQLException {
		this(row.getInt("fk_Typ"), row.getInt("fk_Status"), row.getString("name"));
		this.id = row.getInt("id");
	}

	
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Mediensammlung (name,fk_Typ, fk_Status, fk_Auftrag) values (?,?,?,?)",
								name, Integer.toString(typ), Integer.toString(status), Integer.toString(fk_Auftrag));
			else {
				ActiveRecordManager.execute(
						"UPDATE Mediensammlung SET name = ?, fk_Typ = ?, fk_Status = ?, fk_Auftrag = ? WHERE id = ?",
						name, Integer.toString(typ), Integer.toString(status), Integer.toString(fk_Auftrag), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		return true;
	}
	
	/**
	 * returns false if deleting the {@link ServerMedienSammlung} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Mediensammlung WHERE id=?;", Integer
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
		if (obj instanceof ServerMedienSammlung) {
			ServerMedienSammlung myMediensammlung = (ServerMedienSammlung) obj;
			
			if(isInDB()){
				return id == myMediensammlung.getID();
			}
			else{
				return name.equals(myMediensammlung.name) && typ == myMediensammlung.typ;
			}
		}
		return false;
	}




	

}
