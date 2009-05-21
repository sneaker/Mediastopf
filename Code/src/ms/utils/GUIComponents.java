package ms.utils;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

public class GUIComponents {
	
	private static I18NManager manager = I18NManager.getManager();
	
	public static JLabel createJLabel(URL url, Border border) {
		JLabel label = createJLabel(url);
		label.setBorder(border);
		return label;
	}
	
	private static JLabel createJLabel(URL url) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(url));
		return label;
	}
	
	public static JLabel createJLabel(URL url, Rectangle bounds) {
		JLabel label = createJLabel(url);
		label.setBounds(bounds);
		return label;
	}
	
	public static JLabel createJLabel(String text, Rectangle bounds) {
		JLabel label = new JLabel(text);
		label.setBounds(bounds);
		label.setForeground(Color.RED);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.RED));
		return label;
	}
	
	public static void initJFrame(JFrame frame, String title, URL url, Dimension size, int closeOperation) {
		frame.setTitle(title);
		frame.setSize(size);
		frame.setIconImage(new ImageIcon(url).getImage());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
		frame.setLayout(null);
		frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setDefaultCloseOperation(closeOperation);
	}
	
	public static void initJDialog(JDialog dialog, String title, URL url, Dimension size) {
		dialog.setTitle(title);
		dialog.setSize(size);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((screenSize.width - dialog.getWidth()) / 2, (screenSize.height - dialog.getHeight()) / 2);
		dialog.setLayout(null);
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setIconImage(new ImageIcon(url).getImage());
	}
	
	public static JPanel createJPanel(Rectangle bounds) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(bounds);
		return panel;
	}
	
	public static JPanel createPanel(Rectangle bounds, Border border) {
		JPanel panel = createJPanel(bounds);
		panel.setBorder(border);
		return panel;
	}
	
	public static JComboBox createJComboBox(ComboBoxModel model, Rectangle bounds) {
		JComboBox box = new JComboBox(model);
		box.setBounds(bounds);
		box.setUI(new javax.swing.plaf.metal.MetalComboBoxUI() {
			public void layoutComboBox(Container parent, MetalComboBoxLayoutManager manager) {
				super.layoutComboBox(parent, manager);
				arrowButton.setBounds(0, 0, 0, 0);
			}
		});
		return box;
	}
	
	public static JTextField createJTextField(Rectangle bounds) {
		return createJTextField("", bounds);
	}
	
	public static JTextField createJTextField(String text, Rectangle bounds) {
		JTextField textField = new JTextField();
		textField.setText(text);
		textField.setBounds(bounds);
		return textField;
	}
	
	public static JTextField createJTextField(String text, Rectangle bounds, String tooltip) {
		JTextField textField = createJTextField(text, bounds);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setOpaque(false);
		return textField;
	}
	
	public static JTextArea createJTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBorder(LineBorder.createBlackLineBorder());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		return textArea;
	}

	public static JScrollPane createJScrollPane(JComponent comp, Rectangle bounds) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(comp);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(bounds);
		return scrollPane;
	}
	
	public static JButton createJButton(Rectangle bounds, String command) {
		JButton button = createJButton(bounds);
		button.setActionCommand(command);
		return button;
	}
	
	public static JButton createJButton(Rectangle bounds, String buttonText, int mnemonic, URL url) {
		JButton button = createJButton(bounds, buttonText, mnemonic);
		button.setIcon(new ImageIcon(url));
	    return button;
	}
	
	public static JButton createJButton(Rectangle bounds, String buttonText, int mnemonic) {
		JButton button = createJButton(bounds, buttonText);
		button.setText(buttonText);
		button.setMnemonic(mnemonic);
	    button.setVerticalTextPosition(JButton.CENTER);
	    button.setHorizontalTextPosition(JButton.RIGHT);
	    return button;
	}
	
	private static JButton createJButton(Rectangle bounds) {
		JButton button = new JButton();
		button.setBounds(bounds);
	    return button;
	}
	
	private static JButton createJButton(Rectangle bounds, String command, URL url) {
		JButton button = createJButton(bounds, command);
		button.setIcon(new ImageIcon(url));
	    return button;
	}
	
	public static JButton createJButton(Rectangle bounds, String command, URL url, String tooltip) {
		JButton button = createJButton(bounds, command, url);
		button.setToolTipText(tooltip);
		return button;
	}
	
	public static JButton createJButton(Rectangle bounds, URL url, String tooltip) {
		return createJButton(bounds, "", url, tooltip);
	}
	
	public static JFileChooser getJFileChooser() {
		JFileChooser fileChooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;
			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (f.exists() && getDialogType() == SAVE_DIALOG) {
					int result = JOptionPane.showConfirmDialog(
							getTopLevelAncestor(),
							manager.getString("Log.fileoverwritemessage"),
							manager.getString("Log.fileoverwritetitle"),
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					switch (result) {
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
	
	public static void getFileFilter(JFileChooser fileChooser, final String postfix) {
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith("." + postfix)
						|| file.isDirectory();
			}

			public String getDescription() {
				return "*." + postfix;
			}
		});
	}
	
	public static void addESCListener(JDialog dialog, ActionListener listener) {
		JRootPane rootPane = dialog.getRootPane();
		rootPane.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	public static JMenuItem createJMenuItem(String text, KeyStroke acc) {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText(text);
		menuItem.setAccelerator(acc);
		return menuItem;
	}
}
