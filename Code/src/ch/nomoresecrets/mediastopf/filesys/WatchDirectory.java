package ch.nomoresecrets.mediastopf.filesys;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

public class WatchDirectory {



	private String _directoryPath = null;
	private Calendar _lastScan = null;
	private static final int DEFAULT_REFRESH_RATE_SECONDS = 10;
	private int _refreshRateSeconds = DEFAULT_REFRESH_RATE_SECONDS;

	public WatchDirectory(String directoryPath) {
		this._directoryPath = directoryPath;
		this._lastScan = Calendar.getInstance();
		new FilePoller().start();
	}

	public void subscribe(Observable blah) {

	}

	public void unsubscribe() {

	}

	public void notity() {
		// notify all subscribers
	}

	public boolean hasChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	// public boolean hasChanged () {
	// return false;
	// }

	// public List<File> newFiles() {
	// return null;
	// }

}
