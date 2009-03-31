package ms.client.ui.dialogs;

import javax.swing.JOptionPane;

public class MessageDialog {
	
	/**
	 * show a information dialog
	 */
	public static void info(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * show a information dialog
	 */
	public static void noneSelectedDialog() {
		JOptionPane.showMessageDialog(null, "Choose a Task", "No Task selected", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * show a dialog with yes/ no button
	 * 
	 * @param title dialog title
	 * @param message messagetext
	 * @return int value of "yes"/ "no"
	 */
	public static int yesNoDialog(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
	}
}
