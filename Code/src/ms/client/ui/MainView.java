package ms.client.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import ms.client.Client;
import ms.client.StartClient;
import ms.client.logic.TaskList;
import ms.client.ui.dialogs.AboutDialog;
import ms.client.ui.dialogs.ConfigDialog;
import ms.client.ui.dialogs.MessageDialog;
import ms.client.ui.models.TaskComboBoxModel;
import ms.client.ui.tables.TaskTable;

public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PROGRAM = "MediaStopf";
	public static final String UIIMAGELOCATION = "/ms/client/ui/images/";
	private static final String SPLASHIMAGE = UIIMAGELOCATION + "splash.jpg";

	private TaskList taskList;
	private JComboBox taskComboBox;
	private JScrollPane tableScrollPane;
	private JPanel tablePanel;
	private JTextField statusBar;
	private TaskTable taskTable;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private HashMap<String, JPanel> panelMap = new HashMap<String, JPanel>();
	private String run = "Run", reload = "Reload", send = "Send", cancel = "Cancel",
			runningTask = "Running Tasks", tasks = "Tasks",
			statusbar = "StatusBar";
	private Client client;

	public MainView(Client client) {
		if (StartClient.DEBUG) {
			setTitle(MainView.PROGRAM + " - Debug");
		} else {
			new SplashScreen(SPLASHIMAGE);
		}
		this.client = client;

		initGUI();
	}

	/**
	 * init GUI Components
	 */
	private void initGUI() {
		initFrame();

		addStatusBar();
		addTaskPanel();
		addRunningTaskPanel();
	}

	private void initFrame() {
		setTitle(PROGRAM);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(400, 450);
		setMinimumSize(new Dimension(getWidth(), getHeight()));
		setLayout(null);
		setIconImage(new ImageIcon(getClass().getResource(UIIMAGELOCATION + "icon.png")).getImage());
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
				exit();
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
		int width = getWidth();
		int height = getHeight();

		JPanel runtaskPanel = panelMap.get(runningTask);
		runtaskPanel.setSize(width - 5, height - 180);
		JPanel taskPanel = panelMap.get(tasks);
		taskPanel.setSize(width - 5, taskPanel.getHeight());
		JPanel statusPanel = panelMap.get(statusbar);
		statusPanel.setBounds(0, height - 70, width - 5, statusPanel.getHeight());
		
		updateComponentBounds(runtaskPanel, taskPanel, statusPanel);
	}

	private void updateComponentBounds(JPanel runtaskPanel, JPanel taskPanel, JPanel statusPanel) {
		buttonMap.get(run).setLocation(taskPanel.getWidth() - 135, taskPanel.getHeight() - 40);

		taskComboBox.setSize(taskPanel.getWidth() - 30, 20);
		statusBar.setSize(statusPanel.getWidth(), statusPanel.getHeight());

		int width = runtaskPanel.getWidth() - 10;
		int height = runtaskPanel.getHeight() - 70;
		tablePanel.setSize(width, height);
		tableScrollPane.setSize(tablePanel.getWidth(), tablePanel.getHeight());
		tableScrollPane.revalidate();

		buttonMap.get(send).setLocation(width - 235, height + 30);
		buttonMap.get(cancel).setLocation(width - 125, height + 30);
	}

	private void addStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 380, 450, 20);
		panel.setBorder(BorderFactory.createTitledBorder(statusbar));
		panelMap.put(statusbar, panel);

		statusBar = new JTextField("(C)2009 MediaStopf");
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
		addTaskComboBox();	
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 5, 395, 90);
		panel.setBorder(BorderFactory.createTitledBorder(tasks));
		panel.add(taskComboBox);
		panelMap.put(tasks, panel);
		add(panel);
		addTaskButtons(panel);
	}

	/**
	 * combobox which show the tasks available
	 * 
	 * @return JComboBox
	 */
	private void addTaskComboBox() {
		taskList = new TaskList(client);
		taskComboBox = new JComboBox(new TaskComboBoxModel(taskList));
		taskComboBox.setBounds(10, 20, 365, 20);
		if (0 < taskComboBox.getItemCount())
			taskComboBox.setSelectedIndex(0);
		taskComboBox.setUI(new javax.swing.plaf.metal.MetalComboBoxUI() {
			public void layoutComboBox(Container parent,
					MetalComboBoxLayoutManager manager) {
				super.layoutComboBox(parent, manager);
				arrowButton.setBounds(0, 0, 0, 0);
			}
		});
	}
	
	private void addTaskButtons(JPanel panel) {
		int x = 150;
		int y = 50;
		int width = 100;
		int height = 25;
		final String[] buttonText = { reload, run };
		final String[] icons = { "reload.png", "tick.png" };
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 110, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		final int sendAcc = KeyEvent.VK_F5;
		final int cancelAcc = KeyEvent.VK_R;
		final int[] mnemonic = { sendAcc, cancelAcc };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.setIcon(new ImageIcon(getClass().getResource(UIIMAGELOCATION + icons[i])));
		    button.setVerticalTextPosition(JButton.CENTER);
		    button.setHorizontalTextPosition(JButton.RIGHT);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == reload) {
						taskList.updateList();
					} else if (e.getActionCommand() == run) {
						runSelectedItem();
					}
				}
			});
			panel.add(button);
			buttonMap.put(buttonText[i], button);
		}
	}

	private void runSelectedItem() {
		String taskID = (String) taskComboBox.getSelectedItem();
		File task = new File(taskID);
		if (!task.isDirectory()) {
			MessageDialog.info("Not a Directory", taskID
					+ " is not a directory.");
			return;
		}

		client.observeDir(taskID);

		// TODO
		// ApplicationLauncher.open(program);
	}

	/**
	 * add running task panel
	 */
	private void addRunningTaskPanel() {
		addTaskTable();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 100, 395, 270);
		panel.setBorder(BorderFactory.createTitledBorder(runningTask));

		addRunningTaskButtons(panel);

		panel.add(tablePanel);
		panelMap.put(runningTask, panel);
		add(panel);
	}

	/**
	 * add task table
	 * 
	 * @return JPanel
	 */
	private void addTaskTable() {
		tablePanel = new JPanel();
		tablePanel.setBounds(5, 15, 385, 200);
		tablePanel.setLayout(null);
		
		taskTable = new TaskTable();
		tableScrollPane = new JScrollPane(taskTable);
		tableScrollPane.setBounds(0, 0, tablePanel.getWidth(), tablePanel.getHeight());
		tablePanel.add(tableScrollPane);
	}

	/**
	 * add button for the running tasks
	 * 
	 * @param panel
	 *            JPanel
	 */
	private void addRunningTaskButtons(JPanel panel) {
		int x = 150;
		int y = 230;
		int width = 100;
		int height = 25;
		final String[] buttonText = { send, cancel };
		final String[] icons = { "send.png", "cancel.png" };
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + 110, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		final int sendAcc = KeyEvent.VK_S;
		final int cancelAcc = KeyEvent.VK_C;
		final int[] mnemonic = { sendAcc, cancelAcc };
		for (int i = 0; i < buttonText.length; i++) {
			JButton button = new JButton();
			button.setBounds(bounds[i]);
			button.setText(buttonText[i]);
			button.setMnemonic(mnemonic[i]);
			button.setIcon(new ImageIcon(getClass().getResource(UIIMAGELOCATION + icons[i])));
		    button.setVerticalTextPosition(JButton.CENTER);
		    button.setHorizontalTextPosition(JButton.RIGHT);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == send) {
						taskTable.send();
					} else if (e.getActionCommand() == cancel) {
						taskTable.cancel();
					}
				}
			});
			panel.add(button);
			buttonMap.put(buttonText[i], button);
		}
	}

	private void exit() {
		int result = MessageDialog.yesNoDialog("Exit",
				"Do your really want to Quit?");
		switch (result) {
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
	 * @param fileMenu
	 *            JMenu
	 */
	private void addFileItems(JMenu fileMenu) {
		final String config = "Config", log = "Log", exit = "Exit";
		final String[] fileTitles = { config, log, exit };
		final KeyStroke configAccelerator = KeyStroke.getKeyStroke(
				KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke logAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_L,
				KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke exitAccelerator = null;
		final KeyStroke[] keyStrokes = { configAccelerator, logAccelerator,
				exitAccelerator };
		for (int i = 0; i < fileTitles.length; i++) {
			if (i == 2) {
				fileMenu.addSeparator();
			}
			JMenuItem fileItem = new JMenuItem();
			fileItem.setText(fileTitles[i]);
			fileItem.setAccelerator(keyStrokes[i]);
			fileItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == config) {
						ConfigDialog cd = new ConfigDialog();
						cd.setVisible(true);
					} else if (e.getActionCommand() == log) {
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
