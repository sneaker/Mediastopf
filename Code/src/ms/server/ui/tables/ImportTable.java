package ms.server.ui.tables;

import javax.swing.ListSelectionModel;

import ms.server.ui.models.ImportTableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;


public class ImportTable extends JXTable {

	private static final long serialVersionUID = 1L;

	public ImportTable(ImportTableModel taskTableModel) {
		super(taskTableModel);
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
