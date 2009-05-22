package ms.domain;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Verwaltet Dateien, welche zur Übertragung auf den Server bereit sind.
 * 
 * @see ms.utils.networking.client.ImportMediumSender;
 * 
 */
public class SendeListe extends Observable {

	ArrayList<ImportMedium> list = new ArrayList<ImportMedium>();

	/**
	 * Neue Sammlung von Dateien hinzufügen, die bereit zur Übertragung ist.
	 * 
	 * @param m
	 *            eine Sammlung von importierten Dateien
	 */
	public synchronized void add(ImportMedium m) {
		synchronized (list) {
			list.add(m);
		}
	}

	/**
	 * Eine Sammlung von Dateien aus der Liste der zu sendenden Dateien
	 * entfernen, damit sie nicht auf den Server übertragen werden.
	 * 
	 * @param m
	 */
	public synchronized void remove(ImportMedium m) {
		synchronized (list) {
			list.remove(m);
		}
	}

	/**
	 * Liefert die Liste der Importmedien zurück, die sich in der SendeListe
	 * befinden.
	 * 
	 * @return die Liste der ImportMedien, welche zum Versand bereit sind.
	 */
	public synchronized ArrayList<ImportMedium> getList() {
		synchronized (list) {
			return list;
		}
	}

}
