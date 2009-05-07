package ms.client.observer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Updates its subscribers whenever a filesystem change occurred in the
 * specified directory.
 * 
 */
public class DirectoryObserver extends Observable implements Runnable {

	private static final int POLLING_INTERVAL = 2000;
	private File observedDirectory;
	private File[] lastDirectorySnapshot;
	private ArrayList<Long> lastFilesizes;

	/**
	 * @param directory
	 *            Chose the directory that should be observed.
	 */
	public DirectoryObserver(String directory) {
		observedDirectory = new File(directory);
		takeDirectorySnapshot();
	}

	/**
	 * Constructor intended for test-purposes only.
	 * 
	 * @param dir
	 */
	protected DirectoryObserver(File dir) {
		assert (dir.isDirectory());
		observedDirectory = dir;
		takeDirectorySnapshot();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(POLLING_INTERVAL);
				poll();
			} catch (InterruptedException e) {
			} catch (Exception e) {
				// FIXME: Use Logger
				System.err
						.println("Warning: Files have been deleted from directory "
								+ observedDirectory + ".");
			}
		}
	}

	public void poll() throws Exception {
		List<FileChange> fc = getDirectoryChanges();
		if (fc.size() > 0) {
			setChanged();
			notifyObservers(fc);
		}

		takeDirectorySnapshot();
	}

	private void takeDirectorySnapshot() {
		lastDirectorySnapshot = observedDirectory.listFiles();
		lastFilesizes = new ArrayList<Long>();
		for (int i = 0; i < lastDirectorySnapshot.length; i++) {
			lastFilesizes.add(i, lastDirectorySnapshot[i].length());
		}
	}

	/**
	 * Currently not scanning subdirectories!
	 */
	private List<FileChange> getDirectoryChanges() {
		File[] newFileList = observedDirectory.listFiles();
		ArrayList<FileChange> fc = new ArrayList<FileChange>();
		boolean found = false;

		/*
		 * 3 Fälle: 1) a b bisherige Datei, keine Änderung (ggf modified?) 2) a
		 * ~b Datei gelöscht, bisher keine Aktion geplant 3) ~a b Neue Datei
		 * hinzugekommen -> melden 4) ~a ~b keine Änderung: kümmert uns nicht
		 * 
		 * FIXME: Iterator benutzen (MS)
		 */
		for (int i = 0; i < newFileList.length; i++) {
			found = false;
			for (int j = 0; j < lastDirectorySnapshot.length; j++) {
				if (lastDirectorySnapshot[j].getName().equalsIgnoreCase(
						newFileList[i].getName())) {
					found = true;

					if (lastFilesizes.get(j) != (newFileList[i].length())) {
						fc.add(new FileChange(newFileList[i],
								FileChange.MODIFIED));
					}
				}
			}
			if (!found)
				fc.add(new FileChange(newFileList[i], FileChange.NEW));
		}
		return fc;
	}
}
