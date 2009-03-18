package ch.nomoresecrets.mediastopf.filesys;

import static org.junit.Assert.*;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WatchDirectoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	class FileListener$1 implements ActionListener {
		public void actionPerformed(final ActionEvent event) {
			System.out.println("Juheeee! Ne neue Datei :-)");
		}
	}

	@Test(timeout=10000)
	public void testSubscription() throws IOException, InterruptedException {
		WatchDirectory d = new WatchDirectory("/tmp");
		makeFilesystemChange ();
		//assertEquals (true, d.hasChanged());

		while (true) {
			for (int i = 0; i < 999999999; i++)
				System.out.print("");
		}
	}

	private void makeFilesystemChange() throws IOException {
		File f = new File("/tmp/testx");
		if (f.exists()) 
			f.delete();
		else
			f.createNewFile();
	}

}
