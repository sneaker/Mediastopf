package ms.utils.server.database;

import java.util.List;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;


public interface DbAdapter {

	int saveImportMedium(ImportMedium myMedium);

	List<ImportMedium> getImportMediumList(ImportMedium myMediensammlung);

	ImportMedium getImportMedium(int ImportMediumId);

	List<ImportMedium> getImportMediumList();

	boolean deleteAuftrag(Auftrag myAuftrag);

	int saveAuftrag(Auftrag myAuftrag);

	Auftrag getAuftrag(int AuftragId);

	List<Auftrag> getAuftragsListe();
}
