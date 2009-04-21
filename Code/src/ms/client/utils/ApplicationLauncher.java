package ms.client.utils;

import java.io.IOException;

import ms.log.Log;

import org.apache.log4j.Logger;


public class ApplicationLauncher {

	private static Logger logger = Log.getLogger();

	public static void open(final String program) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(program);
					process.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e1) {
					logger.error("Error: Cannot start application: " + program);
					e1.printStackTrace();
				}
			}
		});
		t.start();
	}
}
