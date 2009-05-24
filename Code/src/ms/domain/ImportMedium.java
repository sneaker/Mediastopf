package ms.domain;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

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

	protected ArrayList<String> names;
	protected ArrayList<ByteBuffer> items;

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
	
	/**
	 * Serialisiert ein ImportMedium Objekt
	 * 
	 * @param stream
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException
	{
		//auftrags id
		stream.writeInt(id);
		//names
		stream.writeInt(names.size());
		Iterator<String> name_it = names.iterator();
		while(name_it.hasNext()) {
			String itemname = name_it.next();
			stream.writeInt(itemname.length());
			stream.writeBytes(itemname);
		}
		//items
		stream.writeInt(items.size());
		Iterator<ByteBuffer> item_it = items.iterator();
		while(item_it.hasNext()) {
			ByteBuffer b = item_it.next();
			stream.writeInt(b.capacity());
			System.out.println(b.array().length);
			stream.write(b.array());
		}
	}
	
	/**
	 * Deserialisiert ein ImportMedium
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
	{
		//auftragsid
		id = stream.readInt();
		int length = stream.readInt();
		//filenames
		names = new ArrayList<String>(length);
		for (int i = 0; i < length; ++i) {
			int namelength = stream.readInt();
			byte[] b = new byte[namelength	];
			stream.read(b, 0, namelength);
			String s = new String(b);
			names.add(s);
		}

		File directory = new File(Integer.toString(id));
		directory.mkdir();

		//files
		length = stream.readInt();
		items = new ArrayList<ByteBuffer>(length);
		for (int i = 0; i < length; ++i){
			int filesize = stream.readInt();
			
			int newbytes = 0;
			int totalbytes = 0;
			int maxtoread = 0;
			if (filesize > 1024)
				maxtoread = 1024;
			else
				maxtoread = filesize;
			byte[] tmpbuffer = new byte[1024];
			FileOutputStream writer = new FileOutputStream(directory + File.separator + names.get(i));		
			
			while ((newbytes= stream.read(tmpbuffer, 0, maxtoread)) != -1) {
				writer.write(tmpbuffer, 0, newbytes);
				totalbytes += newbytes;
				if (totalbytes >= filesize)
					break;
				if ((totalbytes + 1024) > filesize)
					maxtoread = filesize - totalbytes;
				else
					maxtoread = 1024;
			}
			writer.close();
		}
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