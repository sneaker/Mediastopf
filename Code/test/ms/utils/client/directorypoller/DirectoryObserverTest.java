package ms.utils.client.directorypoller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ms.utils.client.directorypoller.DirectoryPoller;
import ms.utils.client.directorypoller.FilesRemovedException;
import ms.utils.log.client.ClientLog;

import org.junit.Before;
import org.junit.Test;

public class DirectoryObserverTest {

	protected DirectoryPoller changeScanner;
	private UpdateDetector notifyTester;
	private MockDirectory dir;

	@Before
	public void setUp() {
		org.apache.log4j.Logger logger = ClientLog.getLogger();
		dir = new MockDirectory();
		notifyTester = new UpdateDetector();
		changeScanner = new DirectoryPoller(dir);
		changeScanner.addObserver(notifyTester);
	}

	@Test
	public void testUpdate() throws Exception {
		dir.addMockFile();
		dir.makeAllFilesOlder(11);
		changeScanner.checkStatus();
		assertTrue("Update erwartet", notifyTester.isUpdated);
	}

	@Test
	public void testNoChangeMakesNoUpdate() throws Exception {
		changeScanner.checkStatus();
		assertFalse(notifyTester.isUpdated);
	}

	@Test
	public void testNoMoreNewFilesImpliesUpdate() throws Exception {
		dir.addMockFile();
		changeScanner.checkStatus();
		dir.makeAllFilesOlder(11);
		changeScanner.checkStatus();
		assertTrue(notifyTester.isUpdated);
	}

	@Test
	public void testDontUpdateBeforeTimeout () throws FilesRemovedException {
		dir.addMockFile();
		changeScanner.checkStatus();
		assertFalse(notifyTester.isUpdated);
	}
	
	private class MockFile extends File {

		private static final long serialVersionUID = 1L;
		private long aging_time = 0;

		public MockFile(String mockName) {
			super(System.getProperty("java.io.tmpdir") + File.separator
					+ "newlyCreated" + mockName);
		}

		@Override
		public long lastModified() {
			return System.currentTimeMillis() - aging_time;
		}

		public void makeOlder(int minutes) {
			aging_time = minutes * 60 * 1000;
		}

		@Override
		public long length() {
			return 42;
		}
	}

	/**
	 * Beinhaltet {@link MockFile}s und erlaubt, fiktive Änderungen am
	 * Dateisystem vorzunehmen, ohne dass das reale Dateisystem des
	 * Betriebssystems verändert wird. Grundzustand ist ein leeres Verzeichnis.
	 * 
	 * Undefiniertes Verhalten, wenn Methoden benutzt werden, die nicht
	 * implement sind.
	 */
	private class MockDirectory extends File {

		private static final long serialVersionUID = 1L;
		/**
		 * Ersatz für ein "echtes" Verzeichnis auf dem Dateisystem.
		 */
		ArrayList<MockFile> files = new ArrayList<MockFile>();

		/**
		 * Zeige im Hintergrund auf das Systemunabhängige Temp-Verzeichnis,
		 * damit nicht alle Methoden wie `exists()' überschrieben werden müssen.
		 */
		public MockDirectory() {
			super(System.getProperty("java.io.tmpdir"));
		}

		public void removeFiles(int count) {
			for (int i = 0; i < count; i++)
				if (!files.isEmpty())
					files.remove(0);
		}

		public void removeFile(int index) {
			files.remove(index);
		}

		@Override
		public boolean isDirectory() {
			return true;
		}

		@Override
		public File[] listFiles() {
			File[] f = new File[files.size()];
			files.toArray(f);
			return f;
		}

		public void addMockFile(String mockName) {
			files.add(new MockFile(mockName));
		}

		public void addMockFile() {
			addMockFile("mockFile" + (int) (Math.random() * 10000));
		}

		public void addMockFiles(int times) {
			for (int i = 0; i < times; i++)
				addMockFile();
		}

		public void makeAllFilesOlder(int age_minutes) {
			for (MockFile f : files)
				f.makeOlder(age_minutes);
		}
	}

	/**
	 * Observer für Testzwecke, der sich merkt, ob seine Update-Funktion
	 * aufgerufen wurde.
	 */
	private class UpdateDetector implements Observer {
		public boolean isUpdated = false;

		public void update(Observable o, Object arg) {
			isUpdated = true;
		}

		public void reset() {
			isUpdated = false;
		}
	}
}
