package ms.client.filesys;

import java.io.File;
import java.io.FileFilter;
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

	@Test
	public void testObserveMassiveDirectoryChange() {
		DirectoryObserverTestHelper d = new DirectoryObserverTestHelper(TEMPDIR);
		changeScanner.addObserver(notifyTester);
		new Thread(changeScanner).start();

		for (int i = 0; i < 50; i++)
			d.createFile();

		changeScanner.poll();
		notifyTester.reset();
		d.createFile("newlyCreated-1");
		changeScanner.poll();
		
		//
		//d.createFile("newlyCreated-2");
		//d.createFile("newlyCreated-3");
		
		changeScanner.poll();
		changeScanner.poll();
		
		assertTrue("Should detect filesystem changes", notifyTester.isUpdated);
		
		new File(TEMPDIR + File.separator + "subdir").delete();
	}
	
	@Test
	public void testObserveDirectoryChange() {
		changeScanner.addObserver(notifyTester);
		new Thread (changeScanner).start();

		assertFalse("Should not find filesystem changes", notifyTester.isUpdated);

		new DirectoryObserverTestHelper(TEMPDIR).createFile();

		changeScanner.poll();

		assertTrue("Should detect filesystem changes", notifyTester.isUpdated);
	}
	
	private class UpdateDetector implements Observer {
		public boolean isUpdated = false;
		
		public void update(Observable o, Object arg) {
			isUpdated = true;
		}
		
		public void reset(){
			isUpdated = false;
		}
	}
}
