package ms.client.interfaces;

import java.util.ArrayList;

import ms.client.logic.Task;

public interface ClientHandler {
	
	/**
	 * start directory observer
	 * 
	 * @param folder to Observer
	 */
	public void observeDir(String folder);
	/**
	 * send files from folder
	 * 
	 * @param folder with files
	 */
	public void sendFiles(String folder);
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
