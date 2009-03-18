package ch.nomoresecrets.mediastopf.filesys;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

/**
 * Überwacht einen Dateisystempfad und meldet Veränderungen seinen Abonnenten.
 * 
 * @author mm
 * 
 */
public class WatchDirectory extends Observable {

	private String _directoryPath = null;
	private Calendar _lastScan = null;
	private static final int DEFAULT_REFRESH_RATE_SECONDS = 10;
	private int _refreshRateSeconds = DEFAULT_REFRESH_RATE_SECONDS;

	public WatchDirectory(String directoryPath) {
		this._directoryPath = directoryPath;
		this._lastScan = Calendar.getInstance();
		new FilePoller(this).start();
	}

	public void subscribe(Observable blah) {
		System.out.println("Es subscribded");
	}

	public void unsubscribe() {
		System.out.println("Es unsubscribed...");
	}

	public void notity() {
		System.out.println("Es notifiziert :-)");
	}

	public boolean hasChanged() {
		// TODO Auto-generated method stub
		System.out.println("Hat sich was geändert?");
		return false;
	}

	// public List<File> newFiles() {
	// return null;
	// }

}
