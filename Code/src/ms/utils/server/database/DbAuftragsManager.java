package ms.utils.server.database;

import java.util.List;

import ms.domain.Auftrag;

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
}
