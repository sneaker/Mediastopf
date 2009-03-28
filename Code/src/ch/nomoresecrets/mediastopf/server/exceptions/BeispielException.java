package ch.nomoresecrets.mediastopf.server.exceptions;


public class BeispielException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum State {
		TRUE, FALSE;
	}
	
	public BeispielException(State state) {
		super(getStateMessage(state));
	}
	
	public static String getStateMessage(State state) {
		switch (state) {
		case TRUE:
			return "True";
		case FALSE:
			return "False";
		default:
			return "Error.";
		}
	}
}
