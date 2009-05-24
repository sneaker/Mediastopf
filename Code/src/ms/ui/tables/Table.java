package ms.ui.tables;

import javax.swing.ListSelectionModel;

import ms.domain.TaskList;
import ms.ui.models.TaskTableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;


public class Table extends JXTable {

	private static final long serialVersionUID = 1L;

	public Table(TaskList list) {
		super(new TaskTableModel(list));
		initTable();
	}
	
	public Table(TaskTableModel model) {
		super(model);
		initTable();
	}
	
	/**
	 * init Table
	 */
	private void initTable() {
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setSortOrder(0, SortOrder.ASCENDING);
		setHighlighters(HighlighterFactory.createSimpleStriping(HighlighterFactory.GENERIC_GRAY));
	}
}
