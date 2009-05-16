package ms.application.server;

import java.util.ArrayList;
import java.util.List;

import ms.domain.Auftrag;
import ms.domain.server.ServerAuftrag;
import ms.utils.log.server.ServerLog;
import ms.utils.server.database.DbAdapter;

import org.apache.log4j.Logger;


/**
 * server classe
 * loading gui components and start server
 * 
 * @author david
 *
 */
public class ServerController {
	
	private Logger logger = ServerLog.getLogger();

	public static ArrayList<Auftrag> getTaskList() {
		List<ServerAuftrag> list = DbAdapter.getOrderList();
		ArrayList<Auftrag> taskList = new ArrayList<Auftrag>();
		for(Auftrag a: list) {
			//TODO: AUFTRAG - Anpassen für neue Auftragsklasse
			taskList.add(new Auftrag(a.getID()));
		}
		return taskList;
	}
}
