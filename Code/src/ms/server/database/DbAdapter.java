package ms.server.database;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ms.server.domain.Auftrag;
import ms.server.log.Log;

import org.apache.log4j.Logger;


public class DbAdapter {
	
	static private Connection connection;
	static private String database = "jdbc:sqlite:db/db.sqlite";
	
	public static Connection getConnection() throws SQLException {
		Logger logger = Log.getLogger();
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection(database);
				DatabaseMetaData metaData = connection.getMetaData();
				logger.info("sqlite driver mode: " + metaData.getDriverVersion());
				logger.info("database driver: " + metaData.getDriverName());
			}
		} catch (ClassNotFoundException e) {
			logger.fatal("JDBC Driver nof found");
			e.printStackTrace();
		}
		return connection;
	}

	public static List<Auftrag> getOrderList() {
		return getAuftragList();
	}
	
	public static List<Auftrag> getAuftragList() {
		String sql = "select * from Auftrag";
		return ActiveRecordManager.getObjectList(sql, Auftrag.class);
	}
	
	public static List<Auftrag> getAuftragList(int AuftragId ) {
		String sql = "select * from Auftrag WHERE id = " + AuftragId;
		return ActiveRecordManager.getObjectList(sql, Auftrag.class);
	}
}
