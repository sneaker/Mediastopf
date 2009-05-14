package ms.domain.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import ms.domain.Auftrag;
import ms.domain.ExportMedium;
import ms.domain.ImportMedium;
import ms.utils.server.database.*;


public class ServerAuftrag extends Auftrag implements ActiveRecord {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerAuftrag(int status) {
		super(status);
	}

	public ServerAuftrag(ResultSet row) throws SQLException {
		this(row.getInt("fk_status"));
		this.id = row.getInt("id");
	}

	
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * returns false if saving the {@link ServerAuftrag} was not successful.
	 */
	public boolean save() {
		try {
			if (!isInDB())
				id = ActiveRecordManager.executeInsert(
								"insert into Auftrag (fk_status) values (?)", Integer.toString(status));
			else {
				ActiveRecordManager.execute(
						"UPDATE Auftrag SET fk_status = ? WHERE id = ?", Integer.toString(status), Integer.toString(id));
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
		
		for (ImportMedium myImportMedium: ListImportMedium) 
		{
			try{ ((ServerImportMedium) myImportMedium).save();
			
			} catch (Exception e) {
				System.err.println(e);
				System.err.println("Tried to save a ImportMedium instead of a ServerImportMedium");
				return false;
			}
			
		}
		
		for (ExportMedium myExMedium: ListExportMedium) 
		{
			try{ ((ServerExportMedium) myExMedium).save();
			
			} catch (Exception e) {
				System.err.println(e);
				System.err.println("Tried to save an ExportMedium instead of a ServerExportMedium");
				return false;
			}
			
		}
		
		return true;
	}
	
	/**
	 * returns false if deleting the {@link ServerAuftrag} was not successful.
	 */
	public boolean delete() {
		try {
			if (isInDB())
							ActiveRecordManager.execute("DELETE FROM Auftrag WHERE id=?;", Integer
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
		if (obj instanceof ServerAuftrag) {
			ServerAuftrag myOrder = (ServerAuftrag) obj;
			
			if(isInDB()){
				return id == myOrder.getID();
			}
			else{
				return  status == myOrder.getStatus();
			}
		}
		return false;
	}
	
	



}
