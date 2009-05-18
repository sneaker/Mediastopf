package ms.application.server;

import java.io.File;

import ms.utils.FileIO;
import ms.utils.log.server.ServerLog;

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
