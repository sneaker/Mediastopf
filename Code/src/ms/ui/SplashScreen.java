package ms.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JWindow;

import ms.utils.GUIComponents;

/**
 * splash screen
 * 
 * @author david
 *
 */
public class SplashScreen extends JWindow implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private String image;

	public SplashScreen(String image) {
		this.image = image;
		new Thread(this).start();
	}

	public void run() {
		initWindow(setSplashImage());
		sleep();
		close();
	}

	private void sleep() {
		try {
			Thread.sleep(1750);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	private void close() {
		setVisible(false);
		dispose();
	}

	private JLabel setSplashImage() {
		JLabel label = GUIComponents.createJLabel(getClass().getResource(image));
		add(label);
		pack();
		return label;
	}

	private void initWindow(JLabel label) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - label.getWidth()) / 2, (screenSize.height - label.getHeight()) / 2);
		setAlwaysOnTop(true);
		setVisible(true);
	}
}