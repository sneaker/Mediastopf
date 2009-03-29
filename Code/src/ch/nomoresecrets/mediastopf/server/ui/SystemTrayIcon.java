package ch.nomoresecrets.mediastopf.server.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ch.nomoresecrets.mediastopf.server.ui.dialogs.LogDialog;
import ch.nomoresecrets.mediastopf.server.ui.dialogs.MessageDialog;

public class SystemTrayIcon {
	
	private MediaStopfServer server;

	public SystemTrayIcon(MediaStopfServer server) {
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
		Image image = new ImageIcon(getClass().getResource(MediaStopfServer.UIIMAGELOCATION + "icon.png")).getImage();
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
						LogDialog ld = new LogDialog();
						ld.setVisible(true);
					} else if(e.getActionCommand() == exit){
						int result = MessageDialog.yesNoDialog("Exit", "Do your really want to Quit?");
						switch(result) {
						case JOptionPane.YES_OPTION:
							System.exit(0);
							break;
						case JOptionPane.NO_OPTION:
							return;
						default:
							return;
						}
					}
				}
			});
			menu.add(item);
		}
		return menu;
	}
}
