package ms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ms.server.database.ActiveRecordManager;
import ms.server.database.DbAdapter;
import ms.server.domain.Auftrag;
import ms.server.interfaces.ServerHandler;
import ms.server.log.Log;
import ms.server.networking.NetworkServer;
import ms.server.ui.MediaStopfServer;
import ms.server.ui.SplashScreen;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Server implements ServerHandler {
	
	private static final String SPLASHIMAGE = MediaStopfServer.UIIMAGELOCATION + "splash.jpg";
	private static final int MAX_SERVER_THREADS = 10;

	public Server(int port) {
		loadLog();
		serverStartInfo();
		startServer(port);
		loadUI();
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
	
	public ArrayList<Auftrag> getDataBase() {
		return DbAdapter.getOrderList();
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
	
	private void loadLog() {
		Log log = new Log();
		log.setLevel(Level.ALL);
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
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

	private void loadUI() {
		setLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MediaStopfServer mediastopf = new MediaStopfServer(Server.this);
				if (StartServer.DEBUG) {
					mediastopf.setTitle(MediaStopfServer.PROGRAM + " - Debug");
				} else {
					new SplashScreen(SPLASHIMAGE);
				}
				mediastopf.setVisible(true);
			}
		});
	}
}
