package ms.domain;

import java.util.ArrayList;
import java.util.Observable;

public class SendeListe extends Observable {

	ArrayList<ImportMedium> list = new ArrayList<ImportMedium>();

	public void add(ImportMedium m) {
		list.add(m);
	}

	public void remove(ImportMedium m) {
		list.remove(m);
	}

	public ArrayList<ImportMedium> getList() {
		return list;
	}
	
}
