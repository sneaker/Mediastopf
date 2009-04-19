package ms.client.filesys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write a Content to a File 
 * 
 * @author david
 *
 */
public class FileContentWriter {
	
	/**
	 * write content to a file
	 * 
	 * @param file File
	 * @param content of File
	 */
	public static void write(File file, String content) {
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
