package ch.nomoresecrets.mediastopf.server;

import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;

public class Server {

	private final int MAX_SERVER_THREADS = 10;
	
	public Server(int port) {
		NetworkServer netserver = new NetworkServer(port, MAX_SERVER_THREADS);
	}
}
