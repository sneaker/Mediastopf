package ms.client.observer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
		if (directoryChanged()) {
			setChanged();
			// FIXME: Remove arguments, not needed anymore
			notifyObservers(new ArrayList<FileChange>());
		}

		takeDirectorySnapshot();
	}

	/**
	 * Postcondition: Snapshot must be Sorted (for further comparison)
	 */
	private void takeDirectorySnapshot() {
		lastDirectorySnapshot = observedDirectory.listFiles();
		lastFilesizes = new ArrayList<Long>();
		for (int i = 0; i < lastDirectorySnapshot.length; i++) {
			lastFilesizes.add(i, lastDirectorySnapshot[i].length());
		}
		Arrays.sort(lastDirectorySnapshot);
	}

	/**
	 * Precondition: lastDirectorySnapshot is sorted
	 * @return
	 * @throws Exception 
	 */
	protected boolean directoryChanged() throws Exception {
		ArrayList<File> alt = new ArrayList<File>();
		ArrayList<File> neu = new ArrayList<File>();
		File[] neu_array = observedDirectory.listFiles();
		Arrays.sort(neu_array);
		for (File f : lastDirectorySnapshot)
			alt.add(f);
		for (File f : neu_array)
			neu.add(f);
		
		ListIterator<File> altIt = alt.listIterator();
		ListIterator<File> neuIt = neu.listIterator();
		
		int deletedFiles = 0;
		int newFiles = 0;

		if (!neuIt.hasNext())
			deletedFiles = alt.size();
		
		while (neuIt.hasNext()) {
			if (!altIt.hasNext()) {
				neuIt.next();
				newFiles++;
				continue;
			}

			int compare = neuIt.next().compareTo(altIt.next());
			if (compare < 0) {
				altIt.previous();
				newFiles++;
			} else if (compare > 0) {
				neuIt.previous();
				deletedFiles++;
			}
		}
		
		if (deletedFiles > 0)
			throw new Exception();
		
		return newFiles > 0;
	}

}

class FilesRemovedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Files have been deleted from the directory which should not be the case in normal usage.";
	}
	
}