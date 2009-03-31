package ch.nomoresecrets.mediastopf.filesys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class DirectoryObserverTestHelper {

	private static String testDirectory;

	public DirectoryObserverTestHelper(String directory) {
		testDirectory = directory;
	}

	public void changeFile() throws IOException {
		File f = new File(testDirectory + "/newlyCreated");
		FileOutputStream fos;

		if (!f.exists())
			f.createNewFile();

		fos = new FileOutputStream(testDirectory + "/newlyChanged");
		new PrintStream(fos).println("Einige Änderungen...");
		fos.close();
	}

	public void toggleFileExistence() throws IOException {
		File f = new File(testDirectory + "/newlyCreated");
		if (f.exists())
			f.delete();
		else
			f.createNewFile();
	}

	public void createFile() throws IOException {
		File f = new File(testDirectory + "/newlyCreated"
				+ (int) (Math.random() * 1000));
		f.createNewFile();
	}

}
