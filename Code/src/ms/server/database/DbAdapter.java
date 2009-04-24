package ms.server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import ms.server.domain.Auftrag;
import ms.server.domain.Einlesegeraet;
import ms.server.domain.Einlesestation;
import ms.server.domain.ImportMedium;
import ms.server.domain.Mediensammlung;
import ms.server.domain.Sammelstation;
import ms.server.log.ServerLog;

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

	public static List<Auftrag> getOrderList() {
		return getAuftragList();
	}

	public static List<Auftrag> getAuftragList() {
		String sql = "select * from Auftrag";
		List<Auftrag> myList = ActiveRecordManager.getObjectList(sql,
				Auftrag.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static Auftrag getAuftrag(int AuftragId) {
		String sql = "select * from Auftrag WHERE id = " + AuftragId;
		List<Auftrag> myList = ActiveRecordManager.getObjectList(sql,
				Auftrag.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<Mediensammlung> getMediensammlungList() {
		String sql = "select * from Mediensammlung";
		List<Mediensammlung> myList = ActiveRecordManager.getObjectList(sql,
				Mediensammlung.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static Mediensammlung getMediensammlung(int MediensammlungId) {
		String sql = "select * from Mediensammlung where id = "
				+ MediensammlungId;
		List<Mediensammlung> myList = ActiveRecordManager.getObjectList(sql,
				Mediensammlung.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<Mediensammlung> getMediensammlungList(Auftrag myAuftrag) {
		String sql = "select * from Mediensammlung where fk_Auftrag = "
				+ myAuftrag.getID();
		List<Mediensammlung> myList = ActiveRecordManager.getObjectList(sql,
				Mediensammlung.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static List<ImportMedium> getImportMediumList() {
		String sql = "select * from ImportMedium";
		List<ImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static ImportMedium getImportMediumList(int ImportMediumId) {
		String sql = "select * from ImportMedium where id = " + ImportMediumId;
		List<ImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<ImportMedium> getImportMediumList(
			Mediensammlung myMediensammlung) {
		String sql = "select * from ImportMedium where fk_Mediensammlung = "
				+ myMediensammlung.getID();
		List<ImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static List<ImportMedium> getImportMediumList(
			Einlesegeraet myEinlesegeraet) {
		String sql = "select * from ImportMedium where fk_Einlesegeraet = "
				+ myEinlesegeraet.getID();
		List<ImportMedium> myList = ActiveRecordManager.getObjectList(sql,
				ImportMedium.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static List<Sammelstation> getSammelstationList() {
		String sql = "select * from Sammelstation";
		List<Sammelstation> myList = ActiveRecordManager.getObjectList(sql,
				Sammelstation.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static Sammelstation getSammelstation(int SammelstationId) {
		String sql = "select * from Sammelstation WHERE id = "
				+ SammelstationId;
		List<Sammelstation> myList = ActiveRecordManager.getObjectList(sql,
				Sammelstation.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<Einlesestation> getEinlesestationList() {
		String sql = "select * from Einlesestation";
		List<Einlesestation> myList = ActiveRecordManager.getObjectList(sql,
				Einlesestation.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static Einlesestation getEinlesestation(int EinlesestationId) {
		String sql = "select * from Einlesestation WHERE id = "
				+ EinlesestationId;
		List<Einlesestation> myList = ActiveRecordManager.getObjectList(sql,
				Einlesestation.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<Einlesestation> getEinlesestation(
			Sammelstation mySammelstation) {
		String sql = "select * from Einlesestation WHERE fk_Sammelstation = "
				+ mySammelstation.getID();
		List<Einlesestation> myList = ActiveRecordManager.getObjectList(sql,
				Einlesestation.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static List<Einlesegeraet> getEinlesegeraetList() {
		String sql = "select * from Einlesegeraet";
		List<Einlesegeraet> myList = ActiveRecordManager.getObjectList(sql,
				Einlesegeraet.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

	public static Einlesegeraet getEinlesegeraet(int EinlesegeraetId) {
		String sql = "select * from Einlesegeraet WHERE id = "
				+ EinlesegeraetId;
		List<Einlesegeraet> myList = ActiveRecordManager.getObjectList(sql,
				Einlesegeraet.class);
		if (myList.isEmpty())
			return null;
		else
			return myList.get(0);
	}

	public static List<Einlesegeraet> getEinlesegeraet(
			Einlesestation myEinlesestation) {
		String sql = "select * from Einlesegeraet WHERE fk_Einlesestation = "
				+ myEinlesestation.getID();
		List<Einlesegeraet> myList = ActiveRecordManager.getObjectList(sql,
				Einlesegeraet.class);
		if (myList.isEmpty())
			return null;
		else
			return myList;
	}

}
