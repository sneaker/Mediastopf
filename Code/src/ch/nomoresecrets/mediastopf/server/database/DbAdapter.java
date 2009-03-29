package ch.nomoresecrets.mediastopf.server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.nomoresecrets.mediastopf.server.domain.*;

public class DbAdapter {
	
	static private Connection connection;
	static private String database = "jdbc:sqlite:db/db.sqlite";
	
	public static Connection getConnection() throws SQLException {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection(database);
				DatabaseMetaData metaData = connection.getMetaData();
				System.out.println("sqlite driver mode: "
						+ metaData.getDriverVersion());
				System.out.println("database driver: "
						+ metaData.getDriverName());
			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver nof found");
			e.printStackTrace();
		}
		return connection;
	}

	public static List<Auftrag> getOrderList() {
		List<Auftrag> resultlist = new ArrayList<Auftrag>();
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
