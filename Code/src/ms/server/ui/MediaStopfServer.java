package ms.server.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
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

import ms.server.Server;
import ms.server.logic.ExportRunningList;
import ms.server.logic.ImportRunningList;
import ms.server.logic.TaskList;
import ms.server.ui.dialogs.AboutDialog;
import ms.server.ui.dialogs.ExportDialog;
import ms.server.ui.dialogs.LogDialog;
import ms.server.ui.dialogs.MessageDialog;
import ms.server.ui.models.ExportTableModel;
import ms.server.ui.models.ImportTableModel;
import ms.server.ui.models.TaskComboBoxModel;
import ms.server.ui.tables.ExportTable;
import ms.server.ui.tables.ImportTable;


public class MediaStopfServer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String PROGRAM = "MediaStopf Server";
	public static final String UIIMAGELOCATION = "/ms/server/ui/images/";

	private TaskComboBoxModel boxModel;
	private ImportTableModel importTableModel;
	private ExportTableModel exportTableModel;
	private JComboBox taskComboBox;
	private JPanel tablePanel;
	private ImportTable importTable;
	private ExportTable exportTable;
	private JTextField statusBar;
	private JTabbedPane tabPane;
	private TaskList taskList;
	private ImportRunningList importRunningList;
	private ExportRunningList exportRunningList;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private HashMap<String, JPanel> panelMap = new HashMap<String, JPanel>();
	private String export = "Export", cancel = "Cancel", runningTask = "Running Tasks", tasks = "Tasks", statusbar = "StatusBar";
	
	public MediaStopfServer(Server server) {
		taskList = new TaskList(server);
		boxModel = new TaskComboBoxModel(taskList);
		importRunningList = new ImportRunningList();
		exportRunningList = new ExportRunningList();
		importTableModel = new ImportTableModel(importRunningList);
		exportTableModel = new ExportTableModel(exportRunningList);

		initGUI();
	}

	/**
	 * init GUI Components
	 */
	private void initGUI() {
		setTitle(PROGRAM);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(400, 450));
		setSize(400, 450);
		setIconImage(new ImageIcon(getClass().getResource(UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);
		setJMenuBar(createMenuBar());

		addTrayIcon();
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
					int width = getWidth() - 5;
					int height = getHeight() - 70;
					
					JPanel runtaskPanel = panelMap.get(runningTask);
					runtaskPanel.setSize(width, height - 110);
					JPanel taskPanel = panelMap.get(tasks);
					taskPanel.setSize(width, taskPanel.getHeight());
					JPanel statusPanel = panelMap.get(statusbar);
					statusPanel.setBounds(0, height, width, statusPanel.getHeight());

					updateComponentBounds(runtaskPanel, taskPanel, statusPanel);
				}
			}

			@Override
			public void componentShown(ComponentEvent e) {
				isShown = true;
			}
		});
	}
	
	private void updateComponentBounds(JPanel runtask, JPanel task, JPanel status) {
		buttonMap.get(export).setLocation(task.getWidth() - 135,task.getHeight() - 40);
		
		int width = runtask.getWidth() - 10;
		int height = runtask.getHeight() - 40;
		buttonMap.get(cancel).setLocation(width - 125, height);
		tablePanel.setSize(width, height - 30);
		
		taskComboBox.setSize(task.getWidth() - 30, 20);

		tabPane.setSize(tablePanel.getWidth(), tablePanel.getHeight());
		
		statusBar.setSize(status.getWidth(), status.getHeight());
	}
	
	private void addTrayIcon() {
		new SystemTrayIcon(this);
	}
	
	private void addStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 380, 450, 20);
		panel.setBorder(BorderFactory.createTitledBorder(statusbar));
		panelMap.put(statusbar, panel);
		
		statusBar = new JTextField("(C)2009 MediaStopf Server");
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
		panel.add(addExportButton());
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
			public void layoutComboBox(Container parent, MetalComboBoxLayoutManager manager) {
				super.layoutComboBox(parent, manager);
				arrowButton.setBounds(0, 0, 0, 0);
			}
		});
	}

	/**
	 * add button to export the task to a medium
	 * 
	 * @return JButton
	 */
	private JButton addExportButton() {
		JButton exportButton = new JButton();
		exportButton.setBounds(260, 50, 100, 25);
		exportButton.setText(export);
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportSelectedItem();
			}
		});
		buttonMap.put(export, exportButton);
		return exportButton;
	}
	
	private void exportSelectedItem() {
		String tasknum = (String)taskComboBox.getSelectedItem();
		File file = new File(tasknum);
		if(!file.isDirectory()) {
			MessageDialog.info("Not found", "No directory of " + tasknum + " found");
			return;
		}
		ExportDialog ed = new ExportDialog(tasknum);
		ed.setVisible(true);
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
		
		importTable = new ImportTable(importTableModel);
		exportTable = new ExportTable(exportTableModel);
		
		tabPane = new JTabbedPane();
		tabPane.setBounds(0, 5, tablePanel.getWidth(), tablePanel.getHeight());
		tabPane.addTab("Import", new JScrollPane(importTable));
		tabPane.addTab("Export", new JScrollPane(exportTable));
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

	/**
	 * add button for the running tasks
	 * 
	 * @param panel
	 *            JPanel
	 */
	private void addRunningTaskButtons(JPanel panel) {
		int x = 260;
		int y = 230;
		int width = 100;
		int height = 25;
		JButton button = new JButton();
		button.setBounds(new Rectangle(x, y, width, height));
		button.setText(cancel);
		button.setMnemonic(KeyEvent.VK_C);
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == cancel) {
					exportTable.cancel();
				}
			}
		});
		panel.add(button);
		buttonMap.put(cancel, button);
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
		final String log = "Log", exit = "Exit";
		final String[] fileTitles = { log, exit };
		final KeyStroke configAccelerator = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
		final KeyStroke exitAccelerator = null;
		final KeyStroke[] keyStrokes = { configAccelerator, exitAccelerator };
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
