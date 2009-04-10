package ms.server.database;

import java.sql.SQLException;
import java.util.List;



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
		List<Auftrag> AuftragsList = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		for (Auftrag name: AuftragsList) System.out.println(name.toString());

		System.out.println("Mediensammlungliste:");
		sql = "select * from Mediensammlung";
		List<Mediensammlung> MediensammlungList = ActiveRecordManager.getObjectList(sql, Mediensammlung.class);
		for (Mediensammlung name: MediensammlungList) System.out.println(name.toString());
		
		System.out.println("Importmediumliste:");
		sql = "select * from Importmedium";
		List<ImportMedium> ImportmediumList = ActiveRecordManager.getObjectList(sql, ImportMedium.class);
		for (ImportMedium name: ImportmediumList) System.out.println(name.toString());
		
		System.out.println("Itemliste:");
		sql = "select * from Item";
		List<Item> ItemList = ActiveRecordManager.getObjectList(sql, Item.class);
		for (Item name: ItemList) System.out.println(name.toString());
		
		/*System.out.println("BildItemliste:");
		sql = "select * from BildItem";
		List<BildItem> BildItemList = ActiveRecordManager.getObjectList(sql, BildItem.class);
		for (BildItem name: BildItemList) System.out.println(name.toString());
		*/
		
		System.out.println("Containerliste:");
		sql = "select * from Container";
		List<Container> ContainerList = ActiveRecordManager.getObjectList(sql, Container.class);
		for (Container name: ContainerList) System.out.println(name.toString());
		
		System.out.println("Einlesegeraetliste:");
		sql = "select * from Einlesegeraet";
		List<Einlesegeraet> EinlesegeraetList = ActiveRecordManager.getObjectList(sql, Einlesegeraet.class);
		for (Einlesegeraet name: EinlesegeraetList) System.out.println(name.toString());
		
		System.out.println("Einlesestationliste:");
		sql = "select * from Einlesestation";
		List<Einlesestation> EinlesestationList = ActiveRecordManager.getObjectList(sql, Einlesestation.class);
		for (Einlesestation name: EinlesestationList) System.out.println(name.toString());
		
		System.out.println("Sammelstationliste:");
		sql = "select * from Sammelstation";
		List<Sammelstation> SammelstationList = ActiveRecordManager.getObjectList(sql, Sammelstation.class);
		for (Sammelstation name: SammelstationList) System.out.println(name.toString());
		
		System.out.println("Exportmediumliste:");
		sql = "select * from Exportmedium";
		List<ExportMedium> ExportmediumList = ActiveRecordManager.getObjectList(sql, ExportMedium.class);
		for (ExportMedium name: ExportmediumList) System.out.println(name.toString());
		
		//Get data from DB via adapter class
		//lp = DbAdapter.getOrderList();
		//for (Auftrag name: lp) System.out.println(name.toString());
		
		
		
	}

}
