import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.ui.server.MainView;
import ms.utils.log.server.ServerLog;
import ms.utils.networking.server.PortListener;

import org.apache.log4j.Logger;

public class InitServer {
	
	private Logger logger = ServerLog.getLogger();
	public static boolean DEBUG = false;
	public static final int MAX_SERVER_THREADS = 10;
	
	public InitServer(int port, boolean debug) {
		DEBUG = debug;
		serverStartInfo();
		initNetwork(port);
		initUI();
		new ms.application.server.ServerController();
	}
	
	private void initUI() {
		setLookAndFeel();
		loadUI();
	}

	private void initNetwork(int port) {
		logger.info("Starting network server...");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new PortListener(port, MAX_SERVER_THREADS));
	}
	
	private void serverStartInfo() {
		logger.info("=======================================================");
		logger.info("MediaStopf - Ein Softwaresystem zum Medien Einlesen");
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
				MainView mediastopf = new MainView(DEBUG);
				mediastopf.setVisible(true);
			}
		});
	}
}