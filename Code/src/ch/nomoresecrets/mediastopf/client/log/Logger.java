package ch.nomoresecrets.mediastopf.client.log;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Logger implements LoggingInterface {

	private final static String[] logLevelDescription = { "ERROR   ", "WARNING ", "DEBUG   ", "INFO    " };

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmmss");

	private int level = 1;

	private String className = null;

	public LoggingInterface getInstance(Class className) {
		return new Logger(className.getCanonicalName());
	}

	public Logger(Class className) {
		this(className.toString());
	}

	public Logger(String className) {
		this.className = className;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void warning(String Text) {
		writeToStandardOut(Text, 1);
	}

	public void error(String Text) {
		writeToStandardOut(Text, 0);
	}

	public void debug(String Text) {
		writeToStandardOut(Text, 2);
	}

	public void info(String Text) {
		writeToStandardOut(Text, 3);
	}

	private void writeToStandardOut(String Text, int Level) {
		Date date = new Date(System.currentTimeMillis());
		System.out.println(sdf.format(date) + " " + logLevelDescription[Level] + Text);
	}
}
