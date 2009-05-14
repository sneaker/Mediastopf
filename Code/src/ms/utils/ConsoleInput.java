package ms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ms.utils.log.Log;

import org.apache.log4j.Logger;

public class ConsoleInput {
	
	public static String getMessage() 
	{
		Logger logger = Log.getLogger();
		BufferedReader UserInput = new BufferedReader(new InputStreamReader(System.in));
		String message = null;
		
		try {
			message = UserInput.readLine();
		} catch (IOException e) {
			logger.error("Error: Cannot get Message from Keyboard");
			e.printStackTrace();
		}
		return message;
	}
}
