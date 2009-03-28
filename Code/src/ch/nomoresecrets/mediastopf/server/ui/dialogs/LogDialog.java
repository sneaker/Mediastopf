package ch.nomoresecrets.mediastopf.server.ui.dialogs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import ch.nomoresecrets.mediastopf.server.ui.MediaStopfServer;

public class LogDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogDialog() {
		initGUI();
	}
	
	private void initGUI() {
		setTitle(MediaStopfServer.PROGRAM + " - Log");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(500, 450));
		setSize(500, 450);
		setModal(true);
		setIconImage(new ImageIcon(getClass().getResource(MediaStopfServer.UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		
		addButtons();
	}
	
	/**
	 * add buttons
	 */
	private void addButtons() {
		int x = 250;
		int y = 370;
		int width = 100;
		int height = 25;
		final String save = "Save as TXT", close = "Close";
		final String[] buttonText = { save, close };
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 110, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		final int okMnemonic = KeyEvent.VK_O, cancelMnemonic = KeyEvent.VK_C;
		final int[] mnemonic = { okMnemonic, cancelMnemonic };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == save) {
						
					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
		}
	}
	
	/**
	 * close
	 */
	private void close() {
		setVisible(false);
		dispose();
	}
	
	public static void main(String[] args) {
		LogDialog ld = new LogDialog();
		ld.setVisible(true);
	}
}
