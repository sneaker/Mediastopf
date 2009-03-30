package ch.nomoresecrets.mediastopf.server;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import ch.nomoresecrets.mediastopf.server.log.Log;
import ch.nomoresecrets.mediastopf.server.database.ActiveRecordManager;
import ch.nomoresecrets.mediastopf.server.database.DbAdapter;
import ch.nomoresecrets.mediastopf.server.domain.Auftrag;
import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;

public class Server {

	private final int MAX_SERVER_THREADS = 10;

	public Server(int port) {
		startServer(port);
//		loadData();
	}

	private void startServer(int port) {
		Logger logger = Log.getLogger();
		logger.info("Starting network server...");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new NetworkServer(port, MAX_SERVER_THREADS));
	}

	private void loadData() {
		// Get data from DB direct via domain object and activerecord
		String sql = "select * from Auftrag";
		List<Auftrag> lp = ActiveRecordManager.getObjectList(sql, Auftrag.class);
		for (Auftrag name: lp) System.out.println(name.toString());

		// Get data from DB via adapter class
		lp = DbAdapter.getOrderList();
		for (Auftrag name: lp) System.out.println(name.toString());

	}
}
