package ch.nomoresecrets.mediastopf.client.ui.dialogs;

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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import ch.nomoresecrets.mediastopf.client.ui.MediaStopf;

public class ConfigDialog extends JFrame {

	private static final String CONFIGFILE = "MediaStopf.cfg";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Properties prop = new Properties();
	private JTextField ripperTextField, folderTextField;
	private String audioripper = "audioripper", defaultfolder = "defaultfolder";

	public ConfigDialog() {
		initGUI();
	}

	/**
	 * init GUI
	 */
	private void initGUI() {
		setTitle(MediaStopf.PROGRAM + " - Config");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setSize(400, 230);
		setIconImage(new ImageIcon(getClass().getResource(MediaStopf.UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);

		addButtons();
		addDefaultFolderPanel();
		addAudioRipper();
		addESCListener();
		
		loadProperties();
	}

	private void addDefaultFolderPanel() {
		createBorder("Default Folder", new Rectangle(0, 10, 395, 70));
		createLabel("defaultfolder.png", new Rectangle(12, 30, 40, 40));
		
		folderTextField = createTextField(new Point(60, 40));
		folderTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openDefaultFolderFileChooser();
			}
		});
		
		JButton openIcon = createOpenButton(new Rectangle(355, 40, 22, 22));
		openIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openDefaultFolderFileChooser();
			}
		});
	}

	private void addAudioRipper() {
		createBorder("AudioRipper", new Rectangle(0, 85, 395, 70));
		createLabel("audioripper.png", new Rectangle(12, 105, 40, 40));
		
		ripperTextField = createTextField(new Point(60, 115));
		ripperTextField.addMouseListener(new MouseAdapter() {
			@Override
			 public void mousePressed(MouseEvent e) {
				if((e.getButton() == MouseEvent.BUTTON1) && (e.getClickCount() == 2))
					openAudioRipperDirChooser();
			}
		});
		
		JButton openIcon = createOpenButton(new Rectangle(355, 115, 22, 22));
		openIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAudioRipperDirChooser();
			}
		});
	}
	
	private JButton createOpenButton(Rectangle rec) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource(MediaStopf.UIIMAGELOCATION + "open.png")));
		button.setBounds(rec);
		button.setToolTipText("Choose Directory");
		add(button);
		return button;
	}

	private void createLabel(String icon, Rectangle rec) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(getClass().getResource(MediaStopf.UIIMAGELOCATION + icon)));
		label.setBounds(rec);
		label.setBorder(LineBorder.createBlackLineBorder());
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
		int y = 170;
		int width = 100;
		int height = 25;
		final String ok = "OK", close = "Close";
		final String[] buttonText = { ok, close };
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
		if(!ripperTextField.getText().isEmpty())
			prop.setProperty(audioripper, ripperTextField.getText());
		if(!folderTextField.getText().isEmpty())
			prop.setProperty(defaultfolder, folderTextField.getText());
		try {
			prop.store(new FileWriter(CONFIGFILE), "MediaStopf Config");
		} catch (IOException e) {
			System.out.println(e);
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
	 * load properties.
	 */
	void loadProperties() {
		File config = new File(CONFIGFILE);
		if(!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			prop.load(new FileReader(CONFIGFILE));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
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
}
