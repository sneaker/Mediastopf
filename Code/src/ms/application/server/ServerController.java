package ms.application.server;

import java.io.File;

import ms.utils.FileIO;
import ms.utils.log.server.ServerLog;

import org.apache.log4j.Logger;

/**
 * server classe loading gui components and start server
 * 
 * @author david
 * 
 */
public class ServerController {

	private static Logger logger = ServerLog.getLogger();

	/**
	 * Kopiert die importierten Dateien eines Auftrages vom "Sammelordner" in
	 * ein Exportverzeichnis, welches entweder auf eine CD/DVD gebrannt werden
	 * kann oder direkt auf dem gewünschten Exportmedium liegt.
	 * 
	 * @param folder
	 *            sourceforlder
	 * @param exportFolder
	 *            destinationfolder
	 * @return true wenn das Kopieren geklappt hat und genügend Platz vorhanden
	 *         war für die kopierten Dateien
	 */
	public static boolean copyFiles(File[] folder, File exportFolder) {
		boolean succeed = FileIO.copyFiles(folder, exportFolder);
		if (succeed) {
			logger.info("Filetransfer succeed");
		} else {
			logger.warn("Filetransfer failed");
		}
		return succeed;
	}

	/**
	 * Schnittstelle zum einfachen Schreiben von Dateien.
	 * 
	 * @param file
	 *            Ziel-Datei
	 * @param content
	 *            zu schreibender Dateiinhalt
	 */
	public static void writeFile(File file, String content) {
		FileIO.write(file, content);
	}
}
