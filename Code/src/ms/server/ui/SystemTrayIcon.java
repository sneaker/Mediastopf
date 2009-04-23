package ms.server.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import ms.common.ui.LogFrame;
import ms.server.utils.I18NManager;

/**
 * show trayicon if possible
 * 
 * @author david
 *
 */
public class SystemTrayIcon {
	
	private I18NManager manager = I18NManager.getManager();
	private MainViewServer server;

	public SystemTrayIcon(MainViewServer server) {
		if (!SystemTray.isSupported()) {
			return;
		}
		this.server = server;

		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(getIcon(tray));
		} catch (AWTException e) {
			System.err.println("TrayIcon could not be added.");
		}
	}

	private TrayIcon getIcon(SystemTray tray) {
		Image image = new ImageIcon(getClass().getResource(Constants.UIIMAGE + Constants.ICON)).getImage();
		TrayIcon trayIcon = new TrayIcon(image, Constants.PROGRAM, addPopUpMenu(tray));
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.setVisible(!server.isVisible());
			}
		});
		return trayIcon;
	}

	private PopupMenu addPopUpMenu(SystemTray tray) {
		PopupMenu menu = new PopupMenu();

		final String toggle = manager.getString("Tray.minimize"), log = manager.getString("Tray.log"), exit = manager.getString("exit");
		final String[] items = { toggle, log, exit };
		for (int i = 0; i < items.length; i++) {
			if (2 < i) {
				menu.addSeparator();
			}
			MenuItem item = new MenuItem(items[i]);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == toggle) {
						server.setVisible(!server.isVisible());
					} else if(e.getActionCommand() == log) {
						LogFrame ld = new LogFrame();
						ld.setVisible(true);
					} else if(e.getActionCommand() == exit){
						server.exit();
					}
				}
			});
			menu.add(item);
		}
		return menu;
	}
}
