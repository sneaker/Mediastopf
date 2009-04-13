package ms.server.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


/**
 * save/load a configfile
 * 
 * @author david
 *
 */
public class ConfigHandler extends Properties {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static ConfigHandler handler = new ConfigHandler();
	
	private String filename = Constants.CONFIGFILE;
	
	private ConfigHandler() {
		load();
	}
	
	public void save() {
		try {
			store(new FileWriter(filename), filename);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void load() {
		File config = new File(filename);
		if(!config.exists()) {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			load(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static ConfigHandler getHandler() {
		return handler;
	}
}
