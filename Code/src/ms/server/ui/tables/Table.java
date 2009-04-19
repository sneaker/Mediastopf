package ms.server.ui.tables;

import javax.swing.ListSelectionModel;

import ms.server.ui.models.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;


public class Table extends JXTable {

	private static final long serialVersionUID = 1L;

	public Table() {
		super(new TableModel());
		initTable();
	}
	
	public Table(TableModel tableModel) {
		super(tableModel);
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
