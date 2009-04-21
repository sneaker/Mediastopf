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
import ms.client.networking.ServerConnection;
import ms.client.ui.MainView;
import ms.client.ui.dialogs.MessageDialog;
import ms.client.utils.I18NManager;
import ms.log.Log;
import ms.logic.Task;

import org.apache.log4j.Logger;

/**
 * client class
 * used to load gui components and connecting to server,
 * also included a directory observer, which automatically trigger "sendFiles"
 * 
 * @author david
 *
 */
public class Client implements ClientHandler {
	
	public static final String HOST = "localhost";
	public static final int PORT = 1337;
	
	private I18NManager manager = I18NManager.getManager();
	private Logger logger = Log.getLogger();
	private ServerConnection client;
	
	
	public Client() {
		loadUI();
		connectToServer();
	}
	
	private void connectToServer() {
		try {
			client = new ServerConnection(HOST, PORT);
		} catch (UnknownHostException e) {
			logger.fatal("Unknown host");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			logger.info("Cannot connect to host");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * start directory observer
	 * 
	 * @param folder to observe
	 */
	public void observeDir(final File folder) {
		DirectoryObserver dirObserver = new DirectoryObserver(folder.toString());
		dirObserver.addObserver(new Observer() {
			public void update(Observable o, Object arg) {
				sendFiles(folder);
			}
		});
		new Thread(dirObserver).start();
		
		logger.info("Directory Observer started in " + folder);
	}
	
	/**
	 * send files from folder
	 * 
	 * @param folder with files
	 */
	public void sendFiles(File folder) {
		String[] fileList = folder.list();
		for(String f: fileList) {
			try {
				client.sendFile(folder + File.separator + f);
			} catch (IOException e) {
				logger.warn(f + " not sent");
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
			MessageDialog.info(manager.getString("Dialog.cantgettask"), manager.getString("Dialog.checkconnection"));
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

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
