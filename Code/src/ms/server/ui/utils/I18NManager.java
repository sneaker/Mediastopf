package ms.server.ui.utils;

import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

public class I18NManager extends Observable {

	private static I18NManager manager = new I18NManager();
	
	private ResourceBundle rb;
	private Locale locale;
	
	private I18NManager() {
		rb = ResourceBundle.getBundle(Constants.LANGUAGE);
		locale = Locale.getDefault();
	}
	
	public void changeManagerLocale(Locale locale) {
		if(this.locale != locale) {
			rb = ResourceBundle.getBundle(Constants.LANGUAGE, locale);
			this.locale = locale;
			
			setChanged();
			notifyObservers();
		}
	}
	
	public static I18NManager getManager() {
		return manager;
	}
	
	public String getString(String key) {
		return rb.getString(key);
	}
}
