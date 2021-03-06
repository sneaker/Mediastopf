package ms.application.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import ms.domain.AuftragsListe;
import ms.domain.ImportMedium;
import ms.domain.MediaStopfListe;
import ms.utils.ApplicationLauncher;
import ms.utils.AuftragslistenReceiver;
import ms.utils.client.directorypoller.DirectoryPoller;
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
	public AuftragslistenReceiver auftragreceiver;

	/**
	 * Ermöglicht, die Dateien von abgeschlossenen Aufträgen auf den Server zu
	 * übermitteln.
	 */
	public ImportMediumSender mediumsender;

	public AuftragsListe auftragliste;

	public Hashtable<Integer, DirectoryPoller> dirPollers;
	
	private HashMap<Integer, ImportMedium> readylist;
	
	private static ClientController instance;

	/**
	 * Nach der Initialisierungsphase können hier die Referenzen auf
	 * initialisierte Objekte hier übergeben werden.
	 * 
	 * @param rec
	 *            der Thread, der die aktuellen Aufträge abruft
	 * @param send
	 *            der Thread, der neue Dateien versendet
	 */
	public ClientController(AuftragslistenReceiver rec, ImportMediumSender send, AuftragsListe aliste) {
		auftragreceiver = rec;
		mediumsender = send;
		auftragliste = aliste;
		readylist = new HashMap<Integer, ImportMedium>();
		dirPollers = new Hashtable<Integer, DirectoryPoller>();
		instance = this;
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
	public void pollDirForAuftrag(File folder, int auftrag_id) {
		//inner classes need final arguments
		final File _folder = folder;
		final int _auftrag_id = auftrag_id;
		DirectoryPoller dirPoller = new DirectoryPoller(folder);
		dirPoller.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				dirPollers.remove(_auftrag_id);
				generateImportMedium(_folder, _auftrag_id);
				auftragliste.getbyAuftragsNr(_auftrag_id).setStatus(2);
			}
		});
		
		dirPollers.put(auftrag_id, dirPoller);
		
		new Thread(dirPoller).start();

		ClientLog.getLogger().info(DirectoryPoller.class.getSimpleName() + " started in " + folder);
	}
	
	private void generateImportMedium(File folder, final int auftrag_id)
	{
		ImportMedium medium = null;
		try {
			medium = new ImportMedium(folder);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		medium.setId(auftrag_id);
		readylist.put(auftrag_id, medium);
	}

	/**
	 * Fügt die Dateien eines abgeschlossenen Importvorganges in die Liste der
	 * zu übermittelnden Dateien, welche dann automatisch zum Server übertragen
	 * werden.
	 * 
	 * @param o
	 *            ein ImportMedium mit Dateien drin
	 */
	public void addForSending(int auftrag_id) {
		ImportMedium m = readylist.get(auftrag_id);
		mediumsender.addMediumForTransfer(m);
		readylist.remove(auftrag_id);
		deleteFiles(new File(Integer.toString(auftrag_id)));
	}

	/**
	 * Holt die aktuelle Auftragsliste vom Server bzw. liefert die aktuellste
	 * Liste, welche noch abgerufen werden konnte, falls der Server nicht
	 * erreichbar ist.
	 */
	public MediaStopfListe getTaskList() {
		return auftragliste;
	}
	
	private static void deleteFiles(final File file) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				if (file.isDirectory()) {
					File[] fileList = file.listFiles();
					for (File f : fileList) {
						if (f.isDirectory()) {
							deleteFiles(f);
						}
						f.delete();
					}
				}
				file.delete();
			}
		});
		t.start();
	}

	public void openApplication(String app) {
		ApplicationLauncher.open(app);
	}
	
	public static ClientController getClientController()
	{
		return instance;
	}
}
