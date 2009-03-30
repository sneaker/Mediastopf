package ch.nomoresecrets.mediastopf.client;


public class StartClient {
	
	public static boolean DEBUG = false;

	/**
	 * Start Client
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
			DEBUG = true;
		}
		new Client();
	}
}
