package ms.client.ui;

import java.awt.Color;
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
import ms.client.ui.StatusMessage.StatusType;
import ms.client.ui.dialogs.ConfigDialog;
import ms.client.ui.models.TaskComboBoxModel;
import ms.client.ui.tables.TaskTable;
import ms.client.utils.ConfigHandler;
import ms.client.utils.I18NManager;
import ms.logic.Task;
import ms.ui.LogFrame;
import ms.ui.SplashScreen;
import ms.ui.dialogs.AboutDialog;
import ms.ui.dialogs.MessageDialog;
import ms.utils.ApplicationLauncher;

/**
 * main window of mediastopf
 * 
 * @author david
 *
 */
public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private I18NManager manager = I18NManager.getManager();
	private ConfigHandler config = ConfigHandler.getHandler();
	private TaskList taskList;
	private TaskList runTaskList;
	private JComboBox taskComboBox;
	private JScrollPane tableScrollPane;
	private JPanel tablePanel;
	private JTextField statusBar;
	private TaskTable taskTable;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private HashMap<String, JPanel> panelMap = new HashMap<String, JPanel>();
	private String run = manager.getString("Main.run"), reload = manager.getString("Main.reload"),
	send = manager.getString("send"), cancel = manager.getString("cancel"),
	runningTask = manager.getString("Main.runtask"), tasks = manager.getString("Main.task"), statusbar = manager.getString("Main.statusbar");

	public MainView() {
		if (StartClient.DEBUG) {
			setTitle(Constants.PROGRAM + " - Debug");
		} else {
			new SplashScreen(Constants.SPLASH);
		}

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
		setTitle(Constants.PROGRAM);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(600, 550);
		setMinimumSize(new Dimension(400, 450));
		setLayout(null);
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
		runtaskPanel.setSize(width - 10, height - 180);
		JPanel taskPanel = panelMap.get(tasks);
		taskPanel.setSize(width - 10, taskPanel.getHeight());
		JPanel statusPanel = panelMap.get(statusbar);
		statusPanel.setBounds(0, height - 70, width - 10, statusPanel.getHeight());
		
		updateComponentBounds(runtaskPanel, taskPanel, statusPanel);
	}

	private void updateComponentBounds(JPanel runtaskPanel, JPanel taskPanel, JPanel statusPanel) {
		buttonMap.get(reload).setLocation(taskPanel.getWidth() - 260, taskPanel.getHeight() - 40);
		buttonMap.get(run).setLocation(taskPanel.getWidth() - 135, taskPanel.getHeight() - 40);

		taskComboBox.setSize(taskPanel.getWidth() - 25, 20);
		statusBar.setSize(statusPanel.getWidth(), statusPanel.getHeight());

		int width = runtaskPanel.getWidth() - 10;
		int height = runtaskPanel.getHeight() - 70;
		tablePanel.setSize(width, height);
		tableScrollPane.setSize(tablePanel.getWidth(), tablePanel.getHeight());
		tableScrollPane.revalidate();

		buttonMap.get(send).setLocation(width - 250, height + 30);
		buttonMap.get(cancel).setLocation(width - 125, height + 30);
	}

	private void addStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, getHeight() - 70, getWidth() - 10, 20);
		panelMap.put(statusbar, panel);

		statusBar = new JTextField(manager.getString("StatusMessage.copyright"));
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
		panel.setBounds(0, 5, getWidth() - 10, 90);
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
		taskList = new TaskList();
		taskComboBox = new JComboBox(new TaskComboBoxModel(taskList));
		taskComboBox.setBounds(10, 20, getWidth() - 30, 20);
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
		int x = panel.getWidth() - 260;
		int y = panel.getHeight() - 40;
		int width = 115;
		int height = 25;
		final String[] buttonText = { reload, run };
		final String[] icons = { Constants.RELOAD, Constants.TICK };
		final Rectangle reloadBounds = new Rectangle(x, y, width, height);
		final Rectangle runBounds = new Rectangle(x + width + 10, y, width, height);
		final Rectangle[] bounds = { reloadBounds, runBounds };
		final int reloadMnemonic = KeyEvent.VK_F5;
		final int runMnemonic = manager.getMnemonic("Main.run");
		final int[] mnemonic = { reloadMnemonic, runMnemonic };
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
					if (e.getActionCommand() == reload) {
						taskList.updateList();
						updateStatusBar(StatusType.RELOADMESSAGE);
					} else if (e.getActionCommand() == run) {
						runSelectedItem();
					}
				}
			});
			panel.add(button);
			buttonMap.put(buttonText[i], button);
		}
	}

	// TODO
	private void runSelectedItem() {
		String taskID = (String) taskComboBox.getSelectedItem();
		if(taskID == null) {
			MessageDialog.noneSelectedDialog();
			return;
		}
		String folder = getValueOf(Constants.DEFAULTFOLDERCFG);
		if(!new File(folder).exists()) {
			askFolder(manager.getString("Main.choosedefaultfoldertitle"),
			manager.getString("Main.choosedefaultfolder"));
			return;
		}
		File task = new File(folder + File.separator + taskID);
		task.mkdirs();
		
		String ripper = getValueOf(Constants.AUDIORIPPERCFG);
		if(!new File(ripper).exists()) {
			askFolder(manager.getString("Main.chooseaudiorippertitle"),
			manager.getString("Main.chooseaudioripper"));
			return;
		}
		ApplicationLauncher.open(ripper);
		
		updateStatusBar(StatusType.RUNMESSAGE);
		Client.observeDir(task);
		
		// TODO
		int id = Integer.valueOf(taskID);
		taskList.remove(taskComboBox.getSelectedIndex());
		runTaskList.add(new Task(id, "Warten"));
	}

	private void askFolder(String title, String message) {
		MessageDialog.info(title, message + manager.getString("Config.defaultfolder"));
		openConfigDialog();
		runSelectedItem();
	}

	private String getValueOf(String key) {
		if(config.containsKey(key)) {
			return config.getProperty(key).trim();
		}
		return "";
	}
	
	private void updateStatusBar(StatusType type) {
		statusBar.setForeground(Color.BLACK);
		statusBar.setText(StatusMessage.getMessage(type));
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(4000);
					statusBar.setForeground(Color.GRAY);
					statusBar.setText(manager.getString("StatusMessage.copyright"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	/**
	 * add running task panel
	 */
	private void addRunningTaskPanel() {
		addTaskTable();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 100, getWidth() - 10, getHeight() - 180);
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
		tablePanel.setBounds(5, 15, getWidth() - 20, getHeight() - 250);
		tablePanel.setLayout(null);
		
		runTaskList = new TaskList();
		taskTable = new TaskTable(runTaskList);
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
		int x = panel.getWidth() - 260;
		int y = panel.getHeight() - 40;
		int width = 115;
		int height = 25;
		final String[] buttonText = { send, cancel };
		final String[] icons = { Constants.SEND, Constants.CANCEL };
		final Rectangle sendBounds = new Rectangle(x, y, width, height);
		final Rectangle cancelBounds = new Rectangle(x + width + 10, y, width, height);
		final Rectangle[] bounds = { sendBounds, cancelBounds };
		final int sendAcc = manager.getMnemonic("send");
		final int cancelAcc = manager.getMnemonic("cancel");
		final int[] mnemonic = { sendAcc, cancelAcc };
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
					if (e.getActionCommand() == send) {
						taskTable.send();
						updateStatusBar(StatusType.SENDMESSAGE);
					} else if (e.getActionCommand() == cancel) {
						taskTable.cancel();
						updateStatusBar(StatusType.CANCELMESSAGE);
					}
				}
			});
			panel.add(button);
			buttonMap.put(buttonText[i], button);
		}
	}

	private void exit() {
		int result = MessageDialog.yesNoDialog(manager.getString("Main.exittitle"),
				manager.getString("Main.exitmessage"));
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
		final String config = manager.getString("Main.configitem"), log = manager.getString("Main.logitem"), exit = manager.getString("exit");
		final String[] fileTitles = { config, log, exit };
		final KeyStroke configAccelerator = KeyStroke.getKeyStroke(manager.getMnemonic("Main.configitem"), KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke logAccelerator = KeyStroke.getKeyStroke(manager.getMnemonic("Main.logitem"), KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke exitAccelerator = null;
		final KeyStroke[] keyStrokes = { configAccelerator, logAccelerator, exitAccelerator };
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
						openConfigDialog();
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

	private void openConfigDialog() {
		ConfigDialog cd = new ConfigDialog();
		cd.setVisible(true);
	}
}
