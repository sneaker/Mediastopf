package ch.nomoresecrets.mediastopf.server.web;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLDownloader {

	public static byte[] downloadURL(String InternetURL) {
		byte[] content = null;
		ByteArrayOutputStream output = null;
		URL url = null;
		URLConnection connection = null;
		InputStream is = null;
		try {
			url = new URL(InternetURL);
			connection = url.openConnection();
			connection.setRequestProperty( "User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9) Gecko/2008052906 Firefox/3.0" );
			output = new ByteArrayOutputStream();
			is = connection.getInputStream();
			int last = 0;
			while (last != -1) {
				last = is.read();
				output.write(last);
			}
			content = output.toByteArray();
			is.close();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
