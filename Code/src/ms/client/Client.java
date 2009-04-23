package ms.client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.client.networking.ServerConnection;
import ms.client.observer.DirectoryObserver;
import ms.client.ui.MainView;
import ms.client.utils.I18NManager;
import ms.log.Log;
import ms.logic.Task;
import ms.ui.dialogs.MessageDialog;

import org.apache.log4j.Logger;

/**
 * client class
 * used to load gui components and connecting to server,
 * also included a directory observer, which automatically trigger "sendFiles"
 * 
 * @author david
 *
 */
public class Client {
	
	public static final String HOST = "localhost";
	public static final int PORT = 1337;
	
	private static I18NManager manager = I18NManager.getManager();
	private static Logger logger = Log.getLogger();
	private static ServerConnection client;
	
	
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
	public static void observeDir(final File folder) {
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
	public static void sendFiles(File folder) {
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
	 * get Tasks from Database
	 */
	public static ArrayList<Task> getTaskList() {
		ArrayList<Task> list = new ArrayList<Task>();
		try {
			list = client.getTaskList();
		} catch (IOException e) {
			logger.fatal("Can't get Tasks");
			MessageDialog.info(manager.getString("Dialog.cantgettask"), manager.getString("Dialog.checkconnection"));
		}
		return list;
	}
	
	private void loadUI() {
		setLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainView mediastopf = new MainView();
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
