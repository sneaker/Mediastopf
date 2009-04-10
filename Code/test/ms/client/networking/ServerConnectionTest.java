package ms.client.networking;

import static org.junit.Assert.assertEquals;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ms.client.Client;
import ms.client.networking.ServerConnection;
import ms.server.Server;
import ms.server.log.Log;
import ms.server.networking.NetworkServer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerConnectionTest {
	
	private static final String TEMPDIR = System.getProperty("java.io.tmpdir") + "msclienttest" + File.separator;
	private File folder;
	private ServerConnection connection;

	@Before
	public void setUp() throws Exception {
		startServer();
		connection = new ServerConnection(Client.HOST, Client.PORT);
		
		makeDirs();
		generateFiles();
	}

	@After
	public void tearDown() throws Exception {
		delDir();
	}

	@Test
	public void testSendFile() {
		String[] fileList = folder.list();
		for(String f: fileList) {
			try {
				connection.sendFile(folder + File.separator + f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String[] transferedList = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.contains("_rec");
			}
		});
		
		assertEquals(fileList.length, transferedList.length);
		
		for(String f: fileList){
			File file = new File(folder + File.separator + f);
			File transfile = new File(folder + File.separator + f + "_rec");
			assertEquals(file.length(), transfile.length());
		}
		// TODO: check filenames etc.
	}
	
	private void startServer() {
		loadLog();
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new NetworkServer(Client.PORT, Server.MAX_SERVER_THREADS));
	}

	private void loadLog() {
		Log log = new Log();
		log.setLevel(Level.ALL);
		Logger logger = Log.getLogger();
		logger.info("Starting network server...");
	}
	
	private void generateFiles() {
		for(int i=0; i < 100; i++) {
			File f = new File(folder + File.separator + "testfile" + (int)(Math.random()*10000));
			try {
				f.createNewFile();
				generate_content(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void generate_content(File f) throws IOException {
		FileOutputStream fous = new FileOutputStream(f);
		BufferedOutputStream bos = new BufferedOutputStream(fous);
		int rand = (int) (Math.random()*10000);
		for(int i = 0; i < rand; i++){
			bos.write(i);
			bos.flush();
		}
		bos.close();
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
