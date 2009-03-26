package ch.nomoresecrets.mediastopf.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;

//import ch.nomoresecrets.mediastopf.database.*;
//import ch.nomoresecrets.mediastopf.domain.*;

public class Server {

	private final int MAX_SERVER_THREADS = 10;

	public Server(int port) {
		startServer(port);
		loadData();
	}

	private void startServer(int port) {
		System.out.println("Starting network server...");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new NetworkServer(port, MAX_SERVER_THREADS));
	}

	private void loadData() {
		// Get data from DB direct via domain object and activerecord
		String sql = "select * from Auftrag";
		// List<Order> lp = ActiveRecordManager.getObjectList(sql, Order.class);
		// for (Order name: lp) System.out.println(name.toString());

		// Get data from DB via adapter class
		// lp = DbAdapter.getOrderList();
		// for (Order name: lp) System.out.println(name.toString());

	}
}
