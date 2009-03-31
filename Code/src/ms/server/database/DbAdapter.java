package ms.server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	public static ArrayList<Auftrag> getOrderList() {
		ArrayList<Auftrag> resultlist = new ArrayList<Auftrag>();
		Connection conn;
		try {
			conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery("select * from Auftrag");
			while (res.next()) {
				resultlist.add(new Auftrag(res));
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultlist;
	}
}
