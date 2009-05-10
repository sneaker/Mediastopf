package ms.client.log;

import java.io.ByteArrayOutputStream;

import ms.client.ui.ClientConstants;
import ms.common.log.Log;

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
	
	static {
		new ClientLog();
	}
	
	private ClientLog() {
		logger = Logger.getLogger(ClientLog.class);
		bos = new ByteArrayOutputStream();
		
		initLogger(ClientConstants.class);
	}
}
