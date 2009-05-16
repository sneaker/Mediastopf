package ms.utils.log.server;

import java.io.ByteArrayOutputStream;

import ms.ui.server.ServerConstants;
import ms.utils.log.Log;

import org.apache.log4j.Logger;

/**
 * Apache log4j Logger
 * three different logtypes:
 * - show in console
 * - write to a file (daily logging)
 * - put log information to a outputstream
 * 
 * @author david
 *
 */
public class ServerLog extends Log {
	
	public static ServerLog log = new ServerLog();
	
	private ServerLog() {
		logger = Logger.getLogger(ServerLog.class);
		bos = new ByteArrayOutputStream();
		
		initLogger(ServerConstants.class);
	}
	
	/**
	 * get logger
	 * 
	 * @return Logger
	 */
	public static Logger getLogger() {
		return logger;
	}
}