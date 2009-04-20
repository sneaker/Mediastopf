package ms.client.filesys;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DirectoryObserverTest extends TestCase {

	private static final String TEMPDIR = System.getProperty("java.io.tmpdir");
	
	private DirectoryObserver changeScanner;
	private UpdateDetector notifyTester;

	@Before
	public void setUp() {
		changeScanner = new DirectoryObserver(TEMPDIR);
		notifyTester = new UpdateDetector();
	}

	@After
	public void tearDown() throws Exception {
		File f = new File(TEMPDIR);
		File[] list = f.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return (pathname.getName().startsWith("newlyCreated"));
			}
		});
		for (int i = 0; i < list.length; i++)
			list[i].delete();
	}

//	@Test
//	public void testObserveMassiveDirectoryChange() {
//		DirectoryObserverTestHelper d = new DirectoryObserverTestHelper(TEMPDIR);
//		changeScanner.addObserver(notifyTester);
//		new Thread(changeScanner).start();
//
//		for (int i = 0; i < 50; i++)
//			d.createFile();
//
//		changeScanner.poll();
//		notifyTester.reset();
//		d.createFile("newlyCreated-1");
//		changeScanner.poll();
//		
//		//
//		//d.createFile("newlyCreated-2");
//		//d.createFile("newlyCreated-3");
//		
//		changeScanner.poll();
//		changeScanner.poll();
//		
//		assertTrue("Should detect filesystem changes", notifyTester.isUpdated);
//		
//		new File(TEMPDIR + File.separator + "subdir").delete();
//	}
	
	@Test
	public void testFalsePositives() {
		changeScanner.addObserver(notifyTester);
		new Thread (changeScanner).start();

		assertFalse("Should not find filesystem changes", notifyTester.isUpdated);
		changeScanner.poll();
		assertFalse("Still should not find filesystem changes", notifyTester.isUpdated);
	}
	
	@Test
	public void testObserveNewFile() {
		changeScanner.addObserver(notifyTester);
		new Thread (changeScanner).start();

		assertFalse("Should not find filesystem changes", notifyTester.isUpdated);

		new DirectoryObserverTestHelper(TEMPDIR).createFile("newlyCreated123");

		changeScanner.poll();

		assertTrue("Should detect filesystem changes", notifyTester.isUpdated);
		assertEquals("Should detect the right file", "newlyCreated123", notifyTester.changes.get(0).affectedFile.getName());
	}
	
	@Test
	public void testObserveModifiedFile() {
		changeScanner.addObserver(notifyTester);
		new Thread (changeScanner).start();

		new DirectoryObserverTestHelper(TEMPDIR).createFile("newlyCreated123");
		changeScanner.poll();
		
		new DirectoryObserverTestHelper(TEMPDIR).changeFile("newlyCreated123");
		
		changeScanner.poll();

		assertTrue("Should detect filesystem changes", notifyTester.isUpdated);
		assertEquals("Should detect the right file", "newlyCreated123", notifyTester.changes.get(0).affectedFile.getName());
		assertEquals("Should detect the right status", FileChange.MODIFIED, notifyTester.changes.get(0).status);
	}
	
	private class UpdateDetector implements Observer {
		public boolean isUpdated = false;
		public ArrayList<FileChange> changes = new ArrayList<FileChange>();
		
		public void update(Observable o, Object arg) {
			isUpdated = true;

			// Done this way because I don't know the correct way.
			if (arg != null && arg.getClass().toString().equals("class java.util.ArrayList"))
				changes = (ArrayList<FileChange>)arg;
			else
				fail("Someone called update() with the withoud a List of FileChanges");
		}
		
		public void reset(){
			isUpdated = false;
			changes = new ArrayList<FileChange>();
		}
	}
}
