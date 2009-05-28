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
import ms.utils.networking.client.ServerFinder;

import org.apache.log4j.Logger;

public class InitClient {

	private Logger logger = ClientLog.getLogger();
	
	private AuftragslistenReceiver rec;
	private ImportMediumSender send;
	private AuftragsListe liste;

	public InitClient() {
		initNetwork();
		initUI();
		new ClientController(rec, send, liste);
	}

	private void initNetwork() {
		
		ServerFinder finder = new ServerFinder();
		// stay in loop until a server is found
		while(finder.getLocation() == null) {
			logger.info("No server found, waiting");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		String server_address = finder.getLocation();
		int port = finder.getPort();
		
		try {
			ClientAuftragslistenUpdater clientupdater = new ClientAuftragslistenUpdater(server_address, port);
			rec = new AuftragslistenReceiver(clientupdater);
			liste = new AuftragsListe(rec);

			Executor exec_rec = Executors.newSingleThreadExecutor();
			exec_rec.execute(rec);
			
			send = new ImportMediumSender(server_address, port);
			send.connect();
			
			Executor exec_send = Executors.newSingleThreadExecutor();
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
