package ms.utils.server.database;

import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;

public class DbAuftragsManager {
	
	private static DbAuftragsManager instance;
	
	private DbAuftragsManager() {
		instance = this;
	}
	
	public static DbAuftragsManager getinstance()
	{
		if (instance == null)
			instance = new DbAuftragsManager();
		
		return instance;
	}
	
	public List<Auftrag> getAuftragList() {
		return SqlDbAdapter.getAuftragList();
	}
	
	public Auftrag getAuftrag(int AuftragId) {
		return SqlDbAdapter.getAuftrag(AuftragId);
	}
	
	public int saveAuftrag(Auftrag myAuftrag) {
		return SqlDbAdapter.saveAuftrag(myAuftrag);
	}
	
	public boolean deleteAuftrag(Auftrag myAuftrag) {
		return SqlDbAdapter.deleteAuftrag(myAuftrag);
	}
	
	public int saveImportMedium(ImportMedium myMedium) {
		return SqlDbAdapter.saveImportMedium(myMedium);
	}
	
	public List<ImportMedium> getImportMediumList() {
		return SqlDbAdapter.getImportMediumList();
	}

	public ImportMedium getImportMediumList(int ImportMediumId) {
		return SqlDbAdapter.getImportMediumList(ImportMediumId);
	}

	public List<ImportMedium> getImportMediumList(ImportMedium myMediensammlung) {
		return SqlDbAdapter.getImportMediumList(myMediensammlung);
	}
}
