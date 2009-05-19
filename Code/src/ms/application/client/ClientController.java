package ms.application.client;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import ms.domain.AuftragsListe;
import ms.domain.ImportMedium;
import ms.utils.AuftragslistenReceiver;
import ms.utils.client.directoryobserver.DirectoryObserver;
import ms.utils.log.client.ClientLog;
import ms.utils.networking.client.ImportMediumSender;

/**
 * client class
 * used to load gui components and connecting to server,
 * also included a directory observer, which automatically trigger "sendFiles"
 * 
 * @author david
 *
 */
public class ClientController {
	
	public static AuftragslistenReceiver auftragreceiver;
	public static ImportMediumSender mediumsender;
	
	public ClientController(AuftragslistenReceiver rec, ImportMediumSender send) {
		auftragreceiver = rec;
		mediumsender = send;
	}
	
	/**
	 * start directory observer
	 * 
	 * @param folder to observe
	 */	
	public static void observeDir(final File folder) {
		DirectoryObserver dirObserver = new DirectoryObserver(folder);
		dirObserver.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				ImportMedium medium = null;
				for(String filename : folder.list()) {
					medium = new ImportMedium();
					File f = new File(filename);
					medium.addItem(f);
				}
				addImportMedium(medium);
			}
		});
		new Thread(dirObserver).start();
		
		ClientLog.getLogger().info("Directory Observer started in " + folder);
	}
	
	/**
	 * sends whole objects over the network
	 * 
	 * @param o
	 */
	public static void addImportMedium(Object o) {
		mediumsender.addMediumForTransfer((ImportMedium) o);
	}
	
	/**
	 * get Tasks from Database
	 */
	
	public static AuftragsListe getTaskList() {
		return AuftragsListe.getInstance(null);
	}
}
