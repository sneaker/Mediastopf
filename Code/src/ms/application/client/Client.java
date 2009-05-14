package ms.application.client;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.domain.Auftrag;
import ms.domain.ImportMedium;
import ms.ui.client.MainView;
import ms.ui.dialogs.MessageDialog;
import ms.utils.I18NManager;
import ms.utils.client.directoryobserver.DirectoryObserver;
import ms.utils.log.client.ClientLog;
import ms.utils.networking.client.ServerConnection;

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
	private static Logger logger = ClientLog.getLogger();
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
				ImportMedium medium = null;
				for(String filename : folder.list()) {
					medium = new ImportMedium();
					File f = new File(filename);
					medium.addItem(f);
				}
				sendObject(medium);
			}
		});
		new Thread(dirObserver).start();
		
		logger.info("Directory Observer started in " + folder);
	}
	
	/**
	 * sends whole objects over the network
	 * 
	 * @param o
	 */
	public static void sendObject(Object o) {
		client.sendObject(o);
	}
	
	/**
	 * get Tasks from Database
	 */
	public static ArrayList<Auftrag> getTaskList() {
		ArrayList<Auftrag> list = new ArrayList<Auftrag>();
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
