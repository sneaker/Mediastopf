package ms.utils.server.database;

import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;

public class DbAuftragsManager {
	
	public static List<Auftrag> getAuftragList() {
		return SqlDbAdapter.getAuftragList();
	}
	
	public static Auftrag getAuftrag(int AuftragId) {
		return SqlDbAdapter.getAuftrag(AuftragId);
	}
	
	public static int saveAuftrag(Auftrag myAuftrag) {
		return SqlDbAdapter.saveAuftrag(myAuftrag);
	}
	
	public static boolean deleteAuftrag(Auftrag myAuftrag) {
		return SqlDbAdapter.deleteAuftrag(myAuftrag);
	}
	
	public static int saveImportMedium(ImportMedium myMedium) {
		return SqlDbAdapter.saveImportMedium(myMedium);
	}
	
	public static List<ImportMedium> getImportMediumList() {
		return SqlDbAdapter.getImportMediumList();
	}

	public static ImportMedium getImportMediumList(int ImportMediumId) {
		return SqlDbAdapter.getImportMediumList(ImportMediumId);
	}

	public static List<ImportMedium> getImportMediumList(ImportMedium myMediensammlung) {
		return SqlDbAdapter.getImportMediumList(myMediensammlung);
	}
}
