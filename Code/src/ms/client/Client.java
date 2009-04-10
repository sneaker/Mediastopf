package ms.client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.client.filesys.DirectoryObserver;
import ms.client.interfaces.ClientHandler;
import ms.client.log.Log;
import ms.client.logic.Task;
import ms.client.networking.ServerConnection;
import ms.client.ui.MainView;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Client implements ClientHandler {
	
	public static final String HOST = "localhost";
	public static final int PORT = 1337;
	
	private Logger logger = Log.getLogger();
	private ServerConnection client;
	
	
	public Client() {
		initLog();
		loadUI();
		connectToServer();
	}
	
	private void connectToServer() {
		try {
			client = new ServerConnection(HOST, PORT);
		} catch (UnknownHostException e) {
			logger.fatal("Unknown host");
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
		DirectoryObserver dirObserver = new DirectoryObserver(folder);
		dirObserver.subscribe(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				sendFiles(folder);
			}
		});
		dirObserver.start();
		
		logger.info("Directory Observer started in " + folder);
	}
	
	/**
	 * send files from folder
	 * 
	 * @param folder with files
	 */
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
	
	/**
	 * get Tasks from Database
	 */
	public ArrayList<Task> getTaskList() {
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			list = client.getTaskList();
		} catch (IOException e) {
			logger.fatal("Can't get Tasks");
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

	private void initLog() {
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
}
