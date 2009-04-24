package ms.common.utils;

import java.util.ResourceBundle;

import ms.common.ui.Constants;

/**
 * determine language of system and get the language from a file
 * 
 * @author david
 *
 */
public class I18NManager {

	private static I18NManager manager = new I18NManager(Constants.LANGUAGE);
	private ResourceBundle rb;

	private I18NManager(String language) {
		rb = ResourceBundle.getBundle(language);
	}

	public static I18NManager getManager() {
		return manager;
	}
	
	public String getString(String key) {
		return rb.getString(key).replace("&", "");
	}

	public char getMnemonic(String key) {
		return getString(key).charAt(0);
	}
}
