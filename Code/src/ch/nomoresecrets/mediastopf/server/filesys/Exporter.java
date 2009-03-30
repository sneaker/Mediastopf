package ch.nomoresecrets.mediastopf.server.filesys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Exporter {
	
	public static boolean export(File[] srcList, File destDir) {
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
