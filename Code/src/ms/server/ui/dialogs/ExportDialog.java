package ms.server.ui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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

import ms.common.filesys.FileIO;
import ms.common.ui.Constants;
import ms.common.ui.dialogs.MessageDialog;
import ms.common.utils.ConfigHandler;
import ms.common.utils.I18NManager;
import ms.server.ui.ServerConstants;

/**
 * dialog to choose a destination, where the files should be copied
 * 
 * @author david
 *
 */
public class ExportDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigHandler config = ConfigHandler.getClientHandler();
	private I18NManager manager = I18NManager.getManager();
	private final String exportFolder = manager.getString("Exporter.exportstorage");
	private final String export = manager.getString("export"), close = manager.getString("close");
	private JLabel folderNotValidLabel = getNotValidLabel(new Point(140, 10));
	private JTextField exportTextField;
	private int taskID;

	public ExportDialog(int taskID) {
		this.taskID = taskID;
		
		initGUI();
	}

	/**
	 * init GUI
	 */
	private void initGUI() {
		initDialog();

		addESCListener();
		addButtons();
		addDefaultFolderPanel();
		
		loadProperties();
		
		showPathNotValidLabel();
	}

	private void initDialog() {
		setTitle(ServerConstants.PROGRAM + " - " + export);
		setSize(400, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		setLayout(null);
		setResizable(false);
		setModal(true);
		setIconImage(new ImageIcon(getClass().getResource(Constants.UIIMAGE + Constants.ICON)).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void addDefaultFolderPanel() {
		createBorder(exportFolder, new Rectangle(0, 10, 395, 70));
		createLabel(Constants.EXPORT_L, new Rectangle(12, 30, 40, 40));
		
		exportTextField = createTextField(new Point(60, 40));
		exportTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				showPathNotValidLabel();
			}
		});
		exportTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openExportFileChooser();
			}
		});
		createOpenButton(new Rectangle(355, 40, 22, 22));
	}
	
	private void createOpenButton(Rectangle rec) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource(Constants.OPEN)));
		button.setBounds(rec);
		button.setToolTipText(manager.getString("Exporter.choosedir"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openExportFileChooser();
			}
		});
		add(button);
	}

	private void createLabel(String icon, Rectangle rec) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource(Constants.UIIMAGE + icon)));
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
		final int x = 135;
		final int y = 90;
		final int width = 115;
		final int height = 25;
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + width + 10, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		final String[] buttonText = { export, close };
		final String[] icons = { Constants.TICK, Constants.CANCEL };
		final int exportMnemonic = manager.getMnemonic("export"), cancelMnemonic = manager.getMnemonic("close");
		final int[] mnemonic = { exportMnemonic, cancelMnemonic };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.setIcon(new ImageIcon(getClass().getResource(Constants.UIIMAGE + icons[i])));
		    button.setVerticalTextPosition(JButton.CENTER);
		    button.setHorizontalTextPosition(JButton.RIGHT);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == export) {
						export();
						saveAndClose();
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
		File file = new File(Integer.toString(taskID));
		boolean done = FileIO.transfer(file.listFiles(), new File(exportFolder));
		if(done) {
			MessageDialog.info(manager.getString("Exporter.exportdone"), manager.getString("Exporter.exportfilesto") + exportFolder);
		} else {
			MessageDialog.info(manager.getString("Exporter.exportfailedtitle"), manager.getString("Exporter.exportfailed"));
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
	private void saveProperties() {
		saveValues();
		config.save();
	}

	private void saveValues() {
		if(!exportTextField.getText().isEmpty())
			config.setProperty(Constants.EXPORTCFG, exportTextField.getText().trim());
	}
	
	/**
	 * load properties.
	 */
	private void loadProperties() {
		if(config.containsKey(Constants.EXPORTCFG))
			exportTextField.setText(config.getProperty(Constants.EXPORTCFG));
	}
	
	private void openExportFileChooser() {
		JFileChooser dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirChooser.setAcceptAllFileFilterUsed(false);
		openDialog(dirChooser, exportTextField);
		showPathNotValidLabel();
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
	
	private JLabel getNotValidLabel(Point p) {
		JLabel label = new JLabel(manager.getString("Exporter.notvalid"));
		label.setSize(120, 25);
		label.setLocation(p);
		label.setForeground(Color.RED);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.RED));
		label.setVisible(true);
		add(label, 0);
		return label;
	}
	
	private void showPathNotValidLabel() {
		String text = exportTextField.getText();
		File f = new File(text);
		if(f.exists() && f.isDirectory()) {
			folderNotValidLabel.setVisible(false);
		} else {
			folderNotValidLabel.setVisible(true);
		}
	}
}
