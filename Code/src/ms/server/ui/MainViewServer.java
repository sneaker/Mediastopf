package ms.server.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.plaf.metal.MetalComboBoxUI;

import ms.server.Server;
import ms.server.StartServer;
import ms.server.domain.Auftrag;
import ms.server.logic.TaskList;
import ms.server.ui.dialogs.AboutDialog;
import ms.server.ui.dialogs.ExportDialog;
import ms.server.ui.dialogs.MessageDialog;
import ms.server.ui.models.TaskComboBoxModel;
import ms.server.ui.tables.ExportTable;
import ms.server.ui.tables.Table;
import ms.server.utils.Constants;
import ms.server.utils.I18NManager;

/**
 * main window of mediastopf server
 * 
 * @author david
 *
 */
public class MainViewServer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private I18NManager manager = I18NManager.getManager();
	private TaskComboBoxModel boxModel;
	private JComboBox taskComboBox;
	private JPanel tablePanel;
	private ExportTable exportTable;
	private JTextField statusBar;
	private JTabbedPane tabPane;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private HashMap<String, JPanel> panelMap = new HashMap<String, JPanel>();
	private final String export = manager.getString("export"), cancel = manager.getString("cancel"),
	runningTask = manager.getString("Main.runtask"), tasks = manager.getString("Main.task"),
	statusbar = manager.getString("Main.statusbar");
	
	public MainViewServer(Server server) {
		if (StartServer.DEBUG) {
			setTitle(Constants.PROGRAM + " - Debug");
		} else {
			new SplashScreen(Constants.SPLASH);
		}
		boxModel = new TaskComboBoxModel(server);

		initGUI();
	}

	/**
	 * init GUI Components
	 */
	private void initGUI() {
		initFrame();

		addTrayIcon();
		addStatusBar();
		addTaskPanel();
	}

	private void initFrame() {
		setTitle(Constants.PROGRAM);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(null);
		setSize(600, 550);
		setMinimumSize(new Dimension(400, 450));
		setIconImage(new ImageIcon(getClass().getResource(Constants.UIIMAGE + Constants.ICON)).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		setJMenuBar(createMenuBar());
		
		componentListener();
		windowListener();
	}

	private void windowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
	}

	private void componentListener() {
		addComponentListener(new ComponentAdapter() {
			private boolean isShown = false;
			@Override
			public void componentResized(ComponentEvent e) {
				if (isShown) {
					updatePanelBounds();
				}
			}
			@Override
			public void componentShown(ComponentEvent e) {
				isShown = true;
			}
		});
	}
	
	private void updatePanelBounds() {
		int width = getWidth() - 10;
		int height = getHeight() - 70;
		
		JPanel runtaskPanel = panelMap.get(runningTask);
		runtaskPanel.setSize(width, height - 110);
		JPanel taskPanel = panelMap.get(tasks);
		taskPanel.setSize(width, taskPanel.getHeight());
		JPanel statusPanel = panelMap.get(statusbar);
		statusPanel.setBounds(0, height, width, statusPanel.getHeight());
		
		updateComponentBounds(runtaskPanel, taskPanel, statusPanel);
	}
	
	private void updateComponentBounds(JPanel runtask, JPanel task, JPanel status) {
		buttonMap.get(export).setLocation(task.getWidth() - 140,task.getHeight() - 40);
		
		int width = runtask.getWidth() - 10;
		int height = runtask.getHeight() - 40;
		buttonMap.get(cancel).setLocation(width - 130, height);
		tablePanel.setSize(width, height - 30);
		
		taskComboBox.setSize(task.getWidth() - 20, 20);

		tabPane.setSize(tablePanel.getWidth(), tablePanel.getHeight()-5);
		
		statusBar.setSize(status.getWidth(), status.getHeight());
	}
	
	private void addTrayIcon() {
		new SystemTrayIcon(this);
	}
	
	private void addStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, getHeight() - 70, getWidth() - 10, 20);
		panelMap.put(statusbar, panel);
		
		statusBar = new JTextField(manager.getString("Main.copyright"));
		statusBar.setBounds(0, 0, panel.getWidth(), panel.getHeight());
		statusBar.setEditable(false);
		statusBar.setFocusable(false);
		
		panel.add(statusBar);
		add(panel);
	}

	/**
	 * add task panel
	 */
	private void addTaskPanel() {
		addButtons();
		addTaskComboBox();
		addTaskTable();
		
		final int[] y = { 5, 100 };
		final int[] height = { 90, getHeight() - 180 };
		final String[] panelLabel = { tasks, runningTask };
		final String[] buttonLabel = { export, cancel };
		for(int i=0; i<panelLabel.length;i++) {
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(0, y[i], getWidth() - 10, height[i]);
			panel.setBorder(BorderFactory.createTitledBorder(panelLabel[i]));
			panel.add(buttonMap.get(buttonLabel[i]));
			add(panel);
			panelMap.put(panelLabel[i], panel);
		}
		panelMap.get(tasks).add(taskComboBox);
		panelMap.get(runningTask).add(tablePanel);
	}
	
	/**
	 * combobox which show the tasks available
	 * 
	 * @return JComboBox
	 */
	private void addTaskComboBox() {
		taskComboBox = new JComboBox(boxModel);
		taskComboBox.setBounds(10, 20, getWidth() - 30, 20);
		if(0<taskComboBox.getItemCount())
			taskComboBox.setSelectedIndex(0);
		taskComboBox.setUI(new MetalComboBoxUI() {
			public void layoutComboBox(Container parent, MetalComboBoxLayoutManager manager) {
				super.layoutComboBox(parent, manager);
				arrowButton.setBounds(0, 0, 0, 0);
			}
		});
	}

	/**
	 * add buttons
	 * 
	 * @return JButton
	 */
	private void addButtons() {
		final int x = getWidth() - 150;
		final int[] y = { 50, getHeight() - 220 };
		final int width = 115;
		final int height = 25;
		final String[] label = { export, cancel };
		final String[] icons = { Constants.EXPORT_S, Constants.CANCEL };
		final int[] events = { manager.getMnemonic("export"), manager.getMnemonic("cancel") };
		for(int i=0;i<label.length;i++) {
			JButton button = new JButton();
			button.setBounds(x, y[i], width, height);
			button.setText(label[i]);
			button.setMnemonic(events[i]);
			button.setIcon(new ImageIcon(getClass().getResource(Constants.UIIMAGE + icons[i])));
		    button.setVerticalTextPosition(JButton.CENTER);
		    button.setHorizontalTextPosition(JButton.RIGHT);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == export) {
						exportSelectedItem();
					} else {
						exportTable.cancel();
					}
				}
			});
			buttonMap.put(label[i], button);
		}
		buttonMap.get(cancel).setEnabled(false);
	}
	
	private void exportSelectedItem() {
		int taskID = (Integer)taskComboBox.getSelectedItem();
		if(taskID == -1) {
			MessageDialog.noneSelectedDialog();
			return;
		}
		File file = new File(Integer.toString(taskID));
		if(!file.isDirectory()) {
			MessageDialog.info(manager.getString("Main.dirnotfoundtitle"), manager.getString("Main.dirnotfoundmessage") + taskID);
			return;
		}
		ExportDialog ed = new ExportDialog(taskID);
		ed.setVisible(true);
	}

	/**
	 * add task table
	 * 
	 * @return JPanel
	 */
	private void addTaskTable() {
		tablePanel = new JPanel();
		tablePanel.setBounds(5, 15, getWidth() - 20, getHeight() - 250);
		tablePanel.setLayout(null);
		
		exportTable = new ExportTable();
		
		tabPane = new JTabbedPane();
		tabPane.setBounds(0, 5, tablePanel.getWidth(), tablePanel.getHeight()-5);
		tabPane.addTab(manager.getString("Main.import"), new JScrollPane(new Table()));
		tabPane.addTab(manager.getString("Main.export"), new JScrollPane(exportTable));
		tabPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (tabPane.getSelectedIndex() == 0)
					buttonMap.get(cancel).setEnabled(false);
				else
					buttonMap.get(cancel).setEnabled(true);
			}
		});

		tablePanel.add(tabPane);
	}

	void exit() {
		int result = MessageDialog.yesNoDialog(manager.getString("Main.exittitle"), manager.getString("Main.exitmessage"));
		switch(result) {
		case JOptionPane.YES_OPTION:
			System.exit(0);
			break;
		case JOptionPane.NO_OPTION:
			return;
		}
	}


	/**
	 * MenuBar
	 * 
	 * @return JMenuBar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		final String file = manager.getString("Main.filemenu"), help = manager.getString("Main.helpmenu");
		final String[] menuItems = { file, help };
		final int fileMnemonic = manager.getMnemonic("Main.filemenu"), helpMnemonic = manager.getMnemonic("Main.helpmenu");
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
		JMenuItem aboutItem = new JMenuItem(manager.getString("Main.aboutitem"));
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
	 * @param fileMenu
	 *            JMenu
	 */
	private void addFileItems(JMenu fileMenu) {
		final String log = manager.getString("Main.logitem"), exit = manager.getString("exit");
		final String[] fileTitles = { log, exit };
		final KeyStroke logAccelerator = KeyStroke.getKeyStroke(manager.getMnemonic("Main.logitem"), KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke exitAccelerator = null;
		final KeyStroke[] keyStrokes = { logAccelerator, exitAccelerator };
		for (int i = 0; i < fileTitles.length; i++) {
			if(i == 1) {
				fileMenu.addSeparator();
			}
			JMenuItem fileItem = new JMenuItem();
			fileItem.setText(fileTitles[i]);
			fileItem.setAccelerator(keyStrokes[i]);
			fileItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == log) {
						LogFrame ld = new LogFrame();
						ld.setVisible(true);
					} else if (e.getActionCommand() == exit) {
						exit();
					}
				}
			});
			fileMenu.add(fileItem);
		}
	}
}
