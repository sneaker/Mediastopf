package ms.utils.log.client;

import java.io.ByteArrayOutputStream;
import ms.ui.client.ClientConstants;
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
public class ClientLog extends Log {
	
	public static ClientLog log = new ClientLog();
	
	private ClientLog() {
		logger = Logger.getLogger(ClientLog.class);
		bos = new ByteArrayOutputStream();
		
		initLogger(ClientConstants.class);
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
