package ch.nomoresecrets.mediastopf.filesys;

import java.io.File;
import java.io.IOException;

public class FileChangeTest extends Thread {

	private static final String testDirectory = "/tmp";

	enum Action {
		toggleExistence, change, multipleChanges
	};

	public void run() {
		try {
			sleep(500);
		} catch (InterruptedException e) {
			System.err.println("Filesystem-Changer interrupted.");
		}
	}

	public void run(Action action) throws IOException {
		run();
		switch (action) {
		case toggleExistence: {
			toggleFileExistence();break;
		}
		case change: {
			changeFile();break;
		}
		default:
			System.err.println("Action not yet implemented.");
		}
	}

	private void changeFile() throws IOException {
		File f = new File(testDirectory + "/newlyChanged");
		if (f.exists())
			f.delete();
		else
			f.createNewFile();
	}

	private void toggleFileExistence() throws IOException {
		File f = new File(testDirectory + "/newlyCreated");
		if (f.exists())
			f.delete();
		else
			f.createNewFile();
	}

}
