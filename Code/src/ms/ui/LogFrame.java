package ms.ui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import ms.application.server.ServerController;
import ms.utils.GUIComponents;
import ms.utils.I18NManager;

/**
 * show log information from logger
 * 
 * @author david
 *
 */
public class LogFrame extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private I18NManager manager = I18NManager.getManager();
	private JTextArea textArea;
	private JScrollPane scrollArea;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private final String save = manager.getString("save"), close = manager.getString("close");
	private Class<? extends Constants> constants;

	public LogFrame(Class<? extends Constants> constants) {
		this.constants = constants;
		initGUI();
	}

	private void initGUI() {
		initFrame();

		addButtons();
		addTextArea();
		addESCListener();
	}

	private void initFrame() {
		String title = "";
		try {
			title = constants.getField("PROGRAM").get(constants) + " - " + manager.getString("Log.title");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUIComponents.initFrame(this, getClass().getResource(Constants.UIIMAGE + Constants.ICON), new Dimension(500, 430));
		
		componentListener();
	}

	private void componentListener() {
		addComponentListener(new ComponentAdapter() {
			private boolean isShown = false;
			@Override
			public void componentResized(ComponentEvent e) {
				if (isShown) {
					updateComponentBounds();
				}
			}
			@Override
			public void componentShown(ComponentEvent e) {
				isShown = true;
			}
		});
	}
	
	private void updateComponentBounds() {
		int width = getWidth() - 15;
		int height = getHeight() - 65;
		scrollArea.setSize(width , height - 15);
		scrollArea.revalidate();
		
		buttonMap.get(save).setLocation(width - 250, height);
		buttonMap.get(close).setLocation(width - 125, height);
	}

	private void addTextArea() {
		textArea = GUIComponents.createTextArea();
		textArea.setComponentPopupMenu(getPopUpMenu(textArea));
		scrollArea = GUIComponents.createJScrollPane(textArea, new Rectangle(5, 5, 485, 350));
		add(scrollArea);
	}

	/**
	 * add buttons
	 */
	private void addButtons() {
		int x = 235;
		int y = 365;
		int width = 115;
		int height = 25;
		final String[] buttonText = { save, close };
		final URL[] icons = { getClass().getResource(Constants.UIIMAGE + Constants.SAVE), getClass().getResource(Constants.UIIMAGE + Constants.CANCEL) };
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + width + 10, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		final int okMnemonic = KeyEvent.VK_S, cancelMnemonic = KeyEvent.VK_C;
		final int[] mnemonic = { okMnemonic, cancelMnemonic };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = GUIComponents.createButton(bounds[i], buttonText[i], mnemonic[i], icons[i]);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == save) {
						saveAsTXT();
					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
			buttonMap.put(buttonText[i], button);
		}
	}

	private void saveAsTXT() {
		JFileChooser fileChooser = GUIComponents.getFileChooser();
		try {
			fileChooser.setSelectedFile(new File((String) constants.getField("LOGFILE").get(constants)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		GUIComponents.jFileFilter(fileChooser, ".txt");

		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getName();
			String path = fileChooser.getCurrentDirectory().toString();
			String file = addTXTPostfix(filename, path);
			ServerController.writeFile(new File(file), textArea.getText().trim());
		}
	}

	private String addTXTPostfix(String filename, String path) {
		String file = path + File.separator + filename;
		if (!filename.endsWith("txt")) {
			file += ".txt";
		}
		return file;
	}
	
	/**
	 * close
	 */
	private void close() {
		setVisible(false);
		dispose();
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
	 * PopupMenu
	 * 
	 * @param textField
	 * @return JPopupMenu
	 */
	private JPopupMenu getPopUpMenu(final JTextArea textArea) {
		JPopupMenu popupMenu = new JPopupMenu();
		final String copy = manager.getString("copy"), selectall = manager.getString("selectall");
		final String[] menuItems = { copy, selectall };
		for (int i = 0; i < menuItems.length; i++) {
			JMenuItem copyMenuItem = new JMenuItem(menuItems[i]);
			copyMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == copy) {
						textArea.copy();
					} else {
						textArea.selectAll();
					}
				}
			});
			popupMenu.add(copyMenuItem);
		}
		return popupMenu;
	}

	private void readLogContent(ByteArrayOutputStream bos) {
		textArea.setText(bos.toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ByteArrayOutputStream) {
			readLogContent((ByteArrayOutputStream) arg);
		}
	}
}
