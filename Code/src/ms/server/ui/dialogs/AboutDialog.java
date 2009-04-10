
package ms.server.ui.dialogs;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import ms.server.ui.utils.Constants;
import ms.server.ui.utils.I18NManager;
import ms.server.utils.BrowserControl;


public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 9535632795379520L;
	
	private I18NManager manager = I18NManager.getManager();
	private final String website = manager.getString("About.website"), close = manager.getString("close");
	
	public AboutDialog() {
		initGUI();
	}
	
	/**
	 * create and set gui components
	 */
	private void initGUI() {
		initDialog();
		
		addURL();
		addESCListener();
		addButtons();
		drawBackgroundImage();
	}

	private void initDialog() {
		setTitle(Constants.PROGRAM + " - " + manager.getString("About.title"));
		setSize(400, 250);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - 400) / 2, (dim.height - 350) / 2);
		setLayout(null);
		setResizable(false);
		setModal(true);
		setIconImage(new ImageIcon(getClass().getResource(Constants.UIIMAGE + Constants.ICON)).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	

	/**
	 * add buttons
	 */
	private void addButtons() {
		final int x = 175;
		final int y = 190;
		final int width = 100;
		final int height = 20;
		final Rectangle websiteBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 105, y, width, height);
		final Rectangle[] bounds = { websiteBounds, cancelBounds };
		final String[] buttonText = { website, close };
		final int websiteMnemonic = KeyEvent.VK_W, cancelMnemonic = KeyEvent.VK_C;
		final int[] mnemonic = { websiteMnemonic, cancelMnemonic };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == website) {
						BrowserControl.displayURL(Constants.URL);
					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
		}
	}

	/**
	 * draw background image
	 */
	private void drawBackgroundImage() {
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g) {
				ImageIcon img = new ImageIcon(getClass().getResource(Constants.ABOUT));
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
		JTextField textField = new JTextField(Constants.URL);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBounds(new Rectangle(10, 190, 155, 20));
		textField.setEditable(false);
		textField.setOpaque(false);
		textField.setToolTipText(Constants.URLEXT);
		textField.setComponentPopupMenu(addPopUpMenu(textField));
		add(textField);
	}

	/**
	 * PopupMenu
	 * 
	 * @param textField
	 * @return JPopupMenu
	 */
	private JPopupMenu addPopUpMenu(final JTextField textField) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem copyMenuItem = new JMenuItem(manager.getString("copy"));
		copyMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.copy();
			}
		});
		popupMenu.add(copyMenuItem);
		return popupMenu;
	}

	/**
	 * esc = close dialog
	 */
	private void addESCListener() {
		ActionListener cancelListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		};
		JRootPane rootPane = getRootPane();
		rootPane.registerKeyboardAction(cancelListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void close() {
		setVisible(false);
		dispose();
	}
}