package ms.server.log;

import java.io.File;
import java.io.IOException;

import ms.server.utils.Constants;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log {
	
	private static Logger logger = Logger.getRootLogger();
	static {
		new Log();
	}
	private Log() {
		logger.setLevel(Level.ALL);
		String pattern = "%d{ISO8601}: %m %n";
		PatternLayout layout = new PatternLayout(pattern);
		ConsoleLogger(layout);
		fileLogger(layout);
	}

	private void ConsoleLogger(PatternLayout layout) {
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		logger.addAppender(consoleAppender);
	}

	private void fileLogger(PatternLayout layout) {
		createFile();
		FileAppender fileAppender = null;
		try {
			fileAppender = new FileAppender(layout, Constants.LOGFILE, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.addAppender(fileAppender);
	}

	private void createFile() {
		File f = new File(Constants.LOGFILE);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
