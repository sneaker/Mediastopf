package ms.server;

public class StartServer {

	public static boolean DEBUG = false;
	public static int PORT = 1337;

	/**
	 * Start Server
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		if (0 < args.length) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equalsIgnoreCase("-port")) {
					PORT = Integer.valueOf(args[i + 1]);
				}
				if (args[i].equalsIgnoreCase("-debug")) {
					DEBUG = true;
				}
			}
		}
		new Server(PORT);
	}
}
