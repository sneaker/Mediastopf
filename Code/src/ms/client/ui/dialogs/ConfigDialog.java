package ms.client.ui.dialogs;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import ms.client.ui.MainView;


public class ConfigDialog extends JDialog {

	private static final String CONFIGFILE = "MediaStopf.cfg";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Properties prop = new Properties();
	private JTextField ripperTextField, folderTextField;
	private final String audioripper = "AudioRipper", defaultfolder = "Default Folder";
	private final String ok = "Save", close = "Close";

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
	}

	private void initDialog() {
		setTitle(MainView.PROGRAM + " - Config");
		setSize(400, 230);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		setLayout(null);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource(MainView.UIIMAGELOCATION + "icon.png")).getImage());
	}
	
	private void addPanels() {
		final String[] label = { defaultfolder, audioripper };
		final String[] icons = { "defaultfolder.png", "audioripper.png" };
		final int x=0;
		final int[] y= { 10, 85 };
		for(int i=0; i<label.length;i++) {
			createBorder(label[i], new Rectangle(x, y[i], 395, 70));
			createLabel(icons[i], new Rectangle(x+12, y[i]+20, 40, 40));
			
			JButton icon = createOpenButton(label[i], new Rectangle(355, y[i]+30, 22, 22));
			icon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == defaultfolder) {
						openDefaultFolderFileChooser();
					} else {
						openAudioRipperDirChooser();
					}
				}
			});
		}
		addDefaultFolderTextField();
		addAudioRipperTextField();
	}

	private void addDefaultFolderTextField() {
		folderTextField = createTextField(new Point(60, 40));
		folderTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openDefaultFolderFileChooser();
			}
		});
	}

	private void addAudioRipperTextField() {
		ripperTextField = createTextField(new Point(60, 115));
		ripperTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openAudioRipperDirChooser();
			}
		});
	}
	
	private JButton createOpenButton(String name, Rectangle rec) {
		JButton button = new JButton();
		button.setActionCommand(name);
		button.setIcon(new ImageIcon(getClass().getResource(MainView.UIIMAGELOCATION + "open.png")));
		button.setBounds(rec);
		button.setToolTipText("Choose Directory");
		add(button);
		return button;
	}

	private void createLabel(String icon, Rectangle rec) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource(MainView.UIIMAGELOCATION + icon)));
		label.setBounds(rec);
		add(label);
	}

	private void createBorder(String border, Rectangle rec) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(border));
		panel.setOpaque(false);
		panel.setBounds(rec);
		add(panel);
	}

	private JTextField createTextField(Point p) {
		final JTextField textField = new JTextField();
		textField.setSize(290, 22);
		textField.setLocation(p);
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
		int x = 150;
		int y = 170;
		int width = 100;
		int height = 25;
		final Rectangle okBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 110, y, width, height);
		final Rectangle[] bounds = { okBounds, cancelBounds };
		final String[] buttonText = { ok, close };
		final String[] icons = { "save.png", "cancel.png" };
		final int okMnemonic = KeyEvent.VK_S, cancelMnemonic = KeyEvent.VK_C;
		final int[] mnemonic = { okMnemonic, cancelMnemonic };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.setIcon(new ImageIcon(getClass().getResource(MainView.UIIMAGELOCATION + icons[i])));
		    button.setVerticalTextPosition(JButton.CENTER);
		    button.setHorizontalTextPosition(JButton.RIGHT);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == ok) {
						saveProperties();
						close();
					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
		}
	}
	
	/**
	 * save properties.
	 */
	void saveProperties() {
		saveValues();
		try {
			prop.store(new FileWriter(CONFIGFILE), "MediaStopf Config");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	private void saveValues() {
		if(!ripperTextField.getText().isEmpty())
			prop.setProperty(audioripper, ripperTextField.getText());
		if(!folderTextField.getText().isEmpty())
			prop.setProperty(defaultfolder, folderTextField.getText());
	}
	
	/**
	 * load properties.
	 */
	void loadProperties() {
		File config = new File(CONFIGFILE);
		try {
			if(!config.exists()) {
				config.createNewFile();
			}
			prop.load(new FileReader(CONFIGFILE));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		loadValues();
	}
	
	private void loadValues() {
		if(prop.containsKey(audioripper))
			ripperTextField.setText(prop.getProperty(audioripper).trim());
		if(prop.containsKey(defaultfolder))
			folderTextField.setText(prop.getProperty(defaultfolder).trim());
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

	private void fileFilter(JFileChooser fileChooser) {
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			fileChooser.setFileFilter(new FileFilter() {
				public boolean accept(File file) {
					return file.getName().toLowerCase().endsWith(".exe") || file.isDirectory();
				}
				public String getDescription() {
					return "*.exe";
				}
			});
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
		JRootPane rootPane = getRootPane();
		rootPane.registerKeyboardAction(cancelListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
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
		final String clear = "Clear", cut = "Cut", copy = "Copy", paste = "Paste", selectAll = "Select All"; 
		final String[] menuItems = new String[] { clear, cut, copy, paste, selectAll };
		for (int i = 0; i < menuItems.length; i++) {
			JMenuItem menuItem = new JMenuItem(menuItems[i]);
			if (i == 1 || i == 4) {
				popupMenu.addSeparator();
			}
			menuItem.addActionListener(new ActionListener() {
				@Override
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
}
