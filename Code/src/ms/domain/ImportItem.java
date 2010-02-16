package ms.domain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ImportItem implements Serializable {

	private static final long serialVersionUID = -7500129200907075110L;
	private String _name;
	private byte[] _content;
	
	public ImportItem(String location) {
		setName(getFilename(location));
		getBytesFromFile(location);
	}

	private static String getFilename(String location) {
		String[] parts = location.split(File.separator);
		return parts[parts.length-1];
	}

	public void getBytesFromFile(String location) {
		File __file = new File(location);
		long __size = __file.length();
		_content = new byte[(int) __size];
		
		try {
			FileInputStream fis = new FileInputStream(__file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(_content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveFileTo(String location) throws IOException {
		if (!new File(location).exists())
			throw new FileNotFoundException();
		String path = location + File.separator + _name;
		File newfile = new File(path);
		newfile.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(newfile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(_content);
	}
	
	public int getSize() {
		return _content.length;
	}

	private void setName(String _name) {
		this._name = _name;
	}

	public String getName() {
		return _name;
	}

}
