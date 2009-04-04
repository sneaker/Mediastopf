package ms.server.ui.dialogs;

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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import ms.server.filesys.FileIO;
import ms.server.ui.MainViewServer;


public class ExportDialog extends AbstractDialog {

	private static final String CONFIGFILE = "MediaStopfServer.cfg";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Properties prop = new Properties();
	private JTextField exportTextField;
	private final String exportFolder = "External Storage";
	private final String export = "Export", close = "Close";
	private String taskID;

	public ExportDialog(String taskID) {
		this.taskID = taskID;
		
		initGUI();
	}

	/**
	 * init GUI
	 */
	private void initGUI() {
		setTitle(MainViewServer.PROGRAM + " - Export");
		setSize(400, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);

		addDefaultFolderPanel();
		
		loadProperties();
	}

	private void addDefaultFolderPanel() {
		createBorder(exportFolder, new Rectangle(0, 10, 395, 70));
		createLabel("usbstick.png", new Rectangle(12, 30, 40, 40));
		
		exportTextField = createTextField(new Point(60, 40));
		exportTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openExportFileChooser();
			}
		});
		JButton openIcon = createOpenButton(new Rectangle(355, 40, 22, 22));
		openIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openExportFileChooser();
			}
		});
	}
	
	private JButton createOpenButton(Rectangle rec) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource(MainViewServer.UIIMAGELOCATION + "open.png")));
		button.setBounds(rec);
		button.setToolTipText("Choose Directory");
		add(button);
		return button;
	}

	private void createLabel(String icon, Rectangle rec) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource(MainViewServer.UIIMAGELOCATION + icon)));
		label.setBounds(rec);
		add(label);
	}

	private void createBorder(String borderTitle, Rectangle rec) {
		JPanel panel = new JPanel();
		panel.setBounds(rec);
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(borderTitle));
		panel.setOpaque(false);
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
	
	@Override
	ActionListener getButtonActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == export) {
					export();
					saveAndClose();
				} else if (e.getActionCommand() == close) {
					saveAndClose();
				}
			}
		};
	}

	@Override
	Rectangle[] getButtonBounds() {
		final int x = 150;
		final int y = 90;
		final int width = 100;
		final int height = 25;
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 110, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		return bounds;
	}

	@Override
	int[] getButtonMnemonic() {
		final int okMnemonic = KeyEvent.VK_O, cancelMnemonic = KeyEvent.VK_C;
		final int[] mnemonic = { okMnemonic, cancelMnemonic };
		return mnemonic;
	}

	@Override
	String[] getButtonText() {
		return new String[] { export, close };
	}
	
	private void export() {
		String exportFolder = exportTextField.getText().trim();
		File file = new File(taskID);
		boolean done = FileIO.transfer(file.listFiles(), new File(exportFolder));
		if(done) {
			MessageDialog.info("Export done", "Exported Files to " + exportFolder);
		}
	}
	
	/**
	 * close
	 */
	private void saveAndClose() {
		saveProperties();
		close();
	}

	/**
	 * save properties.
	 */
	void saveProperties() {
		if(!exportTextField.getText().isEmpty())
			prop.setProperty(exportFolder, exportTextField.getText().trim());
		try {
			prop.store(new FileWriter(CONFIGFILE), "MediaStopf Config");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * load properties.
	 */
	void loadProperties() {
		String configfile = CONFIGFILE;
		File config = new File(configfile);
		if(!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			prop.load(new FileReader(configfile));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		if(prop.containsKey(exportFolder))
			exportTextField.setText(prop.getProperty(exportFolder));
	}
	
	private void openExportFileChooser() {
		JFileChooser dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirChooser.setAcceptAllFileFilterUsed(false);
		openDialog(dirChooser, exportTextField);
	}

	private void openDialog(JFileChooser dirChooser, JTextField textField) {
		if(!textField.getText().isEmpty()) {
			dirChooser.setCurrentDirectory(new File(textField.getText()));
		}
		int returnVal = dirChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			textField.setText(dirChooser.getSelectedFile().getAbsolutePath().trim());
		}
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
