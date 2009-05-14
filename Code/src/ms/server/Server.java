package ms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.domain.Auftrag;
import ms.domain.server.ServerAuftrag;
import ms.log.server.ServerLog;
import ms.networking.server.PortListener;
import ms.ui.server.MainViewServer;
import ms.utils.server.database.DbAdapter;

import org.apache.log4j.Logger;


/**
 * server classe
 * loading gui components and start server
 * 
 * @author david
 *
 */
public class Server {
	
	public static final int MAX_SERVER_THREADS = 10;
	private Logger logger = ServerLog.getLogger();

	public Server(int port) {
		startServer(port);
		loadUI();
	}

	private void startServer(int port) {
		serverStartInfo();
		logger.info("Starting network server...");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new PortListener(port, MAX_SERVER_THREADS));
	}

	public static ArrayList<Auftrag> getTaskList() {
		List<ServerAuftrag> list = DbAdapter.getOrderList();
		ArrayList<Auftrag> taskList = new ArrayList<Auftrag>();
		for(Auftrag a: list) {
			//TODO: AUFTRAG - Anpassen für neue Auftragsklasse
			taskList.add(new Auftrag(a.getID()));
		}
		return taskList;
	}
	
	private void serverStartInfo() {
		// TODO: ANPASSEN!
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
				MainViewServer mediastopf = new MainViewServer();
				mediastopf.setVisible(true);
			}
		});
	}
}
