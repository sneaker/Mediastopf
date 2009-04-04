
package ms.client.ui.dialogs;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ms.client.ui.MainView;
import ms.client.utils.BrowserControl;


public class AboutDialog extends AbstractDialog {

	private static final long serialVersionUID = 9535632795379520L;
	
	private static final String URL = "www.no-more-secrets.ch";
	private static final String URLEXT = "powered by No More Secrets";
	private static final String BACKGROUNDIMAGE = MainView.UIIMAGELOCATION + "about.jpg";
	private final String website = "Website", close = "Close";
	
	public AboutDialog() {
		initGUI();
	}
	
	/**
	 * create and set gui components
	 */
	private void initGUI() {
		setTitle(MainView.PROGRAM + " - About...");
		setSize(400, 250);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		
		addURL();
		drawBackgroundImage();
	}
	
	String[] getButtonText() {
		final String[] buttonText = { website, close };
		return buttonText;
	}

	Rectangle[] getButtonBounds() {
		final int x = 175;
		final int y = 190;
		final int width = 100;
		final int height = 20;
		final Rectangle websiteBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 105, y, width, height);
		final Rectangle[] bounds = { websiteBounds, cancelBounds };
		return bounds;
	}

	int[] getButtonMnemonic() {
		final int websiteMnemonic = KeyEvent.VK_S, cancelMnemonic = KeyEvent.VK_C;
		final int[] mnemonic = { websiteMnemonic, cancelMnemonic };
		return mnemonic;
	}
	
	ActionListener getButtonActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == website) {
					BrowserControl.displayURL(URL);
				} else if (e.getActionCommand() == close) {
					close();
				}
			}
		};
	}

	/**
	 * draw background image
	 */
	private void drawBackgroundImage() {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g) {
				ImageIcon img = new ImageIcon(getClass().getResource(BACKGROUNDIMAGE));
				g.drawImage(img.getImage(), 0, -30, null);
			}
		};
		panel.setOpaque(false);
		panel.setLayout(null);
		panel.setBounds(0, 0, getWidth(), getHeight());
		add(panel);
	}
	
	/**
	 * add url textfield
	 */
	private void addURL() {
		JTextField textField = new JTextField(URL);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds(new Rectangle(10, 190, 155, 20));
		textField.setEditable(false);
		textField.setOpaque(false);
		textField.setToolTipText(URLEXT);
		textField.setComponentPopupMenu(addPopUpMenu(textField));
		add(textField);
	}

	String[] getPopUpItems() {
		return new String[] { "Copy" };
	}
	
	ActionListener getPopUpActionListener(final JTextField textField) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.copy();
			}
		};
	}
}