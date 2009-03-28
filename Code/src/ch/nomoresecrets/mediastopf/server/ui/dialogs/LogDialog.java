package ch.nomoresecrets.mediastopf.server.ui.dialogs;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import ch.nomoresecrets.mediastopf.server.log.Log;
import ch.nomoresecrets.mediastopf.server.ui.MediaStopfServer;

public class LogDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
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
	}

	private void addTextArea() {
		textArea = new JTextArea("test");
		textArea.setEditable(false);
		textArea.setBounds(5, 5, 485, 350);
		textArea.setBorder(LineBorder.createBlackLineBorder());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

		add(textArea);
	}

	private void addRefreshBox() {
		final JCheckBox box = new JCheckBox("Auto Refresh");
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

					} else if (e.getActionCommand() == close) {
						close();
					}
				}
			});
			add(button);
			buttonMap.put(buttonText[i], button);
		}
	}

	/**
	 * close
	 */
	private void close() {
		setVisible(false);
		dispose();
	}
}
