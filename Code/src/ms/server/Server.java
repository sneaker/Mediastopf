package ms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.server.database.DbAdapter;
import ms.server.domain.Auftrag;
import ms.server.interfaces.ServerHandler;
import ms.server.log.Log;
import ms.server.logic.Task;
import ms.server.networking.NetworkServer;
import ms.server.ui.MainViewServer;

import org.apache.log4j.Logger;


/**
 * server classe
 * loading gui components and start server
 * 
 * @author david
 *
 */
public class Server implements ServerHandler {
	
	public static final int MAX_SERVER_THREADS = 10;

	public Server(int port) {
		startServer(port);
		loadUI();
	}

	private void startServer(int port) {
		serverStartInfo();
		Logger logger = Log.getLogger();
		logger.info("Starting network server...");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new NetworkServer(port, MAX_SERVER_THREADS));
	}

	public ArrayList<Task> getDataBase() {
		List<Auftrag> list = DbAdapter.getOrderList();
		ArrayList<Task> tasks = new ArrayList<Task>();
		for(Auftrag a: list) {
			tasks.add(new Task(a.getID(), Integer.toString(a.getStatus())));
		}
		return tasks;

	}
	
	public void sendObject(Object o) {
		//TODO
	}
	
	public Object getObject() {
		//TODO
		return null;
	}
	
	/**
	 * cancel running job
	 */
	public void cancelJob() {
		//TODO
	}
	
	private void serverStartInfo() {
		// TODO: ANPASSEN!
		Logger logger = Log.getLogger();
		logger.info("=======================================================");
		logger.info("MediaStopf - Ein Softwaresystem zum Lieb haben ;)");
		logger.info("=======================================================");
		logger.info("Copyright (C)2009");
		logger.info("Powered by NoMoreSecrets");
		logger.info("www.no-more-secrets.ch");
		logger.info("University of Applied Science Rapperswil");
		logger.info("www.hsr.ch");
		logger.info("=======================================================");
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void loadUI() {
		setLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainViewServer mediastopf = new MainViewServer(Server.this);
				mediastopf.setVisible(true);
			}
		});
	}
}
