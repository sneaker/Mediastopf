package ms.utils.ui;

import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.border.Border;

public class Panel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Panel(Rectangle bounds) {
		setLayout(null);
		setBounds(bounds);
	}
	
	public Panel(Rectangle bounds, Border border) {
		this(bounds);
		setBorder(border);
	}
}
