package ms.application.server;

import java.io.File;

import ms.domain.AuftragsListe;
import ms.utils.FileIO;
import ms.utils.log.server.ServerLog;

import org.apache.log4j.Logger;

/**
 * server classe loading gui components and start server
 */
public class ServerController {

	public AuftragsListe auftragsListe;
	
	private static ServerController instance;
	
	private Logger logger = ServerLog.getLogger();

	public ServerController(AuftragsListe alist)
	{
		auftragsListe = alist;
		instance = this;
	}
	
	public static ServerController getInstance()
	{
		return instance;
	}
	
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
	public boolean copyFiles(File[] folder, File exportFolder) {
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
	public void writeFile(File file, String content) {
		FileIO.write(file, content);
	}
}
