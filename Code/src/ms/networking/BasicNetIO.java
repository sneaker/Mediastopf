package ms.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import ms.log.Log;

import org.apache.log4j.Logger;

abstract public class BasicNetIO {
	
	protected Socket commSocket = null;
	protected Logger logger = Log.getLogger();

	protected String receiveMessage() throws IOException {
		BufferedReader receiver = null;
		
		try {
			receiver = new BufferedReader(new InputStreamReader(commSocket
					.getInputStream()));
		} catch (IOException e) {
			logger.error("Error: Cannot get InputStream");
			e.printStackTrace();
		}
	
		String rec = receiver.readLine();
		logger.info("SERVER: Client message: " + rec);
		return rec;
	}

	protected void sendMessage(String message) throws IOException {
		PrintWriter sender = null;
		
		try {
			sender = new PrintWriter(new OutputStreamWriter(commSocket
					.getOutputStream()), false);
			logger.info("SERVER: Server message: " + message);
		} catch (IOException e) {
			logger.error("Error: Cannot get OutputStream");
		}
	
		sender.println(message);
		sender.flush();
	}
	
	
}
