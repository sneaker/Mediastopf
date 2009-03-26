package ch.nomoresecrets.mediastopf.filesys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class DirectoryObserverTestHelper {

	private String testDirectory;

	public DirectoryObserverTestHelper(String directory) {
		testDirectory = directory + File.separator + "newlyCreated";
	}

	public void changeFile() {
		File f = new File(testDirectory);
		if (!f.exists())
			try {
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(testDirectory);
				new PrintStream(fos).println("Some Changes...");
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void toggleFileExistence() {
		File f = new File(testDirectory);
		if (f.exists())
			f.delete();
		else
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void createFile() {
		File f = new File(testDirectory + (int) (Math.random() * 1000));
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
