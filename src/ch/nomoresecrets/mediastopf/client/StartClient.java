package ch.nomoresecrets.mediastopf.client;

import ch.nomoresecrets.mediastopf.client.ui.MediaStopf;
import ch.nomoresecrets.mediastopf.client.ui.SplashScreen;

public class StartClient {
	
	private static final String SPLASHPIC = MediaStopf.UIIMAGELOCATION + "splash.jpg";

	/**
	 * Start Client
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
			MediaStopf mediastopf = new MediaStopf();
			mediastopf.setVisible(true);
			mediastopf.setTitle("-- Debug Modus --");
		} else {
			MediaStopf mediastopf = new MediaStopf();
			new SplashScreen(SPLASHPIC);
			mediastopf.setVisible(true);
		}
	}
}
