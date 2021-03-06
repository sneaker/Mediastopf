package ms.utils.client.directorypoller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import ms.utils.log.Log;

import org.apache.log4j.Logger;

/**
 * Teilt seinen SubscriberInnen mit, falls im überwachten Datei-Verzeichnis neue
 * Dateien hinzugekommen sind. Falls Dateien gelöscht wurden wird gewarnt, damit
 * entsprechende Aktualisierungs-Massnahmen getroffen werden können.
 * 
 * Benutzung: Soll zum Beispiel das Temporäre Verzeichnis auf Änderungen
 * überwacht werden, kann die Klasse folgendermassen verwendet werden:
 * 
 * <code>
 * DirectoryObserver o = new DirectoryObserver("/tmp");
 * changeScanner.addObserver(
 * 		new Observer () { 
 * 			public void update (Observable o, Object arg) { 
 * 				// You action here
 * 			});
 * new Thread(o).start();
 * </code>
 */
public class DirectoryPoller extends Observable implements Runnable {

	private static final int POLLING_INTERVAL = 2000;
	private Logger logger = Log.getLogger();
	private File observedDirectory;
	private ArrayList<File> lastDirectorySnapshot = new ArrayList<File>();
	/**
	 * Time in seconds this thread waits for new changes until he calls update()
	 * on his subscribers.
	 */
	public int updateTimeout = 120;

	/**
	 * Initialisiert den Observer mit dem aktuellen Verzeichnisinhalt.
	 * 
	 * @param directory
	 *            Pfad zum Verzeichnis, welches überwacht werden soll
	 */
	public DirectoryPoller(String directory) {
		this(new File(directory));
	}

	/**
	 * Alternativer Kontruktor, falls das Verzeichnis bereits als {link File}
	 * zur Verfügung steht.
	 * 
	 * @param directory
	 *            muss ein Verzeichnis sein, keine Datei
	 */
	public DirectoryPoller(File directory) {
		assert (directory.isDirectory());
		observedDirectory = directory;
		takeDirectorySnapshot();
	}

	public void run() {
		try {
			while (!checkStatus()) {
				Thread.sleep(POLLING_INTERVAL);
				logger.info(getClass().getSimpleName() + ": still observing "
						+ observedDirectory.getAbsolutePath());
			}
		} catch (InterruptedException e) {
			logger.warn("Warning: " + getClass().getSimpleName() + " for "
					+ observedDirectory + " got interrupted");
		} catch (FilesRemovedException e) {
			logger.warn(e.getMessage() + "\nObserved directory was "
					+ observedDirectory);
		}
	}

	/**
	 * Prüft, ob im überwachten Verzeichnis seit einem gewissen Zeitintervall
	 * keine Änderung mehr aufgetreten ist. Falls Dateien neu aus dem
	 * Verzeichnis gelöscht wurden, wird eine Exception geworfen, da dies unter
	 * Umständen zu einer Dateninkonsistenz führen kann, wenn jemand Dateien
	 * manuell aus dem Auftragsverzeichnis löscht.
	 * 
	 * @throws FilesRemovedException
	 *             wenn eine Datei gelöscht wurde und daher mit einer
	 *             Inkonsistenz auf dem Server gerechnet werden muss
	 * @return true wenn der Importvorgang noch läuft und weitere Dateien
	 *         hinzugekommen sind
	 * @return false wenn seit einem bestimmten Intervall keine Änderung
	 *         aufgetreten ist.
	 */
	protected boolean checkStatus() throws FilesRemovedException {
		if (!recentChange() && observedDirectory.listFiles().length > 0) {
			logger.info(getClass().getSimpleName() + ": finished");
			setChanged();
			notifyObservers();
			return true;
		}

		takeDirectorySnapshot();
		logger.info(getClass().getSimpleName() + "nothing changed");
		return false;
	}

	private boolean recentChange() {
		logger.info(getClass().getSimpleName() + ": Autocommit in "
					    + getRemainingTime()
					    + " seconds.");
		return getLastModifyDate() > System.currentTimeMillis() - updateTimeout
				 * 1000;
	}

	public int getRemainingTime() {
		int result = (int) ((updateTimeout * 1000 - (System.currentTimeMillis() - getLastModifyDate())) / 1000);
		return (result > 0 ? result : 0);
	}

	/**
	 * Put the current contents of the observed directory into a sorted list.
	 */
	private void takeDirectorySnapshot() {
		lastDirectorySnapshot = getSortedDirectorySnapshot();
	}

	/**
	 * Prüft, ob eine neue Datei im überwachten Verzeichnis erstellt wurde.
	 * 
	 * @return den Status, ob seit der letzten Kontrolle eine neue Datei
	 *         hinzugekommen ist.
	 */
	protected boolean directoryChanged() {
		ArrayList<File> currentDirectorySnapshot = getSortedDirectorySnapshot();

		if (currentDirectorySnapshot.containsAll(lastDirectorySnapshot)
				&& lastDirectorySnapshot.containsAll(currentDirectorySnapshot))
			return false;

		return true;
	}

	/**
	 * Ermittelt, wie viele Dateien seit der letzten Kontrolle des überwachten
	 * Verzeichnisses gelöscht wurden.
	 * 
	 * @return Anzahl der gelöschten Dateien seit letzter Kontrolle
	 */
	protected int getDeletedFiles() {
		ArrayList<File> copyOfLastSnapshot = new ArrayList<File>(
				lastDirectorySnapshot);
		copyOfLastSnapshot.removeAll(getSortedDirectorySnapshot());

		logger.info(getClass().getSimpleName() + ": " + copyOfLastSnapshot.size());
		return 0;
	}

	private long getLastModifyDate() {
		long mostRecent = 0;
		for (File f : observedDirectory.listFiles()) {
			if (f.lastModified() > mostRecent)
				mostRecent = f.lastModified();
		}
		return mostRecent;
	}

	/**
	 * Liefert analog zu directory.listFiles() statt einem Array eine ArrayList
	 * und sortiert diese, damit sie effizient mit einer anderen Liste
	 * verglichen werden kann. <br />
	 * Der Umweg über die ArrayList wurde gewählt, da diese bequeme
	 * Vergleichsoperationen (z.B. für {@link getDeletedFiles}) anbietet.
	 * 
	 * @return
	 */
	private ArrayList<File> getSortedDirectorySnapshot() {
		File[] neu_array = observedDirectory.listFiles();
		ArrayList<File> resultat = new ArrayList<File>();
		Arrays.sort(neu_array);
		for (File f : neu_array)
			resultat.add(f);
		return resultat;
	}
}
