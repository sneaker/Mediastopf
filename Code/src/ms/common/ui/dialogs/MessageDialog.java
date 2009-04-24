package ms.common.ui.dialogs;

import javax.swing.JOptionPane;

import ms.common.utils.I18NManager;

public class MessageDialog {
	
	private static I18NManager manager = I18NManager.getClientManager();
	
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
		JOptionPane.showMessageDialog(null, manager.getString("Dialog.notaskmessage"), manager.getString("Dialog.notasktitle"), JOptionPane.INFORMATION_MESSAGE);
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
