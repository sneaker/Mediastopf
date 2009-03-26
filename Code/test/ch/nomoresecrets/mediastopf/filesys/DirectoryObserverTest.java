package ch.nomoresecrets.mediastopf.filesys;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DirectoryObserverTest {

	private static final String TEMPDIR = System.getProperty("java.io.tmpdir");
	
	private DirectoryObserver changeScanner;
	private UpdateDetector notifyTester;

	class UpdateDetector implements Observer {
		public boolean hasBeenUpdated = false;
		
		UpdateDetector () {}

		public void update(Observable o, Object arg) {
			hasBeenUpdated = true;
		}
	}

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

	@Test
	public void observeDirectoryChange() {
		changeScanner.subscribe(notifyTester);
		changeScanner.start();

		assertFalse("Should not find filesystem changes", notifyTester.hasBeenUpdated);

		new DirectoryObserverTestHelper(TEMPDIR).createFile();

		changeScanner.poll();

		assertTrue("Should detect filesystem changes", notifyTester.hasBeenUpdated);
	}

	@Test
	public void testSubscriptionMechanism() {
		changeScanner.subscribe(notifyTester);
		changeScanner.unsubscribe(notifyTester);

		changeScanner.start();

		new DirectoryObserverTestHelper(TEMPDIR).createFile();
		assertFalse("Should not find filesystem changes", notifyTester.hasBeenUpdated);
	}
}
