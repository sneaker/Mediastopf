package ms.domain;

import java.util.Iterator;
import java.util.Observable;

public class LaufendeAuftragsListe extends MediaStopfListe {

	@Override
	public void add(Auftrag auftrag) {
		
		if (list.contains(auftrag))
			return;
				
		list.add(auftrag);
		setChanged();
		notifyObservers();
	}
	
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
	
	public void clean()
	{
		Iterator<Auftrag> it = list.iterator();
		while(it.hasNext()) {
			Auftrag a = it.next();
			if (a.getStatus() == 3 || a.getStatus() == 4) {
				it.remove();
				// observers must know about the list change, otherwhise access to non-existing element
				setChanged();
				notifyObservers();
			}
		}
	}
	
}
