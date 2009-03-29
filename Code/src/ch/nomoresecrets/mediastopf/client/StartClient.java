package ch.nomoresecrets.mediastopf.client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Level;

import ch.nomoresecrets.mediastopf.client.networking.TestConnect;
import ch.nomoresecrets.mediastopf.client.ui.MediaStopf;
import ch.nomoresecrets.mediastopf.client.ui.SplashScreen;
import ch.nomoresecrets.mediastopf.server.log.Log;
import ch.nomoresecrets.mediastopf.filesys.DirectoryObserver;

public class StartClient {

	public static boolean DEBUG = false;

	private static final String SPLASHIMAGE = MediaStopf.UIIMAGELOCATION	+ "splash.jpg";

	/**
	 * Start Client
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
				MediaStopf mediastopf;
				if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
					DEBUG = true;
					mediastopf = new MediaStopf();
					mediastopf.setTitle(MediaStopf.PROGRAM + " - Debug");
				} else {
					mediastopf = new MediaStopf();
					new SplashScreen(SPLASHIMAGE);
				}
				mediastopf.setVisible(true);
			}
		});

		try {
			TestConnect connect = new TestConnect();
		} catch (IOException e) {
			System.out.println("Error in TestConnect");
			e.printStackTrace();
		}
	}
	
	/*
	private void testwatcher() {
		System.out.println("Starting directory watcher service...");
		DirectoryObserver dserver = new DirectoryObserver("/tmp/");
		
		dserver.subscribe(new Observer(){
			public void update(Observable o, Object arg) {
				System.out.println("Change Detected!");
			}
		});
		
		dserver.start();
		dserver.run();
	}
	*/
}
