package ms.client.ui.tables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

import ms.client.ui.dialogs.MessageDialog;
import ms.client.ui.models.TaskTableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;


public class TaskTable extends JXTable {
	
	private static final long serialVersionUID = 1L;
	
	
	public TaskTable() {
		super(new TaskTableModel());
		initTable();
	}
	
	/**
	 * init Table
	 */
	private void initTable() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setComponentPopupMenu(createPopupMenu());
		setSortOrder(0, SortOrder.ASCENDING);
		setHighlighters(HighlighterFactory.createSimpleStriping(HighlighterFactory.GENERIC_GRAY));
	}
	
	/**
	 * Popupmenu
	 * 
	 * @return popupMenu
	 */
	private JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		
		final String send = "Send", cancel = "Cancel";
		final String[] itemLabel = { send, cancel };
		for (int i = 0; i < itemLabel.length; i++) {
			JMenuItem menuItem = new JMenuItem(itemLabel[i]);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (event.getActionCommand() == send) {
						send();
					} else if (event.getActionCommand() == cancel) {
						cancel();
					}
				}
			});
			popupMenu.add(menuItem);
		}
		return popupMenu;
	}
	
	/**
	 * send files to server
	 */
	//TODO
	public void send() {
		int row = getSelectedRow();
		if (row < 0) {
			MessageDialog.noneSelectedDialog();
			return;
		}
		int tasknum = (Integer) getValueAt(row, 0);
		String status = (String) getValueAt(row, 1);
		
		MessageDialog.info("Sending Tasknr: " + tasknum + " - with status: " + status, "Not sending anything, just a test");
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
		
		MessageDialog.info("Canceling Tasknr: " + tasknum + " - with status: " + status, "Not sending anything, just a test");
	}
}
