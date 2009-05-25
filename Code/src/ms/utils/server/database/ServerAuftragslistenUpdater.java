package ms.utils.server.database;

import java.util.ArrayList;

import ms.domain.Auftrag;
import ms.utils.AuftragslistenUpdater;

public class ServerAuftragslistenUpdater implements AuftragslistenUpdater{

	@Override
	public ArrayList<Auftrag> updateList() {
		return (ArrayList<Auftrag>) DbAuftragsManager.getAuftragList();
	}

	@Override
	public void stop() {
		//disconnect from datanase
	}

	
}
