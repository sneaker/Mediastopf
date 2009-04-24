package ms.common.utils;

import java.util.ResourceBundle;

import ms.client.ui.ClientConstants;
import ms.server.ui.ServerConstants;

/**
 * determine language of system and get the language from a file
 * 
 * @author david
 *
 */
public class I18NManager {

	private static I18NManager client = new I18NManager(ClientConstants.LANGUAGE);
	private static I18NManager server = new I18NManager(ServerConstants.LANGUAGE);
	private ResourceBundle rb;

	private I18NManager(String language) {
		rb = ResourceBundle.getBundle(language);
	}

	public static I18NManager getClientManager() {
		return client;
	}
	
	public static I18NManager getServerManager() {
		return server;
	}

	public String getString(String key) {
		return rb.getString(key).replace("&", "");
	}

	public char getMnemonic(String key) {
		return getString(key).charAt(0);
	}
}
