package ms.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class SendeListe extends Observable {

	List<ImportMedium> list = new Vector<ImportMedium>();

	public void add(ImportMedium m) {
		list.add(m);
	}

	public void remove(ImportMedium m) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<ImportMedium> getList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
