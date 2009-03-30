package ch.nomoresecrets.mediastopf.client.filesys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
	
	/**
	 * read filecontent
	 * 
	 * @param file File
	 * @return String content of File
	 */
	public static String read(String file) {
		BufferedReader br;
		String readLine, content = "";
		try {
			br = new BufferedReader(new FileReader(new File(file)));
			while ((readLine = br.readLine()) != null) {
				content += readLine + "\n";
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * write content to a file
	 * 
	 * @param file File
	 * @param content of File
	 */
	public static void write(String file, String content) {
		try {
			FileWriter fw = new FileWriter(new File(file));
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
