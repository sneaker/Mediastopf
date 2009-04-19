package ms.client.networking;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;
import ms.client.Client;
import ms.server.Server;
import ms.server.log.Log;
import ms.server.networking.NetworkServer;

import org.apache.log4j.Logger;

public class ServerConnectionTest extends TestCase {
	private static final String TEMPDIR = System.getProperty("java.io.tmpdir") + File.separator + "msclienttest" + File.separator;
	private File folder;
	private ServerConnection connection;

	public void setUp() throws Exception {
		startServer();
		connection = new ServerConnection(Client.HOST, Client.PORT);
		
		makeDirs();
		generateFiles();
	}

	public void tearDown() throws Exception {
		delDir();
	}

	public void testSendFile() {
		String[] fileList = folder.list();
		for(String f: fileList) {
			try {
				connection.sendFile(folder + File.separator + f);
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
		String[] transferedList = folder.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains("_rec");
			}
		});
		
		assertEquals(fileList.length, transferedList.length);
		
		for(String f: fileList){
			File file = new File(folder + File.separator + f);
			File transfile = new File(folder + File.separator + f + "_rec");
			assertEquals(file.length(), transfile.length());
			assertEquals(file.getName(), transfile.getName().replace("_rec", ""));
		}
	}
	
	private void startServer() {
		loadLog();
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new NetworkServer(Client.PORT, Server.MAX_SERVER_THREADS));
	}

	private void loadLog() {
		Logger logger = Log.getLogger();
		logger.info("Starting network server...");
	}
	
	private void generateFiles() {
		for(int i=0; i < 100; i++) {
			File f = new File(folder + File.separator + "testfile" + (int)(Math.random()*10000));
			System.out.println(File.separator);
			System.out.println(f.getPath());
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
		folder = new File(TEMPDIR);
		folder.mkdirs();
	}
	
	private void delDir() {
		if(folder.isDirectory()) {
			File[] fileList = folder.listFiles();
			for(File f: fileList) {
				f.delete();
			}
		}
		folder.delete();
	}
}
