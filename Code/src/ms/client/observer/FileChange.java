package ms.client.observer;

import java.io.File;

/**
 * Steht für eine Änderung an einer einzelnen Datei.
 * 
 * @author MS
 *
 */
public class FileChange {

	public final static int UNKNOWN = 0, NEW = 1, MODIFIED = 2, DELETED = 4;
	
	public File affectedFile;
	public int status = UNKNOWN;
	
	public FileChange(File affectedFile, int status) {
		this.affectedFile = affectedFile;
		this.status = status;
	}
	
	public boolean gotNewData() {
		return status == NEW || status == MODIFIED;
	}

}
