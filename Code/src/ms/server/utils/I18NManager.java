package ms.server.utils;

import java.util.ResourceBundle;

/**
 * determine language of system and get the language from a file
 * 
 * @author david
 *
 */
public class I18NManager {

	private static I18NManager manager = new I18NManager();
	private ResourceBundle rb;

	private I18NManager() {
		rb = ResourceBundle.getBundle(Constants.LANGUAGE);
	}

	public static I18NManager getManager() {
		return manager;
	}

	public String getString(String key) {
		String text = rb.getString(key);
		return text.replace("&", "");
	}

	public char getMnemonic(String key) {
		String text = getString(key);
		return text.charAt(0);
	}
}