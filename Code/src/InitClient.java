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
import ms.utils.log.client.ClientLog;
import ms.utils.networking.client.ClientAuftragslistenUpdater;
import ms.utils.networking.client.ImportMediumSender;

import org.apache.log4j.Logger;

public class InitClient {

	public static final String HOST = "localhost"; //"152.96.235.146";
	public static final int PORT = 1337;

	private Logger logger = ClientLog.getLogger();
	
	AuftragslistenReceiver rec;
	ImportMediumSender send;
	AuftragsListe liste;

	public InitClient() {
		initNetwork();
		initUI();
		new ClientController(rec, send, liste);
	}

	private void initNetwork() {
		try {
			ClientAuftragslistenUpdater clientupdater = new ClientAuftragslistenUpdater(HOST, PORT);
			rec = new AuftragslistenReceiver(clientupdater);
			liste = new AuftragsListe(rec);
			send = new ImportMediumSender(HOST, PORT);
			send.connect();
			
			Executor exec_rec = Executors.newSingleThreadExecutor();
			Executor exec_send = Executors.newSingleThreadExecutor();
			
			exec_rec.execute(rec);
			exec_send.execute(send);
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

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void initUI() {
		setLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainView mediastopf = new MainView();
				mediastopf.setVisible(true);
			}
		});
	}
}
