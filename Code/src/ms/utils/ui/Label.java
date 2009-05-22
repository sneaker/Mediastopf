package ms.utils.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Label extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Label(URL url, Border border) {
		this(url);
		setBorder(border);
	}
	
	private Label(URL url) {
		setIcon(new ImageIcon(url));
	}
	
	public Label(URL url, Rectangle bounds) {
		this(url);
		setBounds(bounds);
	}
	
	public Label(String text, Rectangle bounds) {
		super(text);
		setBounds(bounds);
		setForeground(Color.RED);
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.RED));
	}
}
