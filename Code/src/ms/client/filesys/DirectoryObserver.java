package ms.client.filesys;

import java.io.File;
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
		if (directoryChanged(_observedDirectory.listFiles())) {
			setChanged();
			notifyObservers(new FileChangeList());
		}

		takeDirectorySnapshot();
	}

	private void takeDirectorySnapshot() {
		_lastDirectorySnapshot = _observedDirectory.listFiles();
	}

	private boolean directoryChanged(File[] newFileList) {
		for (int i = 0; i < _lastDirectorySnapshot.length; i++) {
			if (!_lastDirectorySnapshot[i].getName().equalsIgnoreCase(newFileList[i].getName()))
				return true;
		}
		return false;
	}
}
