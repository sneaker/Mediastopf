package ms.client.ui.dialogs;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import ms.client.ui.MainView;

abstract class AbstractDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AbstractDialog() {
		initDialog();
		addESCListener();
		addButtons();
	}

	private void initDialog() {
		setLayout(null);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource(MainView.UIIMAGELOCATION + "icon.png")).getImage());
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

	/**
	 * close
	 */
	protected void close() {
		setVisible(false);
		dispose();
	}

	/**
	 * PopupMenu
	 * 
	 * @param textField
	 * @return JPopupMenu
	 */
	protected JPopupMenu addPopUpMenu(final JTextField textField) {
		JPopupMenu popupMenu = new JPopupMenu();
		final String[] menuItems = getPopUpItems();
		for (int i = 0; i < menuItems.length; i++) {
			JMenuItem menuItem = new JMenuItem(menuItems[i]);
			if (i == 1 || i == 4) {
				popupMenu.addSeparator();
			}
			menuItem.addActionListener(getPopUpActionListener(textField));
			popupMenu.add(menuItem);
		}
		return popupMenu;
	}

	abstract String[] getPopUpItems();
	
	abstract ActionListener getPopUpActionListener(final JTextField textField);

	abstract ActionListener getButtonActionListener();

	abstract int[] getButtonMnemonic();

	abstract Rectangle[] getButtonBounds();

	abstract String[] getButtonText();

	/**
	 * add buttons
	 */
	protected void addButtons() {
		final String[] buttonText = getButtonText();
		final Rectangle[] bounds = getButtonBounds();
		final int[] mnemonic = getButtonMnemonic();
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.addActionListener(getButtonActionListener());
			add(button);
		}
	}
}