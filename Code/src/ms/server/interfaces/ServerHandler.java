package ms.server.interfaces;

import java.util.ArrayList;

import ms.server.domain.Auftrag;


public interface ServerHandler {
	/**
	 * get entries from a database
	 * 
	 * @return ArrayList
	 */
	public ArrayList<Auftrag> getDataBase();
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
}