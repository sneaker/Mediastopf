package ch.nomoresecrets.mediastopf.client.networking;

import java.io.*;
import java.net.UnknownHostException;

public class TestConnect {
	
	public TestConnect() {
	try {
		ServerConnection conn = new ServerConnection("localhost", 1337);
		while (true) {
			String aMessage = getMessage();
			conn.Send(aMessage);
		}
	} catch (UnknownHostException e) {
		System.err.println("Unknown host");
	} catch (IOException e) {
		System.err.println("Could not get I/O for connection to Server");
	}
	
}

public String getMessage() {
        BufferedReader UserInput = new BufferedReader(new InputStreamReader(System.in));
        String message = null;
		try {
			System.out.println("Message to Server:");
			message = UserInput.readLine();
			if (message == "exit") {
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
