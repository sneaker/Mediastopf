package ch.nomoresecrets.mediastopf.server.log;

public interface LoggingInterface {

	public LoggingInterface getInstance(Class className);

	public void debug(String Text);

	public void warning(String Text);

	public void error(String Text);

	public void info(String Text);

	public void setLevel(int level);
}
