package ch.nomoresecrets.mediastopf.server;

import java.util.List;


import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;



public class Server {

	private final int MAX_SERVER_THREADS = 10;
	
	public Server(int port) {
		NetworkServer testserver = new NetworkServer(port);
		testserver.run();
	}
}
