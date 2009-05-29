package ms.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ms.utils.ImageWhiteFilter;

/**
 * Repraesentiert ein digitalisiertes Medium eines Kunden, welches nach dem
 * Import aus einzelnen Dateien besteht.
 */
public class ImportMedium implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String Name;
	protected int id = -1;
	protected int status = -1;
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<String> names;
	public ArrayList<ByteBuffer> items;

	/**
	 * Vorbereitung, damit die einzelnen extrahierten Dateien hinzugefuegt werden
	 * koennen.
	 */
	public ImportMedium(ResultSet row) throws SQLException {
		this.Name = row.getString("Name");
		this.id = row.getInt("id");
		this.status = row.getInt("status");
	}

	public ImportMedium(File folder) {
		items = new ArrayList<ByteBuffer>();
		names = new ArrayList<String>();
		for (String filename : folder.list()) {
			names.add(filename);
			FileInputStream fis;
			File f = new File(folder + File.separator + filename);
			if (isImage(f)) {
				if(ImageWhiteFilter.analyzeImageFile(f)) {
					continue;
				}
			}
			Integer length = (int) f.length();
			ByteBuffer buffer = ByteBuffer.allocate(length);
			try {
				fis = new FileInputStream(f);
				FileChannel fc = fis.getChannel();
				fc.read(buffer);
				buffer.flip();
				fc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			items.add(buffer);
		}
	}
	
	public ImportMedium(int auftrags_id, ArrayList<String> names, ArrayList<ByteBuffer> items)
	{
		setId(auftrags_id);
		this.names =names;
		this.items = items;
		
		Integer id = auftrags_id;
		String _id = id.toString();
		
		File directory = new File(_id);
		directory.mkdir();
		
		for (int i = 0; i < items.size(); ++i) {
			try {
				FileOutputStream writer = new FileOutputStream(directory + File.separator + names.get(i));
				writer.write(items.get(i).array());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isInDB() {
		return id > -1;
	}

	/**
	 * @return ID, mit welcher das reale Import-Medium gekennzeichnet ist, damit
	 *         es eindeutig einem Auftrag zugeordnet werden kann.
	 */
	public int getID() {
		return id;
	}

	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return Nicht zwingend eindeutiger Name des importierten Mediums.
	 */
	public String getName() {
		return Name;
	}

	/**
	 * Liefert alle bis zum aktuellen Zeitpunkt importierten Dateien zurÃ¼ck.
	 * 
	 * @return Liste mit allen bisher importierten Dateien dieses Mediums
	 */
	public ArrayList<ByteBuffer> getItemsbyFile() {
		return items;
	}

	private static boolean isImage(File file) {
		String[] extensions = { "jpg", "jpeg", "gif", "png" };
		for (int i = 0; i < extensions.length; i++) {
			if (file.getName().endsWith(extensions[i])) {
				return true;
			}
		}
		return false;
	}
}