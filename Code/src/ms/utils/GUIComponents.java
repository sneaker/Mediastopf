package ms.utils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

public class GUIComponents {
	
	private static I18NManager manager = I18NManager.getManager();
	
	/**
	 * create a JLabel with a icon
	 * 
	 * @param url iconstring
	 * @return jlabel
	 */
	public static JLabel createJLabel(URL url) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(url));
		label.setBorder(LineBorder.createBlackLineBorder());
		return label;
	}
	
	/**
	 * init a jframe
	 * 
	 * @param frame jframe to init
	 * @param url iconstring
	 * @param dim dimension of frame
	 */
	public static void initFrame(JFrame frame, URL url, Dimension dim) {
		frame.setSize(dim);
		frame.setIconImage(new ImageIcon(url).getImage());
		Dimension dim1 = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dim1.width - frame.getWidth()) / 2, (dim1.height - frame.getHeight()) / 2);
		frame.setLayout(null);
		frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
	}
	
	/**
	 * init a dialog
	 * 
	 * @param dialog jdialog to init
	 * @param title of jdialog
	 * @param url icon of dialog
	 * @param size of dialog
	 * @param closeOperation of dialog
	 */
	public static void initDialog(JDialog dialog, String title, URL url, Dimension size, int closeOperation) {
		dialog.setTitle(title);
		dialog.setSize(size);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((dim.width - dialog.getWidth()) / 2, (dim.height - dialog.getHeight()) / 2);
		dialog.setLayout(null);
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setIconImage(new ImageIcon(url).getImage());
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * create jpanel
	 * 
	 * @param bounds of jpanel
	 * @return jpanel
	 */
	public static JPanel createPanel(Rectangle bounds) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(bounds);
		return panel;
	}
	
	/**
	 * create jpanel with a border
	 * 
	 * @param bounds of jpanel
	 * @param border of jpanel
	 * @return jpanel
	 */
	public static JPanel createPanel(Rectangle bounds, TitledBorder border) {
		JPanel panel = createPanel(bounds);
		panel.setBorder(border);
		return panel;
	}
	
	/**
	 * create jcombobox
	 * 
	 * @param model which jcombobox should use
	 * @param bounds of jcombobox
	 * @return jcombobox
	 */
	public static JComboBox createComboBox(ComboBoxModel model, Rectangle bounds) {
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
	
	/**
	 * create jtextfield
	 * 
	 * @param text of jtextfield
	 * @param bounds of jtextfield
	 * @return jtextfield
	 */
	public static JTextField createTextField(String text, Rectangle bounds) {
		JTextField textField = new JTextField();
		textField.setText(text);
		textField.setBounds(bounds);
		textField.setEditable(false);
		return textField;
	}
	
	/**
	 * create jtextfield with a tooltip
	 * 
	 * @param text of jtextfield
	 * @param bounds of jtextfield
	 * @param tooltip of jtextfield
	 * @return jtextfield
	 */
	public static JTextField createTextField(String text, Rectangle bounds, String tooltip) {
		JTextField textField = createTextField(text, bounds);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setOpaque(false);
		return textField;
	}
	
	/**
	 * create a textarea
	 * 
	 * @return jtextarea
	 */
	public static JTextArea createTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBorder(LineBorder.createBlackLineBorder());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		return textArea;
	}

	/**
	 * create a jscrollpane
	 * 
	 * @param comp which use a jscrollpane
	 * @param bounds of jscrollpane
	 * @return jscrollpane
	 */
	public static JScrollPane createJScrollPane(JComponent comp, Rectangle bounds) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(comp);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(bounds);
		return scrollPane;
	}
	
	/**
	 * create a jbutton wit an icon
	 * 
	 * @param bounds of jbutton
	 * @param buttonText of jbutton
	 * @param mnemonic of jbutton
	 * @param url icon of jbutton
	 * @return jbutton
	 */
	public static JButton createButton(Rectangle bounds, String buttonText, int mnemonic, URL url) {
		JButton button = createButton(bounds, buttonText, mnemonic);
		button.setIcon(new ImageIcon(url));
	    return button;
	}
	
	/**
	 * create a jbutton
	 * 
	 * @param bounds of jbutton
	 * @param buttonText of jbutton
	 * @param mnemonic of jbutton
	 * @return jbutton
	 */
	public static JButton createButton(Rectangle bounds, String buttonText, int mnemonic) {
		JButton button = new JButton();
		button.setBounds(bounds);
		button.setText(buttonText);
		button.setMnemonic(mnemonic);
	    button.setVerticalTextPosition(JButton.CENTER);
	    button.setHorizontalTextPosition(JButton.RIGHT);
	    return button;
	}
	
	/**
	 * create a jfilechooser
	 * 
	 * @return jfilechooser
	 */
	public static JFileChooser getFileChooser() {
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
	
	/**
	 * jfilechooser filefilter
	 * 
	 * @param fileChooser jfilechooser
	 * @param postfix to filter
	 */
	public static void jFileFilter(JFileChooser fileChooser, final String postfix) {
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith(postfix)
						|| file.isDirectory();
			}

			public String getDescription() {
				return "*" + postfix;
			}
		});
	}
	
	/**
	 * an esc listener to dialog
	 * 
	 * @param dialog jdialog
	 * @param listener actionlistener
	 */
	public static void addESCListener(JDialog dialog, ActionListener listener) {
		JRootPane rootPane = dialog.getRootPane();
		rootPane.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	/**
	 * create jmenuitem
	 * 
	 * @param text of jmenuitem
	 * @param acc accelerator of jmenuitem
	 * @return jmenuitem
	 */
	public static JMenuItem createMenuItem(String text, KeyStroke acc) {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText(text);
		menuItem.setAccelerator(acc);
		return menuItem;
	}
}
