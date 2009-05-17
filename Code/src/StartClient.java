/**
 * start client
 * client in debugmode if args has a "-debug" flag
 * 
 * @author david
 *
 */
public class StartClient {
	/**
	 * Start Client
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		boolean DEBUG = false;
		
		if (0 < args.length && args[0].equalsIgnoreCase("-debug")) {
			DEBUG = true;
		}
		
		new InitClient(DEBUG);
	}
}
