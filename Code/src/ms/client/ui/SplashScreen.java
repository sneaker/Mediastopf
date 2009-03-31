package ms.client.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

public class SplashScreen extends JWindow implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private String image;

	public SplashScreen(String splash) {
		this.image = splash;
		new Thread(this).start();
	}

	public void run() {
		JLabel label = new JLabel(new ImageIcon(getClass().getResource(image)));
		label.setBorder(LineBorder.createBlackLineBorder());
		add(label);
		pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - label.getWidth()) / 2, (screenSize.height - label.getHeight()) / 2);
		setAlwaysOnTop(true);
		setVisible(true);
		try {
			Thread.sleep(1600);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		setVisible(false);
		dispose();
	}
}
