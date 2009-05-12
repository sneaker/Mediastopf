package ms.common.domain;

import java.io.Serializable;
import java.util.ArrayList;


public class Auftrag implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int status = -1;
	protected int id = -1;
	protected ArrayList<ImportMedium> ListImportMedium;
	protected ArrayList<ExportMedium> ListExportMedium;

	public boolean addImportMedium(ImportMedium newSammlung) {
		return ListImportMedium.add(newSammlung);
	}

	public MedienSammlung getMedienSammlung(int Index) {
		return ListMediensammlung.get(Index);
	}

	public MedienSammlung removeMedienSammlung(int Index) {
		return ListMediensammlung.remove(Index);
	}

	public boolean addExportMedium(ExportMedium newMedium) {
		return ListExportMedium.add(newMedium);
	}

	public ExportMedium getExportMedium(int Index) {
		return ListExportMedium.get(Index);
	}

	public ExportMedium removeExportMedium(int Index) {
		return ListExportMedium.remove(Index);
	}

	public Auftrag(int newstatus) {
		status = newstatus;
		ListImportMedium = new ArrayList<ImportMedium>();
		ListExportMedium = new ArrayList<ExportMedium>();
	}

	public Auftrag(int newstatus, ArrayList<MedienSammlung> newMSList,
			List<ExportMedium> newEMList) {
		status = newstatus;
		ListImportMedium = newIMList;
		ListExportMedium = newEMList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getID() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Auftrag) {
			Auftrag task = (Auftrag) obj;
			// TODO: AUFTRAG - Anpassen auf neue statusmeldungen
			/*
			 * return ((status == 0 ? task.getStatus() == 0 :
			 * status.equalsIgnoreCase(task.getStatus()) && (id == 0 ?
			 * task.getID() == 0 : id == task.getID())));
			 * 
			 * Quickfix:
			 */
			return ((task.getID() == this.id));
		}
		return false;
	}

}
