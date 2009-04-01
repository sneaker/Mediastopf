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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import ms.server.filesys.Exporter;
import ms.server.ui.MainViewServer;


public class ExportDialog extends JDialog {

	private static final String CONFIGFILE = "MediaStopfServer.cfg";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Properties prop = new Properties();
	private JTextField exportTextField;
	private String export = "exportfolder";
	private String tasknum;

	public ExportDialog(String tasknum) {
		this.tasknum = tasknum;
		
		initGUI();
	}

	/**
	 * init GUI
	 */
	private void initGUI() {
		setTitle(MainViewServer.PROGRAM + " - Export");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setSize(400, 150);
		setModal(true);
		setIconImage(new ImageIcon(getClass().getResource(MainViewServer.UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);

		addButtons();
		addDefaultFolderPanel();
		addESCListener();
		
		loadProperties();
	}

	private void addDefaultFolderPanel() {
		createBorder("External Storage", new Rectangle(0, 10, 395, 70));
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

	/**
	 * add buttons
	 */
	private void addButtons() {
		int x = 150;
		int y = 90;
		int width = 100;
		int height = 25;
		final String export = "Export", close = "Close";
		final String[] buttonText = { export, close };
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
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == export) {
						export();
						close();
					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
		}
	}
	
	private void export() {
		String exportFolder = exportTextField.getText().trim();
		File file = new File(tasknum);
		boolean done = Exporter.export(file.listFiles(), new File(exportFolder));
		if(done) {
			MessageDialog.info("Export done", "Exported Files to " + exportFolder);
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
		saveProperties();
		setVisible(false);
		dispose();
	}
	
	/**
	 * save properties.
	 */
	void saveProperties() {
		if(!exportTextField.getText().isEmpty())
			prop.setProperty(export, exportTextField.getText().trim());
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
		if(prop.containsKey(export))
			exportTextField.setText(prop.getProperty(export));
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
