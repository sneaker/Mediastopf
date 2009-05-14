package ms.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import ms.ui.client.ClientConstants;
import ms.ui.server.ServerConstants;

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
	
	private static ConfigHandler client = new ConfigHandler(ClientConstants.CONFIGFILE);
	private static ConfigHandler server = new ConfigHandler(ServerConstants.CONFIGFILE);
	
	private String filename;
	
	private ConfigHandler(String filename) {
		this.filename = filename;
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
	
	public static ConfigHandler getClientHandler() {
		return client;
	}
	
	public static ConfigHandler getServerHandler() {
		return server;
	}
}
