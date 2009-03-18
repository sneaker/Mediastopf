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

	@Override
	public void run() {
		String receivedMessage = null;

		try {
			receivedMessage = receiveMessage();
		} catch (IOException e) {
			System.out.println("Cannot read from receiver");
			e.printStackTrace();
		}

		String reply = MediaStopfProtocol.ProccessRequest(receivedMessage);
		System.out.println("The reply would be: " + reply);

		try {
			sendMessage(reply);
			if (receivedMessage.equals("TRANSFER"))
				receiveFile();
		} catch (IOException e) {
			System.out.println("cannot write to sender");
			e.printStackTrace();
		}
	}
	
	private void receiveFile() throws IOException {
		System.out.println("Waiting for Filetransfer...");
		final int FILESIZE = 19812;
		int bytesread = 0;
		int current = 0;
		byte[] filebuffer = new byte[FILESIZE];
		InputStream reader = clientSocket.getInputStream();
		FileOutputStream writer = new FileOutputStream("a_filename");
		BufferedOutputStream bos = new BufferedOutputStream(writer);
		bytesread = reader.read(filebuffer, 0, filebuffer.length);
		current = bytesread;
		
		while(bytesread > -1) {
			bytesread = reader.read(filebuffer, 0, filebuffer.length);
			if (bytesread >= -1)
				current += bytesread;
		}
		
		bos.write(filebuffer, 0, current);
		bos.flush();
		bos.close();
	}

	private String receiveMessage() throws IOException {
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

	private void sendMessage(String reply) throws IOException {
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
