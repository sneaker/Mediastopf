package ms.application.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import ms.ui.server.MainView;
import ms.utils.FileIO;
import ms.utils.log.server.ServerLog;
import ms.utils.networking.server.PortListener;

public class InitServer {
	
	private static Logger logger = ServerLog.getLogger();
	
	public InitServer(int port) {
		serverStartInfo();
		initNetwork(port);
		initUI();
	}
	
	private void initUI() {
		setLookAndFeel();
		loadUI();
	}

	public static final int MAX_SERVER_THREADS = 10;
	
	private void initNetwork(int port) {
		logger.info("Starting network server...");
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new PortListener(port, MAX_SERVER_THREADS));
	}
	
	
	/**
	 * copying files
	 * 
	 * @param folder sourceforlder
	 * @param exportFolder destinationfolder
	 * @return true if copying succeed
	 */
	public static boolean copyFiles(File[] folder, File exportFolder) {
		boolean succeed = FileIO.copyFiles(folder, exportFolder);
		if(succeed) {
			logger.info("Filetransfer succeed");
		} else {
			logger.warn("Filetransfer failed");
		}
		return succeed;
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
				MainView mediastopf = new MainView();
				mediastopf.setVisible(true);
			}
		});
	}
}
