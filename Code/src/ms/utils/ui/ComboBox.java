package ms.utils.ui;

import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class ComboBox extends JComboBox {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComboBox(ComboBoxModel model, Rectangle bounds) {
		super(model);
		setBounds(bounds);
		setUI(new javax.swing.plaf.metal.MetalComboBoxUI() {
			public void layoutComboBox(Container parent, MetalComboBoxLayoutManager manager) {
				super.layoutComboBox(parent, manager);
				arrowButton.setBounds(0, 0, 0, 0);
			}
		});
	}
}
