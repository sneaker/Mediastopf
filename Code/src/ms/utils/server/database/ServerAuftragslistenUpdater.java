package ms.utils.server.database;

import java.util.ArrayList;

import ms.domain.Auftrag;
import ms.utils.AuftragslistenUpdater;

public class ServerAuftragslistenUpdater implements AuftragslistenUpdater{

	public ArrayList<Auftrag> updateList() {
		return (ArrayList<Auftrag>) DbAuftragsManager.getAuftragList();
	}

	public void stop() {
		//disconnect from datanase
	}

	
}
