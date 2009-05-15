package ms.utils.networking.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ms.utils.networking.BasicNetIO;

public abstract class AbstractServerConnection extends BasicNetIO {

	private int port = 0;
	private String host = null;

	public AbstractServerConnection(String host, int port) throws UnknownHostException,
			IOException {
		this.port = port;
		this.host = host;
	}

	public void connect() throws UnknownHostException, IOException {
		commSocket = new Socket(host, port);
	}

	public void disconnect() throws IOException {
		commSocket.close();
	}
}