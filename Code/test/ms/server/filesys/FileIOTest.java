package ms.server.filesys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ms.utils.filesys.FileIO;


public class FileIOTest {
	
	private final String tempDir = System.getProperty("java.io.tmpdir") + File.separator;
	private File src = new File(tempDir + "mstestsrc"), dest = new File(tempDir + "mstestdest");

	@Before
	public void setUp() throws Exception {
		makeDirs();
		generateFiles(src);
		generateFolders(src);
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

	private void generateFiles(File folder) {
		for(int i=0; i < 2; i++) {
			File f = new File(folder + File.separator + "testfile" + (int)(Math.random()*10000));
			try {
				f.createNewFile();
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}
	
	private void generateFolders(File folder) {
		for (int i = 0; i < 2; i++) {
			File f = new File(folder + File.separator + "folder" + (int)(Math.random()*10000));
			f.mkdirs();
			generateFiles(f);
		}
	}
	
	private void makeDirs() {
		delSrcDir();
		delDestDir();
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
				if(f.isDirectory()) {
					delDir(f);
				}
				f.delete();
			}
		}
		file.delete();
	}
}
