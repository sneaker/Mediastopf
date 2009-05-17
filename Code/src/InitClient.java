import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import ms.application.client.ClientController;
import ms.ui.client.MainView;

import ms.utils.I18NManager;
import ms.utils.log.client.ClientLog;
import ms.utils.networking.client.AuftragslistenReceiver;
import ms.utils.networking.client.ImportMediumSender;

public class InitClient {

	public static final String HOST = "localhost";
	public static final int PORT = 1337;

	private static Logger logger;
	private static I18NManager manager;
	
	AuftragslistenReceiver rec;
	ImportMediumSender send;

	public InitClient() {
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
			rec = new AuftragslistenReceiver(HOST, PORT);
			send = new ImportMediumSender(HOST, PORT);
			rec.connect();
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
				MainView mediastopf = new MainView();
				mediastopf.setVisible(true);
			}
		});
	}
}
