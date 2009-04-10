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

import ms.server.ui.utils.Constants;


public class SystemTrayIcon {
	
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
		TrayIcon trayIcon = new TrayIcon(image, "MediaStopf Server", addPopUpMenu(tray));
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

		final String open = "Open", hide = "Hide", log = "Log", exit = "Exit";
		final String[] items = { open, hide, log, exit };
		for (int i = 0; i < items.length; i++) {
			if (2 < i) {
				menu.addSeparator();
			}
			MenuItem item = new MenuItem(items[i]);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand() == open) {
						server.setVisible(true);
					} else if(e.getActionCommand() == hide) {
						server.setVisible(false);
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
