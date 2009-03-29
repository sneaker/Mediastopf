package ch.nomoresecrets.mediastopf.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ch.nomoresecrets.mediastopf.client.log.Log;
import ch.nomoresecrets.mediastopf.client.networking.ServerConnection;
import ch.nomoresecrets.mediastopf.client.ui.MediaStopf;
import ch.nomoresecrets.mediastopf.client.ui.SplashScreen;

public class StartClient {

	public static boolean DEBUG = false;

	private static final String SPLASHIMAGE = MediaStopf.UIIMAGELOCATION	+ "splash.jpg";
	private static Logger logger = Log.getLogger();
	private static ServerConnection connection;

	/**
	 * Start Client
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		Log log = new Log();
		log.setLevel(Level.ALL);
		try {
			connection = new ServerConnection("localhost", 1337);
		} catch (UnknownHostException e) {
			logger.fatal("Unknown host");
		} catch (IOException e) {
			logger.error("Could not get I/O for connection to Server");
		}
		
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
				MediaStopf mediastopf;
				if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
					DEBUG = true;
					mediastopf = new MediaStopf(connection);
					mediastopf.setTitle(MediaStopf.PROGRAM + " - Debug");
				} else {
					mediastopf = new MediaStopf(connection);
					new SplashScreen(SPLASHIMAGE);
				}
				mediastopf.setVisible(true);
			}
		});
	}
}
