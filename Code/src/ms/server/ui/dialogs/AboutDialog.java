
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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import ms.server.ui.MainView;
import ms.server.utils.BrowserControl;


public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 9535632795379520L;
	
	private static final String URL = "www.no-more-secrets.ch";
	private static final String URLEXT = "powered by No More Secrets";
	private static final String BACKGROUNDIMAGE = MainView.UIIMAGELOCATION + "about.jpg";
	
	public AboutDialog() {
		initGUI();
	}
	
	/**
	 * create and set gui components
	 */
	private void initGUI() {
		setTitle(MainView.PROGRAM + " - About...");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setSize(400, 250);
		setIconImage(new ImageIcon(getClass().getResource(MainView.UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - 400) / 2, (dim.height - 350) / 2);
		
		addESCListener();
		addCloseButton();
		addWebsiteButton();
		addURL();
		drawBackgroundImage();
	}

	/**
	 * Close Button to close dialog
	 */
	private void addCloseButton() {
		JButton button_close = new JButton();
		button_close.setText("Close");
		button_close.setBounds(280, 190, 100, 20);
		button_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		add(button_close);
	}
	
	private void addWebsiteButton() {
		JButton button_website = new JButton();
		button_website.setText("Website");
		button_website.setBounds(175, 190, 100, 20);
		button_website.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowserControl.displayURL(URL);
			}
		});
		add(button_website);
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
		add(panel);
	}
	
	/**
	 * esc = close dialog
	 */
	private void addESCListener() {
		ActionListener cancelListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		};
		JRootPane rootPane = getRootPane();
		rootPane.registerKeyboardAction(cancelListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	/**
	 * close dialog
	 */
	private void closeDialog() {
		setVisible(false);
		dispose();
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

	/**
	 * PopupMenu
	 * 
	 * @param textField
	 * @return JPopupMenu
	 */
	private JPopupMenu addPopUpMenu(final JTextField textField) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem copyMenuItem = new JMenuItem("Copy");
		copyMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.copy();
			}
		});
		popupMenu.add(copyMenuItem);
		return popupMenu;
	}
}