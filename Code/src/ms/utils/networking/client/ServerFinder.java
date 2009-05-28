package ms.utils.networking.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerFinder {
	
	private String local_addr = null;
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
		Socket socket = new Socket();
		
		// first try localhost
		try {
			InetSocketAddress inetaddress = new InetSocketAddress("localhost", port);
			socket.connect(inetaddress, 10);
			socket.close();
			location = "localhost";
			return;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}

		// needs about 15 seconds for subnet scan
		for(int i = 1; i < 255; ++i) {
			String addr = subnet + i;
			if (addr.equals(local_addr))
				continue;
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
			if (local_addr == null) {
				socket = new Socket("www.test.ch", 80);
				local_addr = socket.getLocalAddress().toString().split("/")[1];
				socket.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] netparts = local_addr.split("\\.");
		if (netparts[0].equals("0") )
			return "";
		else {
			return netparts[0] + "." + netparts[1] + "." + netparts[2] + ".";
		}
	}
}
