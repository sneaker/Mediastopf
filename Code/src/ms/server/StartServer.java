package ms.server;

/**
 * start server
 * server in debugmode if args has "-debug" flag and
 * possibility to change port with the "-port" flag
 * 
 * @author david
 *
 */
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
