package ms.ui.tables.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ms.application.client.ClientController;
import ms.domain.MSListen;
import ms.ui.client.ClientConstants;
import ms.ui.dialogs.MessageDialog;
import ms.ui.models.TaskTableModel;
import ms.ui.tables.Table;
import ms.utils.I18NManager;


public class TaskTable extends Table {
	
	private static final long serialVersionUID = 1L;
	
	private I18NManager manager = I18NManager.getManager();
	
	public TaskTable(MSListen list) {
		super(new TaskTableModel(list));
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
		JMenuItem menuItem = new JMenuItem(manager.getString("send"));
		menuItem.setIcon(new ImageIcon(getClass().getResource(ClientConstants.UIIMAGE + ClientConstants.SEND)));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				send();
			}
		});
		popupMenu.add(menuItem);
		return popupMenu;
	}
	
	/**
	 * send files to server
	 */
	//TODO: add send functionality
	public int send() {
		int row = getSelectedRow();
		if (row < 0) {
			MessageDialog.noneSelectedDialog();
			return -1;
		}
		int tasknum = (Integer) getValueAt(row, 0);
		String status = getValueAt(row, 1).toString();
		
		ClientController.addForSending(tasknum);
		
		//MessageDialog.info("Sending Tasknr: " + tasknum + " - with status: " + status, "added to sending list");
		return row;
		//TODO: remove the id from the list
		//TODO: dont forget to remove the files
	}
}
