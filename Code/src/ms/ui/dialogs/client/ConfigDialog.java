package ms.ui.dialogs.client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import ms.ui.client.ClientConstants;
import ms.utils.ConfigHandler;
import ms.utils.GUIComponents;
import ms.utils.I18NManager;

/**
 * configuration dialog
 * - custom cdripper
 * - custom import folder
 */
public class ConfigDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigHandler config = ConfigHandler.getClientHandler();
	private I18NManager manager = I18NManager.getManager();
	private JTextField ripperTextField, folderTextField;
	private final String audioripper = manager.getString("Config.audiograbber"), defaultfolder = manager.getString("Config.defaultfolder");
	private final String save = manager.getString("save"), close = manager.getString("close");
	private JLabel folderNotValidLabel = getNotValidLabel(100, 10);

	public ConfigDialog() {
		initGUI();
	}

	/**
	 * init GUI
	 */
	private void initGUI() {
		initDialog();

		addPanels();
		addESCListener();
		addButtons();
		
		loadProperties();
		
		showPathNotValidLabel();
	}

	private void initDialog() {
		String title = ClientConstants.PROGRAM + " - " + manager.getString("Config.title"); 
		GUIComponents.initJDialog(this, title, getClass().getResource(ClientConstants.UIIMAGE + ClientConstants.ICON), new Dimension(400, 230));
	}
	
	private void addPanels() {
		final String[] labels = { defaultfolder, audioripper };
		final URL[] icons = { getClass().getResource(ClientConstants.DEFAULTFOLDER), getClass().getResource(ClientConstants.AUDIORIPPER) };
		final int x=0;
		final int[] y= { 10, 85 };
		for(int i=0; i<labels.length;i++) {
			JPanel panel = GUIComponents.createPanel(new Rectangle(x, y[i], 395, 70), BorderFactory.createTitledBorder(labels[i]));
			panel.setOpaque(false);
			add(panel);
			
			add(GUIComponents.createJLabel(icons[i], new Rectangle(x+12, y[i]+20, 40, 40)));
			
			createOpenButton(labels[i], new Rectangle(355, y[i]+30, 22, 22));
		}
		addDefaultFolderTextField();
		addAudioRipperTextField();
	}

	private void addDefaultFolderTextField() {
		folderTextField = createTextField(60, 40);
		folderTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				showPathNotValidLabel();
			}
		});
		folderTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openDefaultFolderFileChooser();
			}
		});
	}

	private void addAudioRipperTextField() {
		ripperTextField = createTextField(60, 115);
		ripperTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openAudioRipperDirChooser();
			}
		});
	}
	
	private void createOpenButton(String command, Rectangle bounds) {
		URL icon = getClass().getResource(ClientConstants.OPEN);
		String tooltip = manager.getString("choosedir");
		JButton button = GUIComponents.createJButton(bounds, command, icon, tooltip);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == defaultfolder) {
					openDefaultFolderFileChooser();
				} else {
					openAudioRipperDirChooser();
				}
			}
		});
		add(button);
	}

	private JTextField createTextField(int x, int y) {
		final JTextField textField = GUIComponents.createJTextField(new Rectangle(x, y, 290, 22));
		textField.setComponentPopupMenu(addPopUpMenu(textField));
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				textField.requestFocus();
				textField.selectAll();
			}
		});
		add(textField);
		return textField;
	}
	
	/**
	 * add buttons
	 */
	protected void addButtons() {
		int x = 135;
		int y = 165;
		int width = 115;
		int height = 25;
		final Rectangle okBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + width + 10, y, width, height);
		final Rectangle[] bounds = { okBounds, cancelBounds };
		final String[] buttonText = { save, close };
		final URL[] icons = { getClass().getResource(ClientConstants.UIIMAGE + ClientConstants.SAVE), getClass().getResource(ClientConstants.UIIMAGE + ClientConstants.CANCEL) };
		final int saveMnemonic = manager.getMnemonic("save"), cancelMnemonic = manager.getMnemonic("close");
		final int[] mnemonic = { saveMnemonic, cancelMnemonic };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = GUIComponents.createJButton(bounds[i], buttonText[i], mnemonic[i], icons[i]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == save) {
						saveAndClose();
					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
		}
	}
	
	private void saveAndClose() {
		saveProperties();
		close();
	}
	
	/**
	 * save properties.
	 */
	private void saveProperties() {
		saveValues();
		config.save();
	}
	
	private void saveValues() {
		if(!ripperTextField.getText().isEmpty())
			config.setProperty(ClientConstants.AUDIORIPPERCFG, ripperTextField.getText());
		if(!folderTextField.getText().isEmpty())
			config.setProperty(ClientConstants.DEFAULTFOLDERCFG, folderTextField.getText());
	}
	
	/**
	 * load properties.
	 */
	private void loadProperties() {
		if(config.containsKey(ClientConstants.AUDIORIPPERCFG))
			ripperTextField.setText(config.getProperty(ClientConstants.AUDIORIPPERCFG).trim());
		if(config.containsKey(ClientConstants.DEFAULTFOLDERCFG))
			folderTextField.setText(config.getProperty(ClientConstants.DEFAULTFOLDERCFG).trim());
	}
	
	private void openAudioRipperDirChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileFilter(fileChooser);
		openDialog(fileChooser, ripperTextField);
	}
	
	private void openDefaultFolderFileChooser() {
		JFileChooser dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirChooser.setAcceptAllFileFilterUsed(false);
		openDialog(dirChooser, folderTextField);
		showPathNotValidLabel();
	}

	private void openDialog(JFileChooser fileChooser, JTextField textField) {
		if(!textField.getText().isEmpty()) {
			fileChooser.setCurrentDirectory(new File(textField.getText()));
		}
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			textField.setText(fileChooser.getSelectedFile().getAbsolutePath().trim());
		}
	}
	
	private JLabel getNotValidLabel(int x, int y) {
		JLabel label = GUIComponents.createJLabel(manager.getString("Config.notvalid"), new Rectangle(x, y, 120, 25)); 
		label.setVisible(true);
		add(label, 0);
		return label;
	}

	private void fileFilter(JFileChooser fileChooser) {
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			GUIComponents.getFileFilter(fileChooser, "exe");
		}
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
		GUIComponents.addESCListener(this, cancelListener);
	}

	/**
	 * close
	 */
	private void close() {
		setVisible(false);
		dispose();
	}
	
	/**
	 * PopupMenu
	 * 
	 * @param textField
	 * @return JPopupMenu
	 */
	private JPopupMenu addPopUpMenu(final JTextField textField) {
		JPopupMenu popupMenu = new JPopupMenu();
		final String clear = manager.getString("clear"), cut = manager.getString("cut"),
		copy = manager.getString("copy"), paste = manager.getString("paste"), selectAll = manager.getString("selectall"); 
		final String[] menuItems = new String[] { clear, cut, copy, paste, selectAll };
		for (int i = 0; i < menuItems.length; i++) {
			JMenuItem menuItem = new JMenuItem(menuItems[i]);
			if (i == 1 || i == 4) {
				popupMenu.addSeparator();
			}
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (event.getActionCommand() == cut) {
						textField.cut();
					} else if (event.getActionCommand() == copy) {
						textField.copy();
					} else if (event.getActionCommand() == paste) {
						textField.paste();
					} else if (event.getActionCommand() == clear) {
						textField.setText("");
					} else {
						textField.selectAll();
					}
				}
			});
			popupMenu.add(menuItem);
		}
		return popupMenu;
	}

	private void showPathNotValidLabel() {
		String text = folderTextField.getText();
		File f = new File(text);
		if(f.exists() && f.isDirectory()) {
			folderNotValidLabel.setVisible(false);
		} else {
			folderNotValidLabel.setVisible(true);
		}
	}
}
