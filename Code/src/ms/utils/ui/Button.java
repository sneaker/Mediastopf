package ms.utils.ui;

import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button(Rectangle bounds, String command) {
		this(bounds);
		setActionCommand(command);
	}
	
	public Button(Rectangle bounds, String buttonText, int mnemonic, URL url) {
		this(bounds, buttonText, mnemonic);
		setIcon(new ImageIcon(url));
	}
	
	public Button(Rectangle bounds, String buttonText, int mnemonic) {
		this(bounds, buttonText);
		setText(buttonText);
		setMnemonic(mnemonic);
	    setVerticalTextPosition(JButton.CENTER);
	    setHorizontalTextPosition(JButton.RIGHT);
	}
	
	private Button(Rectangle bounds) {
		setBounds(bounds);
	}
	
	private Button(Rectangle bounds, String command, URL url) {
		this(bounds, command);
		setIcon(new ImageIcon(url));
	}
	
	public Button(Rectangle bounds, String command, URL url, String tooltip) {
		this(bounds, command, url);
		setToolTipText(tooltip);
	}
	
	public Button(Rectangle bounds, URL url, String tooltip) {
		this(bounds, "", url, tooltip);
	}
}
