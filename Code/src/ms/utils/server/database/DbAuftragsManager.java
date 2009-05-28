package ms.utils.server.database;

import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;

public class DbAuftragsManager {
	
	private static DbAuftragsManager instance;
	
	private DbAdapter dbadapter ;
	
	private DbAuftragsManager(DbAdapter dbAdapter) {
		instance = this;
		this.dbadapter = dbAdapter;
	}
	
	public static DbAuftragsManager getinstance(DbAdapter dbadapter)
	{
		if (instance == null)
			instance = new DbAuftragsManager(dbadapter);
		
		return instance;
	}
	
	public List<Auftrag> getAuftragList() {
		return dbadapter.getAuftragsListe();
	}
	
	public Auftrag getAuftrag(int AuftragId) {
		return dbadapter.getAuftrag(AuftragId);
	}
	
	public int saveAuftrag(Auftrag myAuftrag) {
		return dbadapter.saveAuftrag(myAuftrag);
	}
	
	public boolean deleteAuftrag(Auftrag myAuftrag) {
		return dbadapter.deleteAuftrag(myAuftrag);
	}
	
	public int saveImportMedium(ImportMedium myMedium) {
		return dbadapter.saveImportMedium(myMedium);
	}
	
	public List<ImportMedium> getImportMediumList() {
		return dbadapter.getImportMediumList();
	}

	public ImportMedium getImportMediumList(int ImportMediumId) {
		return dbadapter.getImportMedium(ImportMediumId);
	}

	public List<ImportMedium> getImportMediumList(ImportMedium myMediensammlung) {
		return dbadapter.getImportMediumList(myMediensammlung);
	}
}
