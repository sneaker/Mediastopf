package ms.common.domain;



public class ImportMedium {

	protected String Name;
	protected int fk_Mediensammlung;
	protected int fk_Einlesegeraet;
	protected int id = -1;

	public ImportMedium() {
		super();
	}

	public int getID() {
		return id;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

}