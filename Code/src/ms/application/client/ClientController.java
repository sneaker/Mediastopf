package ms.application.client;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import ms.domain.AuftragsListe;
import ms.domain.ImportMedium;
import ms.utils.ApplicationLauncher;
import ms.utils.AuftragslistenReceiver;
import ms.utils.client.directoryobserver.DirectoryObserver;
import ms.utils.log.client.ClientLog;
import ms.utils.networking.client.ImportMediumSender;

/**
 * Bietet der Benutzerschnittstelle Befehle an, die an die entsprechenden
 * Klassen in den unteren Schichten delegiert werden. Nach der
 * Initialisierungsphase werden Referenzen auf utils-Klassen hier verwaltet und
 * entsprechende Befehle aus der GUI nach unten delegiert.
 * 
 */
public class ClientController {

	/**
	 * Referenz für die GUI, wo die aktuelle Auftragsliste abgerufen werden
	 * kann.
	 */
	public static AuftragslistenReceiver auftragreceiver;

	/**
	 * Ermöglicht, die Dateien von abgeschlossenen Aufträgen auf den Server zu
	 * übermitteln.
	 */
	public static ImportMediumSender mediumsender;

	/**
	 * Nach der Initialisierungsphase können hier die Referenzen auf
	 * initialisierte Objekte hier übergeben werden.
	 * 
	 * @param rec
	 *            der Thread, der die aktuellen Aufträge abruft
	 * @param send
	 *            der Thread, der neue Dateien versendet
	 */
	public ClientController(AuftragslistenReceiver rec, ImportMediumSender send) {
		auftragreceiver = rec;
		mediumsender = send;
	}

	/**
	 * Starte eine neue Dateisystem-Überwachungsinstanz, welche die Dateien
	 * eines ImportMediums nach Abschluss des Importvorganges an den Server
	 * schickt.
	 * 
	 * @param folder
	 *            welcher überwacht werden soll (Importverzeichnis plus die
	 *            Nummer des Auftrages)
	 */
	public static void observeDir(final File folder) {
		DirectoryObserver dirObserver = new DirectoryObserver(folder);
		dirObserver.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				ImportMedium medium = null;
				for (String filename : folder.list()) {
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
	 * Fügt die Dateien eines abgeschlossenen Importvorganges in die Liste der
	 * zu übermittelnden Dateien, welche dann automatisch zum Server übertragen
	 * werden.
	 * 
	 * @param o
	 *            ein ImportMedium mit Dateien drin
	 */
	public static void addImportMedium(Object o) {
		mediumsender.addMediumForTransfer((ImportMedium) o);
	}

	/**
	 * Holt die aktuelle Auftragsliste vom Server bzw. liefert die aktuellste
	 * Liste, welche noch abgerufen werden konnte, falls der Server nicht
	 * erreichbar ist.
	 */
	public static AuftragsListe getTaskList() {
		return AuftragsListe.getInstance(null);
	}
	
	public static void openApplication(String app) {
		ApplicationLauncher.open(app);
	}
}
