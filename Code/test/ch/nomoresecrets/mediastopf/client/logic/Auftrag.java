package ch.nomoresecrets.mediastopf.client.logic;

public class Auftrag {

	public String auftragsID;
	public String kundeVorname, kundeNachname;
	public int anzahlMedientraeger;
	
	public Auftrag(String id, String nachname, String vorname) {
		auftragsID = id;
		kundeNachname = nachname;
		kundeVorname = vorname;
	}

	int getEstimatedMinutes () {
		System.err.println("Not yet implemented");
		return -1;
	}
	
	String getFullCustomerName() {
		return kundeNachname + ", " + kundeVorname;
	}
}
