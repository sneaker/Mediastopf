package ms.server;


public class StartServer {
	
	public static boolean DEBUG = false;
	
	private static int PORT = 1337;
	
	/**
	 * Start Server
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		for(int i=0; i<args.length; i++) {
			if(0<args.length) {
				if (args[i].equalsIgnoreCase("-port")) {
					PORT = Integer.valueOf(args[i+1]);
				}
				if (args[i].equalsIgnoreCase("-debug")) {
					DEBUG = true;
				}
			}
		}
		
		new Server(PORT);
	}
}
