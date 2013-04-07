package com.codewar.televisionary.jsonarrayget;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpJsonArrayGet {
	private static byte[] buff = new byte[1024];

	public static synchronized String downloadFromServer(String... params)
			throws Exception {
		String retval = null;
		String Url = "http://api.trakt.tv/show/season.json/18cced3e20fb3673dab6f5deb5dd56be/the-walking-dead/1";

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(params[0]);

		try {
			HttpResponse responce = client.execute(request);
			StatusLine status = responce.getStatusLine();
			if (status.getStatusCode() != 200) {
				// handle error here
				throw new Exception("Invalid responce from trakt.tv"
						+ status.toString());
			}

			// process the content
			HttpEntity entity = responce.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = ist.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			retval = new String(content.toByteArray());
		} catch (Exception e) {
			throw new Exception(
					"Problem connecting to server" + e.getMessage(), e);
		}
		return retval;
	}
}
