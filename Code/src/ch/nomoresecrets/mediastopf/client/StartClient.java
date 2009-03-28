package ch.nomoresecrets.mediastopf.client;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.nomoresecrets.mediastopf.client.networking.TestConnect;
import ch.nomoresecrets.mediastopf.client.ui.MediaStopf;
import ch.nomoresecrets.mediastopf.client.ui.SplashScreen;

public class StartClient {

	public static boolean DEBUG = false;

	private static final String SPLASHIMAGE = MediaStopf.UIIMAGELOCATION
			+ "splash.jpg";

	/**
	 * Start Client
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
				MediaStopf mediastopf = new MediaStopf();
				if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
					DEBUG = true;
					mediastopf.setTitle(MediaStopf.PROGRAM + " - Debug");
				} else {
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
}
