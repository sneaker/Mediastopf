
package ch.nomoresecrets.mediastopf.client.ui.dialogs;

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
}
