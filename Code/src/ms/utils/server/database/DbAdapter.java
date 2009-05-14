package ms.utils.server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;
import ms.domain.server.ServerAuftrag;
import ms.domain.server.ServerImportMedium;
import ms.utils.log.server.ServerLog;

import org.apache.log4j.Logger;

public class DbAdapter {

	static private Connection connection;
	static private String database = "jdbc:sqlite:db/db.sqlite";

	public static Connection getConnection() throws SQLException {
		Logger logger = ServerLog.getLogger();
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection(database);
				DatabaseMetaData metaData = connection.getMetaData();
				logger.info("sqlite driver mode: "
						+ metaData.getDriverVersion());
				logger.info("database driver: " + metaData.getDriverName());
			}
		} catch (ClassNotFoundException e) {
			logger.fatal("JDBC Driver nof found");
			e.printStackTrace();
		}
		return connection;
	}

	public static List<ServerAuftrag> getOrderList() {
		return getAuftragList();
	}

	public static List<ServerAuftrag> getAuftragList() {
		String sql = "select * from Auftrag";
		List<ServerAuftrag> myList = ActiveRecordManager.getObjectList(sql,
				ServerAuftrag.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static Auftrag getAuftrag(int AuftragId) {
		String sql = "select * from Auftrag WHERE id = " + AuftragId;
		List<ServerAuftrag> myList = ActiveRecordManager.getObjectList(sql,
				ServerAuftrag.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}


	public static List<ServerImportMedium> getImportMediumList() {
		String sql = "select * from ImportMedium";
		List<ServerImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ServerImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static ImportMedium getImportMediumList(int ImportMediumId) {
		String sql = "select * from ImportMedium where id = " + ImportMediumId;
		List<ServerImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ServerImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<ServerImportMedium> getImportMediumList(
			ImportMedium myMediensammlung) {
		String sql = "select * from ImportMedium where fk_Mediensammlung = "
				+ myMediensammlung.getID();
		List<ServerImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ServerImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	

}
