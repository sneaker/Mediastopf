package ms.server.filesys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

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
	
	/**
	 * move files from a source to a destination
	 * 
	 * @param srcList filelist to move
	 * @param destDir destination folder
	 * @return boolean if transfer is done properly
	 */
	public static boolean transfer(File[] srcList, File destDir) {
		destDir.mkdirs();
		
        FileInputStream source;
        FileOutputStream destination;
		try {
			for(File file: srcList) {
				source = new FileInputStream(file);
		        destination = new FileOutputStream(destDir + File.separator + file.getName());
				
		        FileChannel sourceFileChannel = source.getChannel();
		        FileChannel destinationFileChannel = destination.getChannel();
				
		        long size = sourceFileChannel.size();
		        sourceFileChannel.transferTo(0, size, destinationFileChannel);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
