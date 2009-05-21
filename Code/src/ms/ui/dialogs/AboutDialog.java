
package ms.ui.dialogs;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import ms.ui.Constants;
import ms.utils.BrowserControl;
import ms.utils.GUIComponents;
import ms.utils.I18NManager;
import ms.utils.ui.Button;
import ms.utils.ui.Dialog;
import ms.utils.ui.TextField;

/**
 * about dialog
 */
public class AboutDialog extends Dialog {

	private static final long serialVersionUID = 9535632795379520L;
	
	private I18NManager manager = I18NManager.getManager();
	private Class<? extends Constants> constants;
	
	public AboutDialog(Class<? extends Constants> constants) {
		this.constants = constants;
		initGUI();
	}
	
	/**
	 * create and set gui components
	 */
	private void initGUI() {
		initDialog();
		
		addTextFields();
		addESCListener();
		addButtons();
		drawBackgroundImage();
	}

	private void initDialog() {
		String title = "";
		try {
			title = constants.getField("PROGRAM").get(constants) + " - " + manager.getString("About.title");
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.initDialog(title, getClass().getResource(Constants.UIIMAGE + Constants.ICON), new Dimension(400, 250));
	}
	

	/**
	 * add buttons
	 */
	private void addButtons() {
		final int x = 175;
		final int y = 190;
		final int width = 100;
		final int height = 20;
		JButton button = new Button(new Rectangle(x + 105, y, width, height), manager.getString("close"), manager.getMnemonic("close"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		add(button);
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
	private void addTextFields() {
		final String[] texts = { Constants.URL, Constants.HSR };
		final String[] tooltips = { Constants.URLEXT, Constants.HSREXT };
		final Rectangle urlBounds = new Rectangle(10, 190, 155, 20);
		final Rectangle hsrBounds = new Rectangle(165, 190, 110, 20);
		final Rectangle[] bounds = { urlBounds, hsrBounds };
		for(int i=0; i<texts.length; i++) {
			JTextField textField = new TextField(texts[i], bounds[i], tooltips[i]);
			textField.setComponentPopupMenu(addPopUpMenu(textField));
			add(textField);
		}
	}

	/**
	 * PopupMenu
	 * 
	 * @param textField
	 * @return JPopupMenu
	 */
	private JPopupMenu addPopUpMenu(final JTextField textField) {
		final String copy = manager.getString("copy");
		final String open = manager.getString("About.open");
		final String[] texts = { open, copy };
		JPopupMenu popupMenu = new JPopupMenu();
		for(int i=0; i<texts.length; i++) {
			JMenuItem menuItem = new JMenuItem(texts[i]);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == copy) {
						textField.selectAll();
						textField.copy();
						textField.select(0, 0);
					} else {
						BrowserControl.displayURL(textField.getText().trim());
					}
				}
			});
			popupMenu.add(menuItem);
		}
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
		JRootPane rootPane = this.getRootPane();
		rootPane.registerKeyboardAction(cancelListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void close() {
		setVisible(false);
		dispose();
	}
}
