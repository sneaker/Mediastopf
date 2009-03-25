package ch.nomoresecrets.mediastopf.client.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ch.nomoresecrets.mediastopf.client.ui.dialogs.AboutDialog;

public class MediaStopf extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PROGRAM = "MediaStopf";
	public static final String UIIMAGELOCATION = "/ch/nomoresecrets/mediastopf/client/ui/images/";
	
	public MediaStopf() {
		initGUI();
	}
	
	private void initGUI() {
		setTitle(PROGRAM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(800, 600));
		setSize(800, 600);
		// setIconImage(new ImageIcon(getClass().getResource(UIIMAGELOCATION + "icon.gif")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		setJMenuBar(createMenuBar());
	}

	/**
	 * MenuBar
	 * 
	 * @return JMenuBar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		final String file = "File", help = "Help";
		final String[] menuItems = { file, help };
		final int fileMnemonic = KeyEvent.VK_F, helpMnemonic = KeyEvent.VK_H;
		final int[] keyEvent = new int[] { fileMnemonic, helpMnemonic };
		for (int i = 0; i < menuItems.length; i++) {
			JMenu menu = new JMenu(menuItems[i]);
			menu.setMnemonic(keyEvent[i]);
			if (menuItems[i] == file) {
				addFileItems(menu);
			} else {
				addHelpItems(menu);
			}
			menuBar.add(menu);
		}
		return menuBar;
	}

	/**
	 * help menu items
	 * 
	 * @param helpMenu
	 */
	private void addHelpItems(JMenu helpMenu) {
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog about = new AboutDialog();
				about.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
	}

	/**
	 * filemenu items
	 * 
	 * @param fileMenu JMenu
	 */
	private void addFileItems(JMenu fileMenu) {
		final String log = "Log", exit = "Exit";
		final String[] fileTitles = { log, exit };
		final KeyStroke logAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke exitAccelerator = null;
		final KeyStroke[] keyStrokes = { logAccelerator,  exitAccelerator };
		for (int i = 0; i < fileTitles.length; i++) {
			JMenuItem fileItem = new JMenuItem();
			fileItem.setText(fileTitles[i]);
			fileItem.setAccelerator(keyStrokes[i]);
			fileItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == log) {
						
					} else if (e.getActionCommand() == exit){
						System.exit(0);
					}
				}
			});
			fileMenu.add(fileItem);
		}
	}
}
