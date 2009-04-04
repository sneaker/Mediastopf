package ms.server.ui.tables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ms.server.ui.dialogs.MessageDialog;
import ms.server.ui.models.TableModel;


public class ExportTable extends Table {

	private static final long serialVersionUID = 1L;

	public ExportTable(TableModel taskTableModel) {
		super(taskTableModel);
		initTable();
	}

	/**
	 * init Table
	 */
	private void initTable() {
		setComponentPopupMenu(createPopupMenu());
	}

	/**
	 * Popupmenu
	 * 
	 * @return popupMenu
	 */
	private JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();

		final String cancel = "Cancel";
		JMenuItem menuItem = new JMenuItem(cancel);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (event.getActionCommand() == cancel) {
					cancel();
				}
			}
		});
		popupMenu.add(menuItem);
		return popupMenu;
	}

	/**
	 * cancel send transaction
	 */
	// TODO
	public void cancel() {
		int row = getSelectedRow();
		if (row < 0) {
			MessageDialog.noneSelectedDialog();
			return;
		}
		int tasknum = (Integer) getValueAt(row, 0);
		String status = (String) getValueAt(row, 1);

		MessageDialog.info("Canceling Export Tasknr: " + tasknum + " - with status: "
				+ status, "Not sending anything, just a test");
	}
}
