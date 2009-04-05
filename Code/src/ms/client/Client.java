package ms.client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.client.filesys.DirectoryObserver;
import ms.client.interfaces.ClientHandler;
import ms.client.log.Log;
import ms.client.logic.Task;
import ms.client.networking.NetworkClient;
import ms.client.networking.NetworkClientTester;
import ms.client.ui.MainView;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Client implements ClientHandler, Observer {
	
	private static final String HOST = "localhost";
	private static final int PORT = 1337;
	
	private Logger logger = Log.getLogger();
	private NetworkClient client;
	private NetworkClientTester testconnection;
	private String folder;
	
	
	public Client() {
		loadLog();
		loadUI();
		connectToServer();
	}
	
	private void connectToServer() {
		try {
			client = new NetworkClient(HOST, PORT);
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
	 * @param folder to Observe
	 */
	public void observeDir(final String folder) {
		this.folder = folder;
		DirectoryObserver dirObserver = new DirectoryObserver(folder);
		dirObserver.subscribe(this);
		dirObserver.start();
		
		logger.info("Directory Observer started in " + folder);
	}
	
	public void sendFiles(String folder) {
		File task = new File(folder);
		String[] fileList = task.list();
		for(String f: fileList) {
			try {
				client.sendFile(folder + File.separator + f);
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
		// TODO
		return null;
	}
	
	public ArrayList<Task> getTaskList() {
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			list = client.getTaskList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
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
				MainView mediastopf = new MainView(Client.this);
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
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		sendFiles(folder);
	}
}
