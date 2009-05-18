package ms.utils.log.client;

import java.io.ByteArrayOutputStream;

import ms.utils.log.Log;

import org.apache.log4j.Logger;

/**
 * Apache log4j Logger
 * three different logtypes:
 * - show in console
 * - write to a file (daily logging)
 * - put log information to a outputstream
 */
public class ClientLog extends Log {
	
	public static final String LOGFILE = "logs/MediaStopf.log";
	public static ClientLog log = new ClientLog();
	
	private ClientLog() {
		logger = Logger.getLogger(ClientLog.class);
		bos = new ByteArrayOutputStream();
		
		initLogger(LOGFILE);
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
