package ch.nomoresecrets.mediastopf.server.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import ch.nomoresecrets.mediastopf.client.log.Log;

public class NetworkServer {

	private ServerSocket mediastop_ServerSocket;
	private Logger logger = Log.getLogger();
	
	public NetworkServer(int port, int thread_count) {
		try {
			mediastop_ServerSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.fatal("Error: MediaStopf cannot bind to port: " + port);
			logger.info("Exiting...");
			System.exit(-1);
		}
		
		ExecutorService exec = null;
		
		for (int i = 0; i < 5; ++i) {
			exec = Executors.newFixedThreadPool(thread_count);
		}
		
		
		while(mediastop_ServerSocket.isBound()) {	
			try {
				exec.execute(new NetworkServerThread(mediastop_ServerSocket.accept()));
			} catch (IOException e) {
				logger.error("Error on accepting connection");
				e.printStackTrace();
			}
		}
		exec.shutdown();
	}
}
