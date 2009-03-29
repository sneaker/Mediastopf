package ch.nomoresecrets.mediastopf.client.ui;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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

import ch.nomoresecrets.mediastopf.client.filesys.DirectoryObserver;
import ch.nomoresecrets.mediastopf.client.logic.TaskList;
import ch.nomoresecrets.mediastopf.client.logic.TaskRunningList;
import ch.nomoresecrets.mediastopf.client.networking.ServerConnection;
import ch.nomoresecrets.mediastopf.client.ui.dialogs.AboutDialog;
import ch.nomoresecrets.mediastopf.client.ui.dialogs.ConfigDialog;
import ch.nomoresecrets.mediastopf.client.ui.dialogs.LogDialog;
import ch.nomoresecrets.mediastopf.client.ui.dialogs.MessageDialog;
import ch.nomoresecrets.mediastopf.client.ui.models.TaskComboBoxModel;
import ch.nomoresecrets.mediastopf.client.ui.models.TaskTableModel;
import ch.nomoresecrets.mediastopf.client.ui.tables.TaskTable;

public class MediaStopf extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PROGRAM = "MediaStopf";
	public static final String UIIMAGELOCATION = "/ch/nomoresecrets/mediastopf/client/ui/images/";

	private TaskComboBoxModel boxModel;
	private TaskTableModel tableModel;
	private JComboBox taskComboBox;
	private JScrollPane tableScrollPane;
	private JPanel tablePanel;
	private JTextField statusBar;
	private TaskTable taskTable;
	private TaskList taskList;
	private TaskRunningList runningList;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private HashMap<String, JPanel> panelMap = new HashMap<String, JPanel>();
	private String run = "Run", send = "Send", cancel = "Cancel",
			runningTask = "Running Tasks", tasks = "Tasks", statusbar = "StatusBar";
	private ServerConnection connection;

	public MediaStopf(ServerConnection connection) {
		this.connection = connection;
		taskList = new TaskList();
		boxModel = new TaskComboBoxModel(taskList);
		runningList = new TaskRunningList();
		tableModel = new TaskTableModel(runningList);

		initGUI();
	}

	/**
	 * init GUI Components
	 */
	private void initGUI() {
		setTitle(PROGRAM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(400, 450));
		setSize(400, 450);
		setIconImage(new ImageIcon(getClass().getResource(
				UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2,
				(dim.height - getHeight()) / 2);
		setJMenuBar(createMenuBar());

		addStatusBar();
		addTaskComboBox();
		addTaskTable();
		addTaskPanel();
		addRunningTaskPanel();
		
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
					JPanel runtask = panelMap.get(runningTask);
					runtask.setSize(getWidth() - 5, getHeight() - 180);
					JPanel task = panelMap.get(tasks);
					task.setSize(getWidth() - 5, task.getHeight());
					JPanel status = panelMap.get(statusbar);
					status.setBounds(0, getHeight() - 70, getWidth() - 5, status.getHeight());

					buttonMap.get(run).setLocation(task.getWidth() - 135,task.getHeight() - 40);
					buttonMap.get(send).setLocation(runtask.getWidth() - 245,runtask.getHeight() - 40);
					buttonMap.get(cancel).setLocation(runtask.getWidth() - 135,	runtask.getHeight() - 40);

					taskComboBox.setSize(task.getWidth() - 30, 20);

					tablePanel.setSize(runtask.getWidth() - 10, runtask.getHeight() - 70);
					tableScrollPane.setSize(runtask.getWidth() - 10, runtask.getHeight());
					tableScrollPane.revalidate();
					
					statusBar.setSize(status.getWidth(), status.getHeight());
				}
			}
			@Override
			public void componentShown(ComponentEvent e) {
				isShown = true;
			}
		});
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
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 5, 395, 90);
		panel.setBorder(BorderFactory.createTitledBorder(tasks));
		panel.add(taskComboBox);
		panel.add(addRunButton());
		panelMap.put(tasks, panel);
		add(panel);
	}

	/**
	 * combobox which show the tasks available
	 * 
	 * @return JComboBox
	 */
	private void addTaskComboBox() {
		taskComboBox = new JComboBox(boxModel);
		taskComboBox.setBounds(10, 20, 365, 20);
		if(0<taskComboBox.getItemCount())
			taskComboBox.setSelectedIndex(0);
		taskComboBox.setUI(new javax.swing.plaf.metal.MetalComboBoxUI() {
			public void layoutComboBox(Container parent,
					MetalComboBoxLayoutManager manager) {
				super.layoutComboBox(parent, manager);
				arrowButton.setBounds(0, 0, 0, 0);
			}
		});
	}

	/**
	 * add button to run the task
	 * 
	 * @return JButton
	 */
	private JButton addRunButton() {
		JButton runButton = new JButton();
		runButton.setBounds(260, 50, 100, 25);
		runButton.setText(run);
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runSelectedItem();
			}
		});
		buttonMap.put(run, runButton);
		return runButton;
	}
	
	private void runSelectedItem() {
		String taskID = (String)taskComboBox.getSelectedItem();
		File task = new File(taskID);
		if(!task.isDirectory()) {
			MessageDialog.info("Not a Directory", taskID + " is not a directory.");
			return;
		}
		
//		sendFiles(taskID);
		try {
			connection.sendFile("asdf.log");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		dirObserver(taskID);
//		logger.info("Observing directory: " + taskID);
		
		
		//TODO
//		ApplicationLauncher.open(program);
	}
	
	//TODO
	private void dirObserver(final String taskID) {
		DirectoryObserver dirObserver = new DirectoryObserver(taskID);
		dirObserver.subscribe(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				sendFiles(taskID);
			}
		});
		dirObserver.start();
	}
	
	private void sendFiles(String taskID) {
		File task = new File(taskID);
		String[] fileList = task.list();
		for(String f: fileList) {
			try {
				connection.sendFile(taskID + File.separator + f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * add running task panel
	 */
	private void addRunningTaskPanel() {
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

		taskTable = new TaskTable(tableModel);
		tableScrollPane = new JScrollPane(taskTable);
		tableScrollPane.setBounds(0, 0, tablePanel.getWidth(), tablePanel
				.getHeight());
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
			button.addActionListener(new ActionListener() {
				@Override
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
		int result = MessageDialog.yesNoDialog("Exit", "Do your really want to Quit?");
		switch(result) {
		case JOptionPane.YES_OPTION:
			System.exit(0);
			break;
		case JOptionPane.NO_OPTION:
			return;
		default:
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
		final KeyStroke configAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke logAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke exitAccelerator = null;
		final KeyStroke[] keyStrokes = { configAccelerator, logAccelerator, exitAccelerator };
		for (int i = 0; i < fileTitles.length; i++) {
			if(i == 2) {
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
					} else if(e.getActionCommand() == log) {
						LogDialog ld = new LogDialog();
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
