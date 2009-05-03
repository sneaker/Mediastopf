package ms.common.domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ImportMedium implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String Name;
	protected int fk_Mediensammlung;
	protected int fk_Einlesegeraet;
	protected int id = -1;
	protected ArrayList<File> items;

	public ImportMedium() {
		super();
		items = new ArrayList<File>();
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

	public void addItem(File item)
	{
		items.add(item);
	}
	
	public ArrayList<File> getItemsbyFile()
	{
		return items;
	}
}