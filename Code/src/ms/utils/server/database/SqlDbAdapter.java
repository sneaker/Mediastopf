package ms.utils.server.database;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;

public class SqlDbAdapter implements DbAdapter {

	private SqlDbConnection sqldbconnection;
	
	public SqlDbAdapter() {
		sqldbconnection = new SqlDbConnection();
	}

	@Override
	public ArrayList<Auftrag> getAuftragsListe() {
		String sql = "select * from Auftrag";
		ArrayList<Auftrag> myList = sqldbconnection
				.getObjectList(sql, Auftrag.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	@Override
	public Auftrag getAuftrag(int AuftragId) {
		String sql = "select * from Auftrag WHERE id = " + AuftragId;
		List<Auftrag> myList = sqldbconnection
				.getObjectList(sql, Auftrag.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	// TODO: Fix this test!
	// this is no test! so what sould be fixed?
	@Override
	public int saveAuftrag(Auftrag myAuftrag) {
		int id;
		try {
			String sql = "select * from Auftrag where id = "
					+ myAuftrag.getID();
			if (sqldbconnection.getObjectList(sql, Auftrag.class).isEmpty()) {
				id = sqldbconnection.executeInsert(
						"insert into Auftrag (status) values (?)", Integer
								.toString(myAuftrag.getStatus()));
				myAuftrag.setID(id);
				return id;
			} else {
				sqldbconnection.execute(
						"UPDATE Auftrag SET status = ? WHERE id = ?", Integer
								.toString(myAuftrag.getStatus()), Integer
								.toString(myAuftrag.getID()));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return -1;
	}

	@Override
	public boolean deleteAuftrag(Auftrag myAuftrag) {
		try {
			String sql = "select * from Auftrag where id = "
					+ myAuftrag.getID();
			if (sqldbconnection.getObjectList(sql, Auftrag.class).isEmpty()) {
				return false;
			} else {
				sqldbconnection.execute("DELETE FROM Auftrag WHERE id=?;",
						Integer.toString(myAuftrag.getID()));
				return true;
			}
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}

	@Override
	public ArrayList<ImportMedium> getImportMediumList() {
		String sql = "select * from ImportMedium";
		ArrayList<ImportMedium> myList = sqldbconnection.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	@Override
	public ImportMedium getImportMedium(int ImportMediumId) {
		String sql = "select * from ImportMedium where id = " + ImportMediumId;
		ArrayList<ImportMedium> myList = sqldbconnection.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	@Override
	public ArrayList<ImportMedium> getImportMediumList(ImportMedium myMediensammlung) {
		String sql = "select * from ImportMedium where fk_Mediensammlung = "
				+ myMediensammlung.getParentId();
		ArrayList<ImportMedium> myList = sqldbconnection.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	@Override
	public int saveImportMedium(ImportMedium myMedium) {
		int id;
		try {
			String sql = "select * from ImportMedium where id = "
					+ myMedium.getParentId();
			if (sqldbconnection.getObjectList(sql, Auftrag.class).isEmpty()) {
				id = sqldbconnection
						.executeInsert(
								"insert into ImportMedium (status, name) values (?, ?)",
								Integer.toString(myMedium.getParentId()), myMedium
										.getName());
				myMedium.setId(id);
				return id;
			} else {
				sqldbconnection
						.execute(
								"UPDATE ImportMedium SET status = ?, name = ? WHERE id = ?",
								Integer.toString(myMedium.getStatus()),
								myMedium.getName(), Integer.toString(myMedium
										.getParentId()));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return -1;
	}
}
