package ms.utils.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class Dialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dialog() {
		super();
	}
	
	public void initDialog(String title, URL url, Dimension size) {
		setTitle(title);
		setSize(size);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
		setLayout(null);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(url).getImage());
	}
}
