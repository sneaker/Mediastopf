package ms.utils.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;

import ms.domain.ImportMedium;
import ms.domain.SendeListe;

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

	SendeListe mediumlist;

	public SendeListe getsendeliste() {

		return mediumlist;

	}

	public ImportMediumSender(String host, int port)
			throws UnknownHostException, IOException {
		super(host, port);
		mediumlist = new SendeListe();
	}

	/**
	 * Hinzufügen eines Importmediums für den Versand
	 * 
	 * @param m
	 */
	public void addMediumForTransfer(ImportMedium m) {

		mediumlist.add(m);

	}

	public synchronized void run() {
		while (true) {
			synchronized (mediumlist) {
				Iterator<ImportMedium> it = mediumlist.getList().iterator();
				while (it.hasNext()) {
					ImportMedium m = it.next();
					try {
						sendImportMedium(m);
					} catch (IOException e) {
						e.printStackTrace();
					}
					it.remove();
				}
			}
			Thread.yield();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
		sendMessage("TRANSFER");
		sendObject(m);
	}
}
