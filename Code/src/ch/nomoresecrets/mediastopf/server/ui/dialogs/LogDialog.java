package ch.nomoresecrets.mediastopf.server.ui.dialogs;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import ch.nomoresecrets.mediastopf.server.log.Log;
import ch.nomoresecrets.mediastopf.server.ui.MediaStopfServer;

public class LogDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private JScrollPane scrollArea;
	private JCheckBox box;
	private HashMap<String, JButton> buttonMap = new HashMap<String, JButton>();
	private final String save = "Save as TXT", close = "Close";
	private boolean suspendThread = false;

	public LogDialog() {
		initGUI();
	}

	private void initGUI() {
		setTitle(MediaStopfServer.PROGRAM + " - Log");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setMinimumSize(new Dimension(500, 430));
		setSize(500, 430);
		setModal(true);
		setIconImage(new ImageIcon(getClass().getResource(MediaStopfServer.UIIMAGELOCATION + "icon.png")).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2, (dim.height - getHeight()) / 2);

		addButtons();
		addRefreshBox();
		addTextArea();
		addLogListener();
		
		addComponentListener(new ComponentAdapter() {
			private boolean isShown = false;
			@Override
		    public void componentResized(ComponentEvent e) {
				if(isShown) {
					scrollArea.setSize(getWidth()-15, getHeight()-80);
					scrollArea.revalidate();
					box.setLocation(10, getHeight()-60);
					buttonMap.get(save).setLocation(getWidth()-250, getHeight()-65);
					buttonMap.get(close).setLocation(getWidth()-140, getHeight()-65);
				}
			}
			@Override
		    public void componentShown(ComponentEvent e) {
				isShown = true;
			}
			@Override
		    public void componentHidden(ComponentEvent e) {
				suspendListener();
			}
		});
	}

	private void addTextArea() {
		textArea = new JTextArea("test");
		textArea.setEditable(false);
		textArea.setBorder(LineBorder.createBlackLineBorder());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		scrollArea = new JScrollPane(textArea);
		scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollArea.setBounds(5, 5, 485, 350);

		add(scrollArea);
	}

	private void addRefreshBox() {
		box = new JCheckBox("Auto Refresh");
		box.setSelected(true);
		box.setBounds(10, 370, 100, 20);
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (box.isSelected()) {
					resumeListener();
				} else {
					suspendListener();
				}
			}
		});
		add(box);
	}

	private void addLogListener() {
		Thread logListener = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					readLogContent();
					Thread.sleep(2000);
			        synchronized (this) {
			            while (suspendThread) {
			              wait();
			            }
			          }
				} catch (InterruptedException e) {
				}
			}

			private void readLogContent(){
				BufferedReader br;
				String readLine, logContent = "";
				try {
					br = new BufferedReader(new FileReader(new File(Log.getServerLog())));
					while ((readLine = br.readLine()) != null) {
						logContent += readLine + "\n";
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				textArea.setText(logContent);
			}
		});
		logListener.start();
	}

	private void suspendListener() {
		suspendThread = true;
	}

	private synchronized void resumeListener() {
		suspendThread = false;
		notify();
	}

	/**
	 * add buttons
	 */
	private void addButtons() {
		int x = 250;
		int y = 365;
		int width = 100;
		int height = 25;
		final String[] buttonText = { save, close };
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
		JFileChooser fileChooser = getFileChooser();
		fileFilter(fileChooser);

		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getName();
			String path = fileChooser.getCurrentDirectory().toString();
			
			String file = path + File.separator + filename;
			if(!filename.endsWith("txt")) {
				file += ".txt";
			}
			writeFile(file);
		}
	}

	private void writeFile(String file) {
		try {
			FileWriter fw = new FileWriter(new File(file));
			fw.write(textArea.getText().trim());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JFileChooser getFileChooser() {
		JFileChooser fileChooser = new JFileChooser() {

			private static final long serialVersionUID = 1L;

			@Override
            public void approveSelection() {
                File f = getSelectedFile();
                if(f.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
                            "The selected file already exists. " +
                            "Do you want to overwrite it?",
                            "The file already exists",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    switch(result)  {
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                        return;
                    }
                }
                super.approveSelection();
            }
		};
		return fileChooser;
	}

	private void fileFilter(JFileChooser fileChooser) {
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith(".txt") || file.isDirectory();
			}
			public String getDescription() {
				return "*.txt";
			}
		});
	}

	/**
	 * close
	 */
	private void close() {
		setVisible(false);
		dispose();
	}
}
