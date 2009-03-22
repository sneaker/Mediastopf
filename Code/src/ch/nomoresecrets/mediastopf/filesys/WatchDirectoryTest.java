package ch.nomoresecrets.mediastopf.filesys;

import static org.junit.Assert.*;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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

	class FileListener$1 implements Observer {
		public void update(Observable who, Object arg1) {
			System.out.println("Yeah, received update!");
		}
	}

	@Test(timeout = 10000)
	public void testSubscription() throws IOException, InterruptedException {
		// WatchDirectory d = new WatchDirectory("/tmp");
		// observeDirectoryChange ();
		// //assertEquals (true, d.hasChanged());
		//
		// while (true) {
		// for (int i = 0; i < 999999999; i++)
		// System.out.print("");
		// }
	}

	@Test
	public void observeDirectoryChange() throws IOException {
		FilePoller f = new FilePoller (new Observer() {
			public void update(Observable o, Object arg) {
				System.out
						.println("Haha, ich wurde über das Update informiert :-)))");
			}
		});
		//assertFalse("Should not find filesystem changes", f.hasChanged());
		new FileChangeTest().run(FileChangeTest.Action.toggleExistence);
		//while (true) {
			for (int i = 0; i < 999999999; i++)
				System.out.print("");
		//}
		//assertTrue("Add/Remove of file not detected", f.hasChanged());
	}

}


/**
@Test
public void observeDirectoryChange() throws IOException {
	WatchDirectory w = new WatchDirectory("/tmp");
	w.addObserver(new Observer() {
		public void update(Observable o, Object arg) {
			System.out
					.println("Haha, ich wurde über das Update informiert :-)))");
		}
	});
	assertFalse("Should not find filesystem changes", w.hasChanged());
	new FileChangeTest().run(FileChangeTest.Action.toggleExistence);
	//while (true) {
		for (int i = 0; i < 999999999; i++)
			System.out.print("");
	//}
	assertTrue("Add/Remove of file not detected", w.hasChanged());
}
*/