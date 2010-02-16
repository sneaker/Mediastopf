package ms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import ms.domain.ImportMedium;
import ms.utils.networking.client.ImportMediumSender;
import ms.utils.networking.server.PortListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImportMediumTest {

	ImportMedium m;
	private final String tempDir = System.getProperty("java.io.tmpdir") + File.separator;
	String from = "/home/thomas/movies/1";
	String to = "/home/thomas/movies/1_1";
	File f;
	
	@Before
	public void setUp() throws Exception {
		m = new ImportMedium(from);
		f = new File(to);
		f.mkdirs();
	}
	
	@After
	public void tearDown() {
		for (File file : f.listFiles()) {
			file.delete();
		}
		f.delete();
	}
	
	@Test
	public void normalTransferTest() {
		m.saveContent(to);
		assertEquals(new File(from).list().length, f.list().length);
	}
	
	@Test
	public void NetworkTransferTest() {
		int __port = 31337;
		try {
			new Thread(new PortListener(__port, 2)).start();
			ImportMediumSender s = new ImportMediumSender("127.0.0.1", __port);
			s.addMediumForTransfer(m);
			new Thread(s).start();
			Thread.sleep(2000);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(f.exists());
		assertEquals(new File(from).list().length, f.list().length);
	}
}
