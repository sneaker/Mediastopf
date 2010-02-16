package ms.utils.server.database;

import java.util.ArrayList;

import ms.domain.Auftrag;
import ms.utils.AuftragslistenUpdater;

public class ServerAuftragslistenUpdater implements AuftragslistenUpdater{
	
	DbAuftragsManager dbauftragsmanager;

	public ServerAuftragslistenUpdater(DbAdapter dbadapter) {
		dbauftragsmanager = DbAuftragsManager.getinstance(dbadapter);
	}
	
	public ArrayList<Auftrag> updateList() {
		return (ArrayList<Auftrag>) dbauftragsmanager.getAuftragList();
	}

	public void stop() {
		//disconnect from datanase
	}
}
