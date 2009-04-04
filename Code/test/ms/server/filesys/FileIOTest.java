package ms.server.filesys;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileIOTest {
	
	private static final String TEMPDIR = System.getProperty("java.io.tmpdir") + File.separator;
	private File src, dest;

	@Before
	public void setUp() throws Exception {
		makeDirs();
		generateRandomFiles();
	}

	@After
	public void tearDown() throws Exception {
		delSrcDir();
		delDestDir();
	}

	@Test
	public void testFileListCount() {
		File[] srclist = src.listFiles();
		FileIO.transfer(srclist, dest);
		File[] destlist = dest.listFiles();
		assertEquals(srclist.length, destlist.length);
	}

	@Test
	public void testFileList() {
		File[] srclist = src.listFiles();
		FileIO.transfer(srclist, dest);
		File[] destlist = dest.listFiles();
		for(int i=0; i < srclist.length; i++) {
			assertEquals(srclist[i].getName(), destlist[i].getName());
		}
	}
	
	private void generateRandomFiles() {
		for(int i=0; i < 10; i++) {
			File f = new File(src + File.separator + "testfile" + (int)(Math.random()*10000));
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void makeDirs() {
		src = new File(TEMPDIR + "mstestsrc");
		dest = new File(TEMPDIR + "mstestdest");
		src.mkdirs();
		dest.mkdirs();
	}
	
	private void delSrcDir() {
		delDir(src);
	}
	
	private void delDestDir() {
		delDir(dest);
	}
	
	private void delDir(File file) {
		if(file.isDirectory()) {
			File[] fileList = file.listFiles();
			for(File f: fileList) {
				f.delete();
			}
		}
		file.delete();
	}
}
