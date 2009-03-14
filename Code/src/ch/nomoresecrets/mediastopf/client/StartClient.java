package ch.nomoresecrets.mediastopf.client;

import java.io.IOException;

import ch.nomoresecrets.mediastopf.client.networking.TestConnect;
import ch.nomoresecrets.mediastopf.client.ui.MediaStopf;
import ch.nomoresecrets.mediastopf.client.ui.SplashScreen;

public class StartClient {
	
	private static final String SPLASHIMAGE = MediaStopf.UIIMAGELOCATION + "splash.jpg";

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
			new SplashScreen(SPLASHIMAGE);
			mediastopf.setVisible(true);
		}
		try {
			TestConnect connect = new TestConnect();
		} catch (IOException e) {
			System.out.println("Error in TestConnect");
			e.printStackTrace();
		}
	}
}
