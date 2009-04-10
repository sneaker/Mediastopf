package ms.client.networking;

import java.io.IOException;
import java.net.UnknownHostException;

import ms.client.log.Log;
import ms.client.utils.ConsoleInput;

import org.apache.log4j.Logger;

public class NetworkClientTester implements Runnable {

	Logger logger = Log.getLogger();
	String host;
	int port;
	ServerConnection nclient = null;

	public NetworkClientTester(String host, int port) {
			this.host = host;
			this.port = port;
	}

	public void run() {
		while (true) {
			System.out.println("command> ");
			String aMessage = ConsoleInput.getMessage();
			try {
				nclient = new ServerConnection(host, port);
			} catch (UnknownHostException e) {
				logger.fatal("Unknown host");
			} catch (IOException e) {
				logger.error("Could not connect to server");
			}
			if (aMessage.equals("exit"))
				break;

			if (aMessage.equals("transfer")) {
				testtransfer();
			}

			if (aMessage.equals("info")) {
				try {
					nclient.getTaskList();
				} catch (IOException e) {
					logger.error("Cannot get task list");
					e.printStackTrace();
				}
			}
		}
	}

	private void testtransfer() {
		System.out.println("filename: ");
		String filename = ConsoleInput.getMessage();
		String path = "/home/thomas/";

		try {
			nclient.sendFile(path + filename);
		} catch (IOException e) {
			logger.error("Cannot send File");
			e.printStackTrace();
		}
	}

}
