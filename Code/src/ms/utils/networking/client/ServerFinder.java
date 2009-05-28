package ms.utils.networking.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerFinder {
	
	private String location = null;
	private int port = 1337;
	
	public String getLocation()
	{
		if (location == null)
			findServerInSubnet();
		
		return location;
	}
	
	public int getPort() {
		return port;
	}

	private void findServerInSubnet() {
		String subnet = getSubnet();
		Socket socket = null;
		
		// first try localhost
		try {
			socket = new Socket("localhost", port);
			socket.close();
			location = "localhost";
			return;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}

		// needs about 15 seconds for subnet scan
		for(int i = 1; i < 255; ++i) {
			String addr = subnet + i;
			try {
				socket = new Socket();
				InetSocketAddress inetaddress = new InetSocketAddress(addr, port);
				socket.connect(inetaddress, 10);
				socket.close();
				location = addr;
				return;
			} catch (UnknownHostException e) {
			} catch (IOException e) {
			}
		}
	}
	
	private String getSubnet()
	{
		Socket socket = null;
		try {
			socket = new Socket("www.test.ch", 80);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String local_addr = socket.getLocalAddress().toString().split("/")[1];
		String[] netparts = local_addr.split("\\.");
		if (netparts[0].equals("0") )
			return "";
		else {
			return netparts[0] + "." + netparts[1] + "." + netparts[2] + ".";
		}
	}
}
