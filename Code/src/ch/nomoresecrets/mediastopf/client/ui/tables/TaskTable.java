package ch.nomoresecrets.mediastopf.client.ui.tables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;

import ch.nomoresecrets.mediastopf.client.ui.dialogs.MessageDialog;
import ch.nomoresecrets.mediastopf.client.ui.models.TaskTableModel;

public class TaskTable extends JXTable {
	
	private static final long serialVersionUID = 1L;
	
	
	public TaskTable(TaskTableModel taskTableModel) {
		super(taskTableModel);
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
				@Override
				public void actionPerformed(ActionEvent event) {
					//TODO:
					if (event.getActionCommand() == send) {
						MessageDialog.info("todo..", "todo..");
					} else if (event.getActionCommand() == cancel) {
						MessageDialog.info("todo..", "todo..");
					}
				}
			});
			popupMenu.add(menuItem);
		}
		return popupMenu;
	}
}
