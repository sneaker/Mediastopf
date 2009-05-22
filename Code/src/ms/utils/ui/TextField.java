package ms.utils.ui;

import java.awt.Rectangle;

import javax.swing.JTextField;

public class TextField extends JTextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextField(Rectangle bounds) {
		this("", bounds);
	}
	
	public TextField(String text, Rectangle bounds) {
		setText(text);
		setBounds(bounds);
	}
	
	public TextField(String text, Rectangle bounds, String tooltip) {
		this(text, bounds);
		setHorizontalAlignment(JTextField.CENTER);
		setOpaque(false);
	}
}
