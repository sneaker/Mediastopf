package ch.nomoresecrets.mediastopf.client.log;

import java.text.SimpleDateFormat;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;

public class Log4JLog implements LoggingInterface {

//	private final static String[] levelDescriptions = { "ERROR   ", "WARNING ", "DEBUG   ", "INFO    " };

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmmss");

	private String className = null;

	private Category log = null;

	private static boolean loaded = false;

	private static void initialize() {
		loaded = true;
		PropertyConfigurator.configureAndWatch("log4j.properties");
	}

	public LoggingInterface getInstance(Class className) {
		return new Log4JLog(className.getCanonicalName());
	}

	public Log4JLog(Class className) {
		this(className.toString());
	}

	public Log4JLog(String className) {
		if (!loaded)
			initialize();
		this.className = className;
		log = Category.getInstance(className);
	}

	public void setLevel(int level) {
		System.out.println("Log Level Not Implemented. Adjust Logging Level in log4j.properties file.");
	}

	public void debug(String Text) {
		writeToLog4J(Text, 2);
	}

	public void warning(String Text) {
		writeToLog4J(Text, 1);
	}

	public void error(String Text) {
		writeToLog4J(Text, 0);
	}

	public void info(String Text) {
		writeToLog4J(Text, 3);
	}

	private void writeToLog4J(String Text, int Level) {
		switch (Level) {
		case 0:
			log.error(Text);
			break;
		case 1:
			log.warn(Text);
			break;
		case 2:
			log.debug(Text);
			break;
		case 3:
			log.info(Text);
			break;
		}
	}
}
