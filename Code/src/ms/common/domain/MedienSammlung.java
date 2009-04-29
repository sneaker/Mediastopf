package ms.common.domain;
import java.util.List;

public class MedienSammlung {

	protected String name;
	protected int status = -1;
	protected int typ;
	protected int id = -1;
	protected List<ImportMedium> ListImportMedium;

	public MedienSammlung(int newtyp, int newstatus, String newname) {
		typ = newtyp;
		name = newname;
		status = newstatus;
	}
	
	public MedienSammlung(int newtyp, int newstatus, String newname, List<ImportMedium> newIMList){
		typ = newtyp;
		name = newname;
		status = newstatus;
		ListImportMedium = newIMList;
	}

	public int getTyp() {
		return typ;
	}

	public void setTyp(int typ) {
		this.typ = typ;
	}

	public int getID() {
		return id;
	}

}