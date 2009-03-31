package ms.client.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import ms.client.log.Log;

import org.apache.log4j.Logger;


public class TestConnect {
	
	private Logger logger = Log.getLogger();

	public TestConnect() throws IOException {
		
		while (true) {
			String aMessage = getMessage();
			if (aMessage.equals("exit"))
				break;
			
			ServerConnection conn = null;
			try {
				conn = new ServerConnection("localhost", 1337);
			} catch (UnknownHostException e) {
				logger.fatal("Unknown host");
			} catch (IOException e) {
				logger.error("Could not get I/O for connection to Server");
			}
			
			conn.sendMessage(aMessage);
			
			if (aMessage.equals("TRANSFER")) {
				conn.sendFile("asdf.log");
			}
			
			logger.info(conn.receiveMessage());
		}
	}

	public String getMessage() {
		BufferedReader UserInput = new BufferedReader(new InputStreamReader(System.in));
		String message = null;
		try {
			logger.info("Message to Server:");
			message = UserInput.readLine();
		} catch (IOException e) {
			logger.error("Error: Cannot get Message from Keyboard");
			e.printStackTrace();
		}

		return message;
	}
}
