package ms.common.filesys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * File Input/Output:
 * - write a content to a file
 * - copying files from a source to a destionation
 * 
 * @author david
 *
 */
public class FileIO {
	
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
	
	/**
	 * copying files from a source to a destination
	 * 
	 * @param srcList filelist to copy
	 * @param destDir destination folder
	 * @return boolean true, if transfer was done properly
	 */
	public static boolean transfer(File[] srcList, File destDir) {
		destDir.mkdirs();
		
        FileInputStream source;
        FileOutputStream destination;
		try {
			for(File file: srcList) {
				File destFile = new File(destDir + File.separator + file.getName());
				if(file.isDirectory()) {
					destFile.mkdirs();
					transfer(file.listFiles(), destFile);
					continue;
				}
				source = new FileInputStream(file);
		        destination = new FileOutputStream(destFile);
				
		        FileChannel sourceFileChannel = source.getChannel();
		        FileChannel destinationFileChannel = destination.getChannel();
				
		        sourceFileChannel.transferTo(0, sourceFileChannel.size(), destinationFileChannel);
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
