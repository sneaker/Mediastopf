package ch.nomoresecrets.mediastopf.server.ui.tables;

import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.SortOrder;

import ch.nomoresecrets.mediastopf.server.ui.models.ImportTableModel;

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
