package ms.server.networking;

public class MediaStopfProtocol {
	
	public static String Ok() {
		return "OK\n";
	}
	
	public static String ProccessRequest(String request) {
		String reply;
		
		if (request.split(" ")[0].equals("INFO")) {
			reply = "Auftragsliste\n";
		}
		else if (request.split(" ")[0].equals("TRANSFER")) {
			reply = "TRANSFER\n";
		} 
		else {
			reply = "UNKNOWN\n";
		}

		return reply;
	}
}
