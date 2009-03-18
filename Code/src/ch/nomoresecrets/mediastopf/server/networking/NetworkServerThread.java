package ch.nomoresecrets.mediastopf.server.networking;

import java.net.*;
import java.io.*;

public class NetworkServerThread implements Runnable {

	private Socket clientSocket;

	private BufferedReader receiver = null;

	private PrintWriter sender = null;

	public NetworkServerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		String receivedMessage = null;

		try {
			receivedMessage = receive();
		} catch (IOException e) {
			System.out.println("Cannot read from receiver");
			e.printStackTrace();
		}

		String reply = MediaStopfProtocol.ProccessRequest(receivedMessage);
		System.out.println("The reply would be: " + reply);

		try {
			send(reply);
		} catch (IOException e) {
			System.out.println("cannot write to sender");
			e.printStackTrace();
		}
	}

	private String receive() throws IOException {
		try {
			receiver = new BufferedReader(new InputStreamReader(clientSocket
					.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error: Cannot get InputStream");
			e.printStackTrace();
		}
		String receivedMessage;

		receivedMessage = receiver.readLine();

		System.out.println("A message from a client: ");
		System.out.println(receivedMessage);

		return receivedMessage;
	}

	private void send(String reply) throws IOException {
		try {
			sender = new PrintWriter(new OutputStreamWriter(clientSocket
					.getOutputStream()), true);
		} catch (IOException e) {
			System.out.println("Error: Cannot get OutputStream");
		}
		
		sender.println(reply);
		System.out.println("The Reply: " + reply);
	}
}
