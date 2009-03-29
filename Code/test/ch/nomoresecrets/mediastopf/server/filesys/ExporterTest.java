package ch.nomoresecrets.mediastopf.server.filesys;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExporterTest {
	
	private static final String TEMPDIR = System.getProperty("java.io.tmpdir") + File.separator;
	private File src, dest;

	@Before
	public void setUp() throws Exception {
		makeDirs();
		generateRandomFiles();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFileListCount() {
		File[] srclist = src.listFiles();
		Exporter.exportTo(srclist, dest);
		File[] destlist = dest.listFiles();
		assertEquals(srclist.length, destlist.length);
	}

	@Test
	public void testFileList() {
		File[] srclist = src.listFiles();
		Exporter.exportTo(srclist, dest);
		File[] destlist = dest.listFiles();
		for(int i=0; i < srclist.length; i++) {
			assertEquals(srclist[i].getName(), destlist[i].getName());
		}
	}
	
	private void generateRandomFiles() {
		for(int i=0; i < 10; i++) {
			File f = new File(src + File.separator + "testfile" + (int)(Math.random()*1000));
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void makeDirs() {
		src = new File(TEMPDIR + "mediastopftestsrc");
		dest = new File(TEMPDIR + "mediastopftestdest");
		src.mkdirs();
		dest.mkdirs();
	}
}
