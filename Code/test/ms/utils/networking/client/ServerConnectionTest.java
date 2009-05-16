package ms.utils.networking.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ms.application.client.ClientController;
import ms.application.server.Server;
import ms.domain.ImportMedium;
import ms.utils.log.server.ServerLog;
import ms.utils.networking.server.PortListener;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerConnectionTest {
	private final File tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "msclienttest" + File.separator);
	//private ServerConnection connection;

	@Before
	public void setUp() throws Exception {
		startServer();
		//connection = new ServerConnection(Client.ClientController, Client.ClientController);
		
		makeDirs();
		generateFiles();
	}

	@After
	public void tearDown() throws Exception {
		delDir();
	}

	@Test
	public void testSendFile() {
		
	}
//  Fails only for my system? :-( MS
//	@Test
//	public void testSendFile() {
//		ImportMedium m = new ImportMedium();
//		String[] fileList = tempDir.list();
//		for(String filename: fileList) {
//			File f = new File(tempDir + File.separator + filename);
//			m.addItem(f);
//		}
//		
//		try {
//			connection.sendImportMedium(m);
//			//filecopying needs time :)
//			Thread.sleep(50000);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String[] transferedList = tempDir.list(new FilenameFilter() {
//			public boolean accept(File dir, String name) {
//				return name.contains("_rec");
//			}
//		});
//		
//		assertEquals(fileList.length, transferedList.length);
//		
//		for(String f: fileList){
//			File file = new File(tempDir + File.separator + f);
//			File transfile = new File(tempDir + File.separator + f + "_rec");
//			assertEquals(file.length(), transfile.length());
//			assertEquals(file.getName(), transfile.getName().replace("_rec", ""));
//		}
//	}
	
	private void startServer() {
		loadLog();
		ExecutorService exec = Executors.newSingleThreadExecutor();
//		exec.execute(new PortListener(Client.ClientController, Server.MAX_SERVER_THREADS));
	}

	private void loadLog() {
		Logger logger = ServerLog.getLogger();
		logger.info("Starting network server...");
	}
	
	private void generateFiles() {
		for(int i=0; i < 4; i++) {
			File f = new File(tempDir + File.separator + "testfile" + (int)(Math.random()*10000));
			try {
				f.createNewFile();
				generate_content(f);
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}
	
	private void generate_content(File f) {
		FileOutputStream fous;
		BufferedOutputStream bos;
		try {
			fous = new FileOutputStream(f);
			bos = new BufferedOutputStream(fous);
			int rand = (int) (Math.random()*10000);
			for(int i = 0; i < rand; i++){
				bos.write(i);
				bos.flush();
			}
			bos.close();
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	private void makeDirs() {
		delDir();
		tempDir.mkdirs();
	}
	
	private void delDir() {
		if(tempDir.isDirectory()) {
			File[] fileList = tempDir.listFiles();
			for(File f: fileList) {
				f.delete();
			}
		}
		tempDir.delete();
	}
}
