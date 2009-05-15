package ms.utils.networking.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

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

	SendeListe mediumlist = new SendeListe();
	
	public SendeListe getsendeliste()
	{
		return mediumlist;
	}

	public ImportMediumSender(String host, int port)
			throws UnknownHostException, IOException {
		super(host, port);
	}

	/**
	 * Hinzufügen eines Importmediums für den Versand
	 * 
	 * @param m
	 */
	public void addMediumForTransfer(ImportMedium m) {
		mediumlist.add(m);
	}

	@Override
	public void run() {
		while (true) {
			for (ImportMedium m : mediumlist.getList()) {
				try {
					sendImportMedium(m);
					mediumlist.remove(m);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Thread.yield();
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
		sendObject(m);
		disconnect();
	}
}
