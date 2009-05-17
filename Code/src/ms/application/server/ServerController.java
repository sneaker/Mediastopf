package ms.application.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ms.domain.Auftrag;
import ms.utils.FileIO;
import ms.utils.log.server.ServerLog;
import ms.utils.server.database.SqlDbAdapter;

import org.apache.log4j.Logger;


/**
 * server classe
 * loading gui components and start server
 * 
 * @author david
 *
 */
public class ServerController {
	
	private static Logger logger = ServerLog.getLogger();

	public static ArrayList<Auftrag> getTaskList() {
		List<Auftrag> list = SqlDbAdapter.getOrderList();
		ArrayList<Auftrag> taskList = new ArrayList<Auftrag>();
		for(Auftrag a: list) {
			taskList.add(new Auftrag(a.getID()));
		}
		return taskList;
	}
	
	/**
	 * copying files
	 * 
	 * @param folder sourceforlder
	 * @param exportFolder destinationfolder
	 * @return true if copying succeed
	 */
	public static boolean copyFiles(File[] folder, File exportFolder) {
		boolean succeed = FileIO.copyFiles(folder, exportFolder);
		if(succeed) {
			logger.info("Filetransfer succeed");
		} else {
			logger.warn("Filetransfer failed");
		}
		return succeed;
	}
	
	/**
	 * write a file
	 * 
	 * @param file File
	 * @param content Filecontent to write
	 */
	public static void writeFile(File file, String content) {
		FileIO.write(file, content);
	}
}
