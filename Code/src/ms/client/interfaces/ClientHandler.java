package ms.client.interfaces;

import java.io.File;
import java.util.ArrayList;

import ms.logic.Task;

/**
 * client interface
 * 
 * @author david
 *
 */
public interface ClientHandler {
	
	/**
	 * start directory observer
	 * 
	 * @param folder to Observer
	 */
	public void observeDir(File folder);
	/**
	 * send files from folder
	 * 
	 * @param folder with files
	 */
	public void sendFiles(File folder);
	/**
	 * send objects
	 * 
	 * @param Object
	 */
	public void sendObject(Object o);
	/**
	 * get received objects
	 */
	public Object getObject();
	/**
	 * cancel running job
	 */
	public void cancelJob();
	/**
	 * get Tasks from Database
	 */
	public ArrayList<Task> getTaskList();
}
