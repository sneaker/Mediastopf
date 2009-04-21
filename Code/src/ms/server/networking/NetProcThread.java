package ms.server.networking;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

import ms.networking.BasicNetIO;
import ms.server.database.DbAdapter;
import ms.server.domain.Auftrag;


public class NetProcThread extends BasicNetIO implements Runnable {

	public NetProcThread(Socket clientSocket) {
		commSocket = clientSocket;
	}

	public void run() {
		String receivedMessage = null;
		while (true) {
		
			try {
				receivedMessage = receiveMessage();
			} catch (IOException e) {
				logger.error("Cannot read from receiver");
				e.printStackTrace();
			}
			
			if (receivedMessage.equals("END")) {
				try {
					sendMessage("END OK");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}

			if (receivedMessage.equals("INFO")) {
				sendTaskList();
			}

			if (receivedMessage.equals("TRANSFER")) {
				try {
					sendMessage("TRANSFER READY");

					String namemsg = receiveMessage();
					sendMessage("TRANSFER NAME OK");

					String sizemsg = receiveMessage();
					int size = Integer.parseInt(sizemsg);
					sendMessage("TRANSFER SIZE OK");

					receiveFile(namemsg, size);
					sendMessage("ENDTRANSFER");
				} catch (IOException e) {
					logger.error("cannot write to sender");
					e.printStackTrace();
				}
			}
		}
	}

	private void sendTaskList() {
		List<Auftrag> lp = DbAdapter.getOrderList();

		for (Auftrag name : lp) {
			try {
				sendMessage(String.valueOf(name.getID()));
				if (!receiveMessage().equals("OK"))
					logger.fatal("Error in Network protocol");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			sendMessage("ENDINFO");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receiveFile(String name, int size) throws IOException {
		logger.info("Waiting for Filetransfer...");
		int bytesread = 0;
		byte[] filebuffer = new byte[size];
		InputStream reader = commSocket.getInputStream();
		FileOutputStream writer = new FileOutputStream(name + "_rec");
		BufferedOutputStream bos = new BufferedOutputStream(writer);
		while ((bytesread += reader.read(filebuffer, 0, filebuffer.length)) != -1) {
			if (bytesread >= size)
				break;
		}

		bos.write(filebuffer, 0, size);
		bos.flush();
		bos.close();
		writer.close();
	}
}
