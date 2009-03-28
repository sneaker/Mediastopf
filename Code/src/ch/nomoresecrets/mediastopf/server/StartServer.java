package ch.nomoresecrets.mediastopf.server;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.nomoresecrets.mediastopf.server.ui.SplashScreen;
import ch.nomoresecrets.mediastopf.server.ui.MediaStopfServer;

public class StartServer {
	
	public static boolean DEBUG = false;
	
	private static final String SPLASHIMAGE = MediaStopfServer.UIIMAGELOCATION + "splash.jpg";

	/**
	 * Start Server
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
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
				MediaStopfServer mediastopf = new MediaStopfServer();
				if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
					DEBUG = true;
					mediastopf.setTitle(MediaStopfServer.PROGRAM + " - Debug");
				} else {
					new SplashScreen(SPLASHIMAGE);
				}
				mediastopf.setVisible(true);
			}
		});
		
		
//		int port = 1337;
//		if (0 < args.length && args[0].equalsIgnoreCase("-port")) {
//			port = Integer.valueOf(args[1]);
//		}
//		
//		// TODO: ANPASSEN!
//		System.out.println("MediaStopf - Ein Softwaresystem zum Lieb haben ;)");
//		System.out.println("===============================================================");
//		System.out.println("Copyright (C)2009");
//		System.out.println("Powered by NoMoreSecrets");
//		System.out.println("www.no-more-secrets.ch");
//		System.out.println("University of Applied Science Rapperswil");
//		System.out.println("www.hsr.ch");
//		System.out.println("");
//
//		new Server(port);
	}
}
