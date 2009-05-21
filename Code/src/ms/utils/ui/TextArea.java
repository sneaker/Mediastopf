package ms.utils.ui;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class TextArea extends JTextArea {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextArea() {
		setEditable(false);
		setBorder(LineBorder.createBlackLineBorder());
		setWrapStyleWord(true);
		setLineWrap(true);
		setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
	}
}
