package ms.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class GUIComponents {
	
	public static JLabel createJLabel(URL url) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(url));
		label.setBorder(LineBorder.createBlackLineBorder());
		return label;
	}
	
	public static void initFrame(JFrame frame, URL url) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 430);
		frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setLayout(null);
		frame.setIconImage(new ImageIcon(url).getImage());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
	}
	
	
}
