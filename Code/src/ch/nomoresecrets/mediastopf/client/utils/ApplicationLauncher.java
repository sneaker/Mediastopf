package ch.nomoresecrets.mediastopf.client.utils;

import java.io.IOException;

public class ApplicationLauncher {

		public ApplicationLauncher(String program) {
			try {
				Runtime.getRuntime().exec(program);
			} catch (IOException e) {
				System.err.println("Error: Cannot start application: " + program);
				e.printStackTrace();
			}
		}
}
