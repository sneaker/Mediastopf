package ms.ui.server;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import ms.ui.Constants;
import ms.ui.LogFrame;
import ms.utils.I18NManager;

/**
 * show trayicon
 */
public class SystemTrayIcon {
	
	private I18NManager manager = I18NManager.getManager();
	private MainView view;

	public SystemTrayIcon(MainView view) {
		this.view = view;

		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(getIcon(tray));
		} catch (AWTException e) {
			System.err.println("TrayIcon could not be added.");
		}
	}

	private TrayIcon getIcon(SystemTray tray) {
		Image image = new ImageIcon(getClass().getResource(Constants.UIIMAGE + Constants.ICON)).getImage();
		TrayIcon trayIcon = new TrayIcon(image, ServerConstants.PROGRAM, addPopUpMenu(tray));
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setVisible(!view.isVisible());
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
						view.setVisible(!view.isVisible());
					} else if(e.getActionCommand() == log) {
						LogFrame ld = new LogFrame(ServerConstants.class);
						ld.setVisible(true);
					} else if(e.getActionCommand() == exit){
						view.exit();
					}
				}
			});
			menu.add(item);
		}
		return menu;
	}
}
