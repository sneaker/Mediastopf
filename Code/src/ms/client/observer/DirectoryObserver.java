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
	private ArrayList<File> lastDirectorySnapshot;
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
				System.err.println(e.getMessage() + " Directory="
						+ observedDirectory);
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
	 * Put the current contents of the observed directory into a sorted list.
	 * 
	 * Postcondition: Snapshot must be Sorted (for further comparison)
	 */
	private void takeDirectorySnapshot() {
		ArrayList<File> currentFiles = getSortedDirectorySnapshot();
		for (File f : currentFiles)
			lastDirectorySnapshot.add(f);

		takeFilesizeSnapshot();
	}

	private void takeFilesizeSnapshot() {
		lastFilesizes = new ArrayList<Long>();
		for (int i = 0; i < lastDirectorySnapshot.size(); i++)
			lastFilesizes.add(i, lastDirectorySnapshot.get(i).length());
	}

	/**
	 * Precondition: lastDirectorySnapshot is sorted
	 * 
	 * @return
	 * @throws Exception
	 */
	protected boolean directoryChanged() throws FilesRemovedException {
		ArrayList<File> currentDirectorySnapshot = getSortedDirectorySnapshot();

		ListIterator<File> altIt = lastDirectorySnapshot.listIterator();
		ListIterator<File> neuIt = currentDirectorySnapshot.listIterator();

		int deletedFiles = 0;
		int newFiles = 0;

		if (!neuIt.hasNext())
			deletedFiles = lastDirectorySnapshot.size();

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
			throw new FilesRemovedException();

		return newFiles > 0;
	}

	protected boolean testme () {
		ArrayList<File> currentDirectorySnapshot = getSortedDirectorySnapshot();
		if (currentDirectorySnapshot.containsAll(lastDirectorySnapshot) && lastDirectorySnapshot.containsAll(currentDirectorySnapshot))
			return true;
		return false;
	}
	
	protected int getDeletedFiles() {
		ArrayList<File> currentDirectorySnapshot = getSortedDirectorySnapshot();

		ListIterator<File> altIt = lastDirectorySnapshot.listIterator();
		ListIterator<File> neuIt = currentDirectorySnapshot.listIterator();

		int deletedFiles = 0;

		if (!neuIt.hasNext())
			return lastDirectorySnapshot.size();

		while (neuIt.hasNext()) {
			if (!altIt.hasNext()) {
				neuIt.next();
				continue;
			}

			int compare = neuIt.next().compareTo(altIt.next());
			if (compare < 0) {
				altIt.previous(); //nötig
			} else if (compare > 0) {
				neuIt.previous();
				deletedFiles++;
			}
		}

		return deletedFiles;
	}
	
	private ArrayList<File> getSortedDirectorySnapshot() {
		File[] neu_array = observedDirectory.listFiles();
		ArrayList<File> resultat = new ArrayList<File>();
		Arrays.sort(neu_array);
		for (File f : neu_array)
			resultat.add(f);
		return resultat;
	}

}

class FilesRemovedException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Files have been deleted from the directory which should not be the case in normal usage.";
	}

}