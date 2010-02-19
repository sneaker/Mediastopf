package ms;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import ms.domain.ImportMedium;
import ms.utils.networking.client.ImportMediumSender;
import ms.utils.networking.server.NoDestinationSpecifiedExecpetion;
import ms.utils.networking.server.PortListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

public class ImportMediumTest {

	ImportMedium m;
	String tempDir = System.getProperty("java.io.tmpdir") + File.separator;
	String from = tempDir + "from" + File.separator;
	String to = tempDir + "to" + File.separator;

	@Before
	public void setUp() throws Exception {
		File _from = new File(from);
		if (!_from.exists())
			_from.mkdirs();
		
		createFiles(_from);
		
		File _to = new File(to);
		if (!_to.exists())
			_to.mkdirs();

		m = new ImportMedium(from);
	}

	private void createFiles(File from2) {
		for (int i = 1; i < 10; i++) {
			File f = new File(from2 + File.separator + "randomfile"+ i);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				bos.write(getRandomFileContent());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] getRandomFileContent() {
		Integer i = new Random().nextInt(Integer.MAX_VALUE / 1000);
		ByteArrayBuffer b = new ByteArrayBuffer(i);
		return b.getRawData();
	}

	@After
	public void tearDown() {
		for(File f: new File(from).listFiles()) {
			f.delete();
		}
		new File(from).delete();
		for(File t: new File(to).listFiles()) {
			t.delete();
		}
		new File(to).delete();
	}
	
	@Test
	public void nomediumtest() {
		try {
			@SuppressWarnings("unused")
			ImportMedium none = new ImportMedium("");
			fail();
		} catch (FileNotFoundException e) {
		}
		
	}

	@Test
	public void normalTransferTest() {
		try {
			try {
				m.setLocation(to);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			m.saveContent();
		} catch (NoDestinationSpecifiedExecpetion e) {
			e.printStackTrace();
		}
		checkFromtoTo();
	}

	@Test
	public void NetworkTransferTest() {
		ArrayList<ImportMedium> cache = new ArrayList<ImportMedium>();
		int __port = 31337;
		try {
			new Thread(new PortListener(__port, 2, cache)).start();
			ImportMediumSender s = new ImportMediumSender("127.0.0.1", __port);
			s.addMediumForTransfer(m);
			new Thread(s).start();
			Thread.sleep(2000);
			while (cache.isEmpty())
				;
			assertTrue(!cache.isEmpty());
			
			for(ImportMedium m: cache) {
				m.setLocation(to);
				try {
					m.saveContent();
				} catch (NoDestinationSpecifiedExecpetion e) {
					e.printStackTrace();
				}
			}
			checkFromtoTo();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void checkFromtoTo() {
		File fromdir = new File(from);
		ArrayList<Long> fromhash = new ArrayList<Long>();
		File todir = new File(to);
		ArrayList<Long> tohash = new ArrayList<Long>();
		
		for(File fromfile : fromdir.listFiles()) {
			fromhash.add(fromfile.length());
		}
		
		for(File tofile : todir.listFiles()) {
			tohash.add(tofile.length());
		}
		assertArrayEquals(fromhash.toArray(), tohash.toArray());
	}
}
