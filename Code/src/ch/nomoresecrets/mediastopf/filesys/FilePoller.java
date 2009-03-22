package ch.nomoresecrets.mediastopf.filesys;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

class FilePoller extends Thread {

	private static final int POLLING_INTERVAL = 300;
	private File _observedDirectory;
	private File[] _lastDirectorySnapshot;
	private Observer _caller;

	FilePoller(Observer caller) {
		_observedDirectory = new File("/tmp");
		_caller = caller;
		takeDirectorySnapshot();
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				sleep(POLLING_INTERVAL);
				poll();
			} catch (InterruptedException e) {
			}
		}
	}

	private void poll() {
		if (directoryChanged(_observedDirectory.listFiles()))
			_caller.update(null, null);

		takeDirectorySnapshot();
	}

	private void takeDirectorySnapshot() {
		_lastDirectorySnapshot = _observedDirectory.listFiles();
	}

	private boolean directoryChanged(File[] newFileList) {
		for (int i = 0; i < _lastDirectorySnapshot.length; i++) {
			if (!_lastDirectorySnapshot[i].getName().equals(
					newFileList[i].getName()))
				return true;
		}
		return false;
	}

}
