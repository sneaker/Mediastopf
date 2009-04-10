package ms.server.utils;

import java.util.Locale;
import java.util.ResourceBundle;


public class I18NManager {

	private static I18NManager manager = new I18NManager();
	private ResourceBundle rb;
	private Locale locale;

	private I18NManager() {
		rb = ResourceBundle.getBundle(Constants.LANGUAGE);
		locale = Locale.getDefault();
	}

	public void changeManagerLocale(Locale locale) {
		if (this.locale != locale) {
			rb = ResourceBundle.getBundle(Constants.LANGUAGE, locale);
			this.locale = locale;
		}
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
		int index = text.indexOf("&");
		return text.charAt(index + 1);
	}
}
