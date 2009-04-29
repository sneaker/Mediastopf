package ms.server.database;

import java.sql.SQLException;
import java.util.List;



import ms.common.domain.Auftrag;
import ms.common.domain.ExportMedium;
import ms.common.domain.ImportMedium;
import ms.common.domain.MedienSammlung;
import ms.server.domain.*;



public class DatabaseTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createTablesIfMissing();
		loadData();

	}
	
	private static void createTablesIfMissing() {
		try {
			ActiveRecordManager.execute("select * from Auftrag");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Auftrag (id INTEGER PRIMARY KEY  NOT NULL , status INTEGER NOT NULL  DEFAULT 1)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from BildItem");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE BildItem (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , fk_Item INTEGER NOT NULL , Aufloesung INTEGER, Breite INTEGER, Hoehe INTEGER, Aufnahmeort VARCHAR)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from Container");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Container (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , fk_Container INTEGER, Name VARCHAR NOT NULL )");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from Einlesegeraet");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Einlesegeraet (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , fk_Einlesestation INTEGER NOT NULL , Name VARCHAR)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from Einlesestation");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Einlesestation (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , fk_Sammelstation INTEGER NOT NULL , Name VARCHAR NOT NULL , Netzwerkadresse VARCHAR NOT NULL )");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from ExportMedium");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE ExportMedium (id INTEGER PRIMARY KEY  NOT NULL , fk_Container INTEGER, fk_Auftrag INTEGER NOT NULL , Name VARCHAR, Speicherkapazitaet INTEGER)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from ImportMedium");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE ImportMedium (id INTEGER PRIMARY KEY  NOT NULL ,fk_Mediensammlung INTEGER NOT NULL ,Name VARCHAR,fk_Einlesegeraet INTEGER)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from Item");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Item (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , fk_ImportMedium INTEGER NOT NULL , fk_Container INTEGER, Name VARCHAR, Importdatum DATETIME, Speicherort VARCHAR)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from Mediensammlung");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Mediensammlung (id INTEGER PRIMARY KEY  NOT NULL ,name VARCHAR,fk_Auftrag INTEGER NOT NULL  DEFAULT 1 ,typ VARCHAR NOT NULL  DEFAULT 'Image' )");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from MusikItem");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE MusikItem (id INTEGER PRIMARY KEY  NOT NULL ,fk_Item INTEGER NOT NULL ,Dauer INTEGER,Interpret VARCHAR)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			ActiveRecordManager.execute("select * from Sammelstation");
		} catch (SQLException e) {
			try {
				ActiveRecordManager.execute("CREATE TABLE Sammelstation (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , Name VARCHAR NOT NULL , Netzwerkadresse VARCHAR NOT NULL )");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	
	
	private static void loadData() {
		//Get data from DB direct via domain object and activerecord
		System.out.println("Auftragliste:");
		String sql = "select * from Auftrag";
		List<ServerAuftrag> AuftragsList = ActiveRecordManager.getObjectList(sql, ServerAuftrag.class);
		for (Auftrag name: AuftragsList) System.out.println(name.toString());

		System.out.println("Mediensammlungliste:");
		sql = "select * from Mediensammlung";
		List<ServerMedienSammlung> MediensammlungList = ActiveRecordManager.getObjectList(sql, ServerMedienSammlung.class);
		for (MedienSammlung name: MediensammlungList) System.out.println(name.toString());
		
		System.out.println("Importmediumliste:");
		sql = "select * from Importmedium";
		List<ServerImportMedium> ImportmediumList = ActiveRecordManager.getObjectList(sql, ServerImportMedium.class);
		for (ImportMedium name: ImportmediumList) System.out.println(name.toString());
	
		System.out.println("Exportmediumliste:");
		sql = "select * from Exportmedium";
		List<ServerExportMedium> ExportmediumList = ActiveRecordManager.getObjectList(sql, ServerExportMedium.class);
		for (ExportMedium name: ExportmediumList) System.out.println(name.toString());
		
		//Get data from DB via adapter class
		//lp = DbAdapter.getOrderList();
		//for (Auftrag name: lp) System.out.println(name.toString());
		
		
		
	}

}
