package ms.common.domain;



public class ExportMedium {

	protected String name;
	protected int Speicherkapazitaet;
	protected int fk_Auftrag;
	protected int fk_Container;
	protected int id = -1;

	public ExportMedium() {
		super();
	}

	public int getSpeicherkapazitaet() {
		return Speicherkapazitaet;
	}

	public void setSpeicherkapazitaet(int Speicherkapazitaet) {
		this.Speicherkapazitaet = Speicherkapazitaet;
	}

	public int getFk_Auftrag() {
		return fk_Auftrag;
	}

	public void setFk_Auftrag(int fk_Auftrag) {
		this.fk_Auftrag = fk_Auftrag;
	}

	public int getID() {
		return id;
	}

}