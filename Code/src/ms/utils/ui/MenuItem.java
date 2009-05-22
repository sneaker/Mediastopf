package ms.utils.ui;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuItem extends JMenuItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuItem(String text, KeyStroke acc) {
		setText(text);
		setAccelerator(acc);
	}
}
