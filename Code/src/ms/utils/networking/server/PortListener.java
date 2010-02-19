package ms.utils.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ms.domain.ImportMedium;
import ms.utils.log.server.ServerLog;

import org.apache.log4j.Logger;


public class PortListener implements Runnable {

	private ServerSocket mediastop_ServerSocket;
	private Logger logger = ServerLog.getLogger();
	private ArrayList<ImportMedium> cache;
	private int THREADCOUNT;
	
	public PortListener(int port, int thread_count, ArrayList<ImportMedium> cache) {
		try {
			mediastop_ServerSocket = new ServerSocket(port);
			THREADCOUNT = thread_count;
			this.cache = cache;
		} catch (IOException e) {
			logger.fatal("Error: MediaStopf cannot bind to port: " + port);
			logger.info("Exiting...");
			System.exit(-1);
		}
	}

	public void run() {
		ExecutorService exec = null;

		exec = Executors.newFixedThreadPool(THREADCOUNT);

		while (true) {
			try {
				exec.execute(new NetProcThread(mediastop_ServerSocket.accept(), cache));
			} catch (IOException e) {
				logger.error("Error on accepting connection");
				e.printStackTrace();
			}
		}
	}
}
