package ms.client.observer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

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
public class DirectoryObserver extends Observable implements Runnable {

	private static final int POLLING_INTERVAL = 2000;
	protected File observedDirectory;
	protected ArrayList<File> lastDirectorySnapshot = new ArrayList<File>();

	/**
	 * Initialisiert den Observer mit dem aktuellen Verzeichnisinhalt.
	 * 
	 * @param directory
	 *            Pfad zum Verzeichnis, welches überwacht werden soll
	 */
	public DirectoryObserver(String directory) {
		this(new File(directory));
	}

	/**
	 * Alternativer Kontruktor, falls das Verzeichnis bereits als {link File}
	 * zur Verfügung steht.
	 * 
	 * @param directory
	 *            muss ein Verzeichnis sein, keine Datei
	 */
	public DirectoryObserver(File directory) {
		assert (directory.isDirectory());
		observedDirectory = directory;
		takeDirectorySnapshot();
	}

	public void run() {
		try {
			while (checkStatus()) {
				Thread.sleep(POLLING_INTERVAL);
			}
		} catch (InterruptedException e) {
			System.err.println("Warning: DirectoryObserver for "
					+ observedDirectory + " got interrupted");
		} catch (FilesRemovedException e) {
			// FIXME: Use Logger
			System.err.println(e.getMessage() + "\nObserved directory was"
					+ observedDirectory);
		}

	}

	/**
	 * @throws FilesRemovedException
	 *             wenn eine Datei gelöscht wurde und daher mit einer
	 *             Inkonsistenz auf dem Server gerechnet werden muss
	 */
	protected boolean checkStatus() throws FilesRemovedException {
		if (getDeletedFiles() > 0)
			throw new FilesRemovedException(getDeletedFiles(),
					observedDirectory);

		if (directoryChanged()) {
			setChanged();
			notifyObservers();
		}

		takeDirectorySnapshot();
		return hasChanged();
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

		return copyOfLastSnapshot.size();
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

/**
 * 
 * 
 */
class FilesRemovedException extends Exception {

	private static final long serialVersionUID = 1L;
	private int count;
	private File directory;

	public FilesRemovedException(int count, File directory) {
		this.count = count;
		this.directory = directory;
	}

	@Override
	public String getMessage() {
		return count + " files have been deleted from " + directory.getPath()
				+ "!";
	}

}