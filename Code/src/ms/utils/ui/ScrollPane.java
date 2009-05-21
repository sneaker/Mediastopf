package ms.utils.ui;

import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScrollPane(JComponent comp, Rectangle bounds) {
		setViewportView(comp);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		setBounds(bounds);
	}
}
