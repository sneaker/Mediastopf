package ch.nomoresecrets.mediastopf.client.networking;

import java.io.*;
import java.net.UnknownHostException;

public class TestConnect {

	public TestConnect() throws IOException {
		
		while (true) {
			String aMessage = getMessage();
			if (aMessage.equals("exit"))
				break;
			
			ServerConnection conn = null;
			try {
				conn = new ServerConnection("warbird", 1337);
			} catch (UnknownHostException e) {
				System.err.println("Unknown host");
			} catch (IOException e) {
				System.err.println("Could not get I/O for connection to Server");
			}
			
			conn.Send(aMessage);
			System.out.println(conn.Receive());
		}

	}

	public String getMessage() {
		BufferedReader UserInput = new BufferedReader(new InputStreamReader(
				System.in));
		String message = null;
		try {
			System.out.println("Message to Server:");
			message = UserInput.readLine();
			if (message.equals("exit")) {
				System.out.println("Exiting Mediastopf...");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println("Error: Cannot get Message from Keyboard");
			e.printStackTrace();
		}

		return message;
	}
}
