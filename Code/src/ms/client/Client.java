package ms.client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ms.client.filesys.DirectoryObserver;
import ms.client.interfaces.ClientHandler;
import ms.client.log.Log;
import ms.client.networking.NetworkClient;
import ms.client.networking.NetworkClientTester;
import ms.client.ui.MediaStopf;
import ms.client.ui.SplashScreen;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Client implements ClientHandler {
	
	private static final String SPLASHIMAGE = MediaStopf.UIIMAGELOCATION + "splash.jpg";
	private static final String HOST = "localhost";
	private static final int PORT = 1337;
	
	private Logger logger = Log.getLogger();
	private NetworkClient connection;
	private NetworkClientTester testconnection;
	
	
	public Client() {
		loadLog();
		loadUI();
		connectToServer();
	}
	
	private void connectToServer() {
		try {
			connection = new NetworkClient(HOST, PORT);
			testconnection = new NetworkClientTester(HOST, PORT);
			ExecutorService exec = Executors.newSingleThreadExecutor();
			exec.execute(testconnection);
		} catch (UnknownHostException e) {
			logger.fatal("Unknow host");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("Cannot connect to host");
			e.printStackTrace();
		}
	}
	
	/**
	 * start directory observer
	 * 
	 * @param folder to Observer
	 */
	public void observeDir(final String folder) {
		DirectoryObserver dirObserver = new DirectoryObserver(folder);
		dirObserver.subscribe(new Observer() {
			public void update(Observable o, Object arg) {
				sendFiles(folder);
			}
		});
		dirObserver.start();
		
		logger.info("Directory Observer started in " + folder);
	}
	
	public void sendFiles(String folder) {
		File task = new File(folder);
		String[] fileList = task.list();
		for(String f: fileList) {
			try {
				connection.sendFile(folder + File.separator + f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * send objects
	 * 
	 * @param Object
	 */
	public void sendObject(Object o) {
		//TODO
	}
	
	/**
	 * get received objects
	 */
	public Object getObject() {
		//TODO
		return null;
	}
	
	/**
	 * cancel running job
	 */
	public void cancelJob() {
		//TODO
	}
	
	private void loadUI() {
		setLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MediaStopf mediastopf = new MediaStopf(Client.this);
				if (StartClient.DEBUG) {
					mediastopf.setTitle(MediaStopf.PROGRAM + " - Debug");
				} else {
					new SplashScreen(SPLASHIMAGE);
				}
				mediastopf.setVisible(true);
			}
		});
	}

	private void loadLog() {
		Log log = new Log();
		log.setLevel(Level.ALL);
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}
}
