package ms.domain;

import java.util.Observable;

public class LaufendeAuftragsListe extends MSListen {

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
}
