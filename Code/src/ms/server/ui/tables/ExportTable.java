package ms.server.ui.tables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ms.server.ui.dialogs.MessageDialog;
import ms.server.ui.models.TableModel;
import ms.server.ui.utils.Constants;
import ms.server.ui.utils.I18NManager;


public class ExportTable extends Table {

	private static final long serialVersionUID = 1L;
	
	private I18NManager manager = I18NManager.getManager();

	public ExportTable() {
		super(new TableModel());
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
		
		final String cancel = manager.getString("cancel");
		JMenuItem menuItem = new JMenuItem(cancel);
		menuItem.setIcon(new ImageIcon(getClass().getResource(Constants.UIIMAGE + Constants.CANCEL)));
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
