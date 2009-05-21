package ms.utils.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import ms.utils.I18NManager;

public class FileChooser extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static I18NManager manager = I18NManager.getManager();

	public void approveSelection() {
		File f = getSelectedFile();
		if (f.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
					manager.getString("Log.fileoverwritemessage"), manager
							.getString("Log.fileoverwritetitle"),
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

	public void setFileFilter(final String postfix) {
		super.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith("." + postfix)
						|| file.isDirectory();
			}

			public String getDescription() {
				return "*." + postfix;
			}
		});
	}
}
