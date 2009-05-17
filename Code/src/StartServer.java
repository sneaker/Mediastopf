/**
 * Main class for the MediaStopf Server instance. 
 * start server
 * server in debugmode if args has "-debug" flag and
 * possibility to change port with the "-port" flag
 * 
 * @author david
 *
 */
public class StartServer {

	
	public static int PORT = 1337;

	/**
	 * Start Server
	 * 
	 * @param args
	 *            Use -port to set another port to listen to. <br />
	 *            Use -debug to suppress splash screen at startup and enable
	 *            more logging.
	 */
	public static void main(final String[] args) {
		boolean DEBUG = false;
		
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
		new InitServer(PORT, DEBUG);
	}
}
