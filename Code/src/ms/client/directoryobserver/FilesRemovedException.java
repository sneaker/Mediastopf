package ms.client.directoryobserver;

import java.io.File;

/**
 * Weist auf unerwartete Veränderungen in einem Verzeichnis hin. Üblicherweise
 * werden während des Importvorganges nur neue Dateien geschrieben. Wird eine
 * Datei gelöscht, kann es sein, dass dies zu unvorhersehbarem Verhalten führt.
 */
public class FilesRemovedException extends Exception {

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