package ch.nomoresecrets.mediastopf.server.ui.dialogs;

import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ch.nomoresecrets.mediastopf.server.ui.MediaStopfServer;

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
		setTitle(MediaStopfServer.PROGRAM + " - Export");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setSize(400, 150);
		setModal(true);
		setIconImage(new ImageIcon(getClass().getResource(MediaStopfServer.UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);

		addButtons();
		addDefaultFolderPanel();
		
		loadProperties();
	}

	private void addDefaultFolderPanel() {
		JPanel panel = createPanel("Export Storage");
		panel.setBounds(0, 10, 395, 70);
		exportTextField = createTextField();
		exportTextField.setLocation(60, 40);
		exportTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				openDefaultFolderFileChooser();
			}
		});
		JLabel iconLabel = createLabel("export.png", new Rectangle(12, 30, 40, 40));
		JButton openIcon = createOpenButton(new Rectangle(355, 40, 22, 22));
		openIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openDefaultFolderFileChooser();
			}
		});

		JComponent[] comp = { panel, exportTextField, iconLabel, openIcon };
		for(JComponent c: comp) {
			add(c);
		}
	}
	
	private JButton createOpenButton(Rectangle rec) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource(MediaStopfServer.UIIMAGELOCATION + "open.png")));
		button.setBounds(rec);
		button.setToolTipText("Choose Directory");
		return button;
	}

	private JLabel createLabel(String icon, Rectangle rec) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource(MediaStopfServer.UIIMAGELOCATION + icon)));
		label.setBounds(rec);
		label.setBorder(LineBorder.createBlackLineBorder());
		return label;
	}

	private JPanel createPanel(String border) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(border));
		panel.setOpaque(false);
		return panel;
	}

	private JTextField createTextField() {
		final JTextField textField = new JTextField();
		textField.setSize(290, 22);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				textField.requestFocus();
				textField.selectAll();
			}
		});
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
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == export) {
						export();
					} else if (e.getActionCommand() == close) {
						saveProperties();
						close();
					}
				}
			});
			add(button);
		}
	}
	
	private void export() {
		// TODO
		MessageDialog.info("Export " + tasknum, "export to " + exportTextField.getText().trim());
	}
	
	/**
	 * close
	 */
	private void close() {
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
	
	private void openDefaultFolderFileChooser() {
		JFileChooser dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirChooser.setAcceptAllFileFilterUsed(false);
		openDialog(dirChooser, exportTextField);
	}

	private void openDialog(JFileChooser dirChooser, JTextField textField) {
		int returnVal = dirChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = dirChooser.getSelectedFile().getName();
			String path = dirChooser.getCurrentDirectory().toString();
			textField.setText(path + File.separator + filename + File.separator);
		}
	}
}
