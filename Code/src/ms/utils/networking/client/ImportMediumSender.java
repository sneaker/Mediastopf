package ms.utils.networking.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Vector;

import ms.domain.ImportMedium;

/**
 * 
 * Thread der ständig läuft um fertige Importmedien zu versenden. Importmedien
 * können dem Thread mit
 * 
 * <code>
 * addMediumForTransfer(ImportMedium m);
 * </code>
 * 
 * hinzugefügt werden. Der ImportMediumSender verschickt diese dann. Dies muss
 * allerdings nicht sofort passieren.
 * 
 */

public class ImportMediumSender extends AbstractServerConnection implements
		Runnable {

	private Vector<ImportMedium> mediumlist;

	public ImportMediumSender(String host, int port)
			throws UnknownHostException, IOException {
		super(host, port);
		mediumlist = new Vector<ImportMedium>();
	}

	/**
	 * Hinzufügen eines Importmediums für den Versand
	 * 
	 * @param m
	 */
	public void addMediumForTransfer(ImportMedium m) {
		mediumlist.add(m);
	}

	public void run() {
		while (true) {
			Iterator<ImportMedium> it = mediumlist.iterator();
				while (it.hasNext()) {
					ImportMedium m = it.next();
					try {
						sendImportMedium(m);
					} catch (IOException e) {
						e.printStackTrace();
					}
					it.remove();
				}
			Thread.yield();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Verschickt Importmedium
	 * 
	 * @param m
	 * @throws IOException
	 */
	private void sendImportMedium(ImportMedium m) throws IOException {
		connect();
		sendMessage("TRANSFER");
		
		String reply;
		try {
			reply = receiveMessage();
			if (!reply.equals("TRANSFER READY"))
				throw new IOException();
			OutputStream __os = commSocket.getOutputStream();
			ObjectOutputStream __oos = new ObjectOutputStream(__os); 
			__oos.writeObject(m);
			
			sendMessage("END TRANSFER");
			if (!receiveMessage().equals("END OK"))
				throw new IOException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		disconnect();
	}
}
