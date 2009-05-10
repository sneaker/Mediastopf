package ms.server.log;

import java.io.ByteArrayOutputStream;

import ms.common.log.Log;
import ms.server.ui.ServerConstants;

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
	
	static {
		new ServerLog();
	}
	
	private ServerLog() {
		logger = Logger.getLogger(ServerLog.class);
		bos = new ByteArrayOutputStream();
		
		initLogger(ServerConstants.class);
	}
}
