package ms.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ms.utils.ImageWhiteFilter;
import ms.utils.networking.server.NoDestinationSpecifiedExecpetion;

/**
 * Repraesentiert ein digitalisiertes Medium eines Kunden, welches nach dem
 * Import aus einzelnen Dateien besteht.
 */
public class ImportMedium implements Serializable {

	private static final long serialVersionUID = -6136660202504695049L;
	private String name;
	private int parentid = -1;
	private int status = -1;
	private ArrayList<ImportItem> items;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setId(int id) {
		this.parentid = id;
	}

	public ImportMedium(ResultSet row) throws SQLException {
		this.name = row.getString("Name");
		this.parentid = row.getInt("id");
		this.status = row.getInt("status");
	}
	
	public ImportMedium(File folder) throws FileNotFoundException {
		this(folder.getAbsolutePath());
	}

	public ImportMedium(String folder) throws FileNotFoundException {
		File _folder = new File(folder);
		if (!_folder.exists())
			throw new FileNotFoundException("Cannot find folders to read import medias");
		items = new ArrayList<ImportItem>();
		for (String filename : _folder.list()) {
			String filepath = folder + File.separator + filename;
			File f = new File(filepath);
			if (ImageWhiteFilter.isImage(f)) {
				if(ImageWhiteFilter.analyzeImageFile(f)) {
					continue;
				}
			}
			items.add(new ImportItem(filepath));			
		}
	}

	/**
	 * @return ID, mit welcher das reale Import-Medium gekennzeichnet ist, damit
	 *         es eindeutig einem Auftrag zugeordnet werden kann.
	 */
	public int getParentId() {
		return parentid;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Nicht zwingend eindeutiger Name des importierten Mediums.
	 */
	public String getName() {
		return name;
	}
	
	public void setLocation(String location) throws FileNotFoundException {
		File destination = new File(location);
		if (!destination.exists())
			throw new FileNotFoundException();
		this.destination = location;
	}
	
	private String destination = null;

	public void saveContent() throws NoDestinationSpecifiedExecpetion {
		if (destination == null)
			throw new NoDestinationSpecifiedExecpetion();
		for(ImportItem item : items) {
			try {
				item.saveFileTo(destination);
			} catch (FileNotFoundException e) {
				System.err.println("Problem with file/directory creation");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Could not save " + item.getName() + " to location " + destination);
				e.printStackTrace();
			}
		}
	}
}