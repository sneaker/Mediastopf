package ms.client.observer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Updates its subscribers whenever a filesystem change occurred in the
 * specified directory.
 * 
 * @author MS
 */
public class DirectoryObserver extends Observable implements Runnable {

	private static final int POLLING_INTERVAL = 2000;
	private File _observedDirectory;
	private File[] _lastDirectorySnapshot;
	private ArrayList<Long> _lastFilesizes;

	/**
	 * @param directory
	 *            Chose the directory that should be observed.
	 */
	public DirectoryObserver(String directory) {
		_observedDirectory = new File(directory);
		takeDirectorySnapshot();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(POLLING_INTERVAL);
				poll();
			} catch (InterruptedException e) {
			}
		}
	}

	public void poll() {
		List<FileChange> fc = getDirectoryChanges();
		if (fc.size() > 0) {
			setChanged();
			notifyObservers(fc);
		}

		takeDirectorySnapshot();
	}

	private void takeDirectorySnapshot() {
		_lastDirectorySnapshot = _observedDirectory.listFiles();
		_lastFilesizes = new ArrayList<Long>(); 
		for (int i = 0; i < _lastDirectorySnapshot.length; i++) {
			_lastFilesizes.add(i, _lastDirectorySnapshot[i].length()); 
		}
	}

	/**
	 * Currently not scanning subdirectories!
	 */
	private List<FileChange> getDirectoryChanges() {
		File[] newFileList = _observedDirectory.listFiles();
		ArrayList<FileChange> fc = new ArrayList<FileChange>();
		boolean found = false;
		
		/*
		 * 3 Fälle: 1) a b 		bisherige Datei, keine Änderung (ggf modified?) 
		 * 			2) a ~b 	Datei gelöscht, bisher keine Aktion geplant
		 * 			3) ~a b		Neue Datei hinzugekommen -> melden
		 * 			4) ~a ~b	keine Änderung: kümmert uns nicht
		 */
		for (int i = 0; i < newFileList.length; i++) {
			found = false;
			for (int j = 0; j < _lastDirectorySnapshot.length; j++) {
				if (_lastDirectorySnapshot[j].getName().equalsIgnoreCase(newFileList[i].getName())) {
					found = true;

					if (_lastFilesizes.get(j) != (newFileList[i].length())) {
						fc.add(new FileChange(newFileList[i], FileChange.MODIFIED));
					}
				}
			}
			if (!found)
				fc.add(new FileChange(newFileList[i], FileChange.NEW));
		}
		return fc;
	}
}
