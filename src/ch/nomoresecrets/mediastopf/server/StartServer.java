package ch.nomoresecrets.mediastopf.server;

public class StartServer {

	/**
	 * Start Server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 1337;
		if (0 < args.length && args[0].equalsIgnoreCase("-port")) {
			port = Integer.valueOf(args[1]);
		}
		
		// TODO: ANPASSEN!
		System.out.println("MediaStopf - Ein Softwaresystem zum Lieb haben ;)");
		System.out.println("===============================================================");
		System.out.println("Copyright (C)2009");
		System.out.println("Powered by NoMoreSecrets");
		System.out.println("www.no-more-secrets.ch");
		System.out.println("University of Applied Science Rapperswil");
		System.out.println("www.hsr.ch");
		System.out.println("");

		new Server(port);
	}
}
