package ch.nomoresecrets.mediastopf.server;

import java.util.List;


import ch.nomoresecrets.mediastopf.server.networking.NetworkServer;
import ch.nomoresecrets.mediastopf.database.*;
import ch.nomoresecrets.mediastopf.domain.*;



public class Server {

	public Server(int port) {
		NetworkServer testserver = new NetworkServer(port);
		testserver.run();
		
	}
	
	
}
