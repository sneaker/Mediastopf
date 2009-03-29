package ch.nomoresecrets.mediastopf.server;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ch.nomoresecrets.mediastopf.server.log.Log;
import ch.nomoresecrets.mediastopf.server.ui.MediaStopfServer;
import ch.nomoresecrets.mediastopf.server.ui.SplashScreen;

public class StartServer {
	
	public static boolean DEBUG = false;
	
	private static final String SPLASHIMAGE = MediaStopfServer.UIIMAGELOCATION + "splash.jpg";

	/**
	 * Start Server
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		Log log = new Log();
		log.setLevel(Level.ALL);
		
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MediaStopfServer mediastopf;
				if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
					DEBUG = true;
					mediastopf = new MediaStopfServer();
					mediastopf.setTitle(MediaStopfServer.PROGRAM + " - Debug");
				} else {
					mediastopf = new MediaStopfServer();
					new SplashScreen(SPLASHIMAGE);
				}
				mediastopf.setVisible(true);
			}
		});
		
		
		int port = 1337;
		if (0 < args.length && args[0].equalsIgnoreCase("-port")) {
			port = Integer.valueOf(args[1]);
		}
		
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

		new Server(port);
	}
}
