package ch.nomoresecrets.mediastopf.server;

import java.util.List;


import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;
import ch.nomoresecrets.mediastopf.database.*;
import ch.nomoresecrets.mediastopf.domain.*;



public class Server {

	private final int MAX_SERVER_THREADS = 10;
	
	public Server(int port) {
		NetworkServer netserver = new NetworkServer(port, MAX_SERVER_THREADS);
		loadData();
		testserver.run();
		
	}
	
	private void loadData() {
		//Get data from DB direct via domain object and activerecord
		String sql = "select * from Auftrag";
		List<Order> lp = ActiveRecordManager.getObjectList(sql, Order.class);
		for (Order name: lp) System.out.println(name.toString());

		//Get data from DB via adapter class
		lp = DbAdapter.getOrderList();
		for (Order name: lp) System.out.println(name.toString());
		
	}
}
