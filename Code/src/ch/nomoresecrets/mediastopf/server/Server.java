package ch.nomoresecrets.mediastopf.server;

import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;

public class Server {

	public Server(int port) {
		NetworkServer testserver = new NetworkServer(port);
		testserver.run();
	}
}
