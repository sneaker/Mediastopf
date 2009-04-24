package ms.common.log;

import java.io.ByteArrayOutputStream;

import ms.common.ui.Constants;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;

public abstract class Log {

	protected static Logger logger;
	protected static ByteArrayOutputStream bos;


	protected void initLogger(Class<? extends Constants> constants) {
		String pattern = "%d{ISO8601}: %m %n";
		PatternLayout layout = new PatternLayout(pattern);
		logger.setLevel(Level.ALL);
		logger.addAppender(getConsoleLogger(layout));
		logger.addAppender(getFileLogger(layout, constants));
		logger.addAppender(getWriteLogger(layout));
	}
	
	private ConsoleAppender getConsoleLogger(PatternLayout layout) {
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		consoleAppender.setFollow(true);
		return consoleAppender;
	}

	private WriterAppender getWriteLogger(PatternLayout layout) {
		return new WriterAppender(layout, bos);
	}

	private DailyRollingFileAppender getFileLogger(PatternLayout layout, Class<? extends Constants> constants) {
		DailyRollingFileAppender fileAppender = new DailyRollingFileAppender();
		try {
			fileAppender = new DailyRollingFileAppender(layout, (String) constants.getField("LOGFILE").get(constants), "'_'yyyy-MM-dd");
			fileAppender.setAppend(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileAppender;
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
	 * get OutputStream with logged information
	 * 
	 * @return ByteArrayOutputStream
	 */
	public static ByteArrayOutputStream getOutputStream() {
		return bos;
	}
}
