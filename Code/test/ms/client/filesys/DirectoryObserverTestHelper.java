package ms.client.filesys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class DirectoryObserverTestHelper {

	private String testDirectory;

	public DirectoryObserverTestHelper(String directory) {
		testDirectory = directory + File.separator; // + "newlyCreated";
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
		createFile("newly Created" + (Math.random() * 1000));
	}
	
	public void createFile(String name) {
		File f = new File(testDirectory + name);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeFile(String filename) {
		try {
			FileWriter f = new FileWriter(testDirectory + filename);
			f.write("fjkdslfjls\nfjkdsjfdsfjl");
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
