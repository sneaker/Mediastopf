package ms.utils.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame() {
		super();
	}
	
	public void initFrame(String title, URL url, Dimension size, int closeOperation) {
		setTitle(title);
		setSize(size);
		setIconImage(new ImageIcon(url).getImage());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
		setLayout(null);
		setMinimumSize(new Dimension(getWidth(), getHeight()));
		setDefaultCloseOperation(closeOperation);
	}
}
