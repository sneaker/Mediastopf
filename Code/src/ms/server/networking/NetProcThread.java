package ms.server.networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ms.common.domain.ImportMedium;
import ms.common.networking.BasicNetIO;
import ms.server.database.DbAdapter;
import ms.server.domain.ServerAuftrag;

public class NetProcThread extends BasicNetIO implements Runnable {

	public NetProcThread(Socket clientSocket) {
		commSocket = clientSocket;
	}

	public void run() {
		String receivedMessage = null;
		while (true) {
			
			try {
				receivedMessage = receiveMessage();
				if(receivedMessage.equals("END"))
					return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (receivedMessage.equals("INFO")) {
				sendTaskList();
				return;
			}

			if (receivedMessage.equals("TRANSFER")) {
				ImportMedium m = (ImportMedium) receiveObject();
				extractFiles(m);
				return;
			}
		}
	}

	private void extractFiles(ImportMedium m) {
		ArrayList<File> files = m.getItemsbyFile();
		for(File source : files) {
			File destination = new File(source.getAbsoluteFile().toString() + "_rec");
			try {
				InputStream in = new FileInputStream(source);
				OutputStream out = new FileOutputStream(destination);
				byte[] buf = new byte[1024];
				int len;
				while((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				System.out.println("File " + source.getAbsolutePath().toString() + " copied to " + destination.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private void sendTaskList() {
		List<ServerAuftrag> lp = DbAdapter.getOrderList();
		sendObject(lp);
	}
}
