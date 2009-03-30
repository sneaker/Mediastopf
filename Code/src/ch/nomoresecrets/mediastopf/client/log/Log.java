package ch.nomoresecrets.mediastopf.client.log;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log {
	
	private static final String LOGFILE = "MediaStopf.log";
	private static Logger logger = Logger.getRootLogger();

	public Log() {
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
			fileAppender = new FileAppender(layout, LOGFILE, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.addAppender(fileAppender);
	}

	private void createFile() {
		File f = new File(LOGFILE);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Set Log Level:
	 * ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
	 * 
	 * @param level
	 */
	public void setLevel(Level level) {
		logger.setLevel(level);
	}
	
	/**
	 * get logger
	 * 
	 * @return Logger
	 */
	public static Logger getLogger() {
		return logger;
	}
	
	/**
	 * get log filename
	 * 
	 * @return String
	 */
	public static String getLog() {
		return LOGFILE;
	}
}
