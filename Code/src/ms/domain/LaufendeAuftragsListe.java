package ms.domain;

import java.util.Observable;

public class LaufendeAuftragsListe extends MSListen {

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
		if (list.isEmpty())
			return;
			
		for (Auftrag a : list) {
			if (a.status == 3 || a.status == 4)
				remove(a);
		}
	}
}
