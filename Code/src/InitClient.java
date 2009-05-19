import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ms.application.client.ClientController;
import ms.domain.AuftragsListe;
import ms.ui.client.MainView;
import ms.utils.AuftragslistenReceiver;
import ms.utils.I18NManager;
import ms.utils.log.client.ClientLog;
import ms.utils.networking.client.ClientAuftragslistenUpdater;
import ms.utils.networking.client.ImportMediumSender;

import org.apache.log4j.Logger;

public class InitClient {

	public static final String HOST = "localhost";
	public static final int PORT = 1337;
	public static boolean DEBUG = false;

	private static Logger logger;
	@SuppressWarnings("unused")
	private I18NManager manager;
	
	AuftragslistenReceiver rec;
	ImportMediumSender send;

	public InitClient(boolean debug) {
		DEBUG = debug;
		initManager();
		initLogger();
		initNetwork();
		initGui();
		new ClientController(rec, send);
	}

	private void initManager() {
		manager = I18NManager.getManager();
	}

	private void initLogger() {
		logger = ClientLog.getLogger();
	}

	private void initNetwork() {
		try {
			ClientAuftragslistenUpdater clientupdater = new ClientAuftragslistenUpdater(HOST, PORT);
			rec = new AuftragslistenReceiver(clientupdater);
			AuftragsListe.getInstance(rec);
			send = new ImportMediumSender(HOST, PORT);
			send.connect();
			
			Executor exec = Executors.newSingleThreadExecutor();

			exec.execute(rec);
			exec.execute(send);

		} catch (UnknownHostException e) {
			logger.fatal("Unknown host");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			logger.info("Cannot connect to host");
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void initGui() {
		loadUI();
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void loadUI() {
		setLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainView mediastopf = new MainView(DEBUG);
				mediastopf.setVisible(true);
			}
		});
	}
}
