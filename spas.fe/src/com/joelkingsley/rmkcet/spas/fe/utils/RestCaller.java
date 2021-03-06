package com.joelkingsley.rmkcet.spas.fe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import sun.net.www.protocol.http.HttpURLConnection;

public class RestCaller {

	private final static Logger LOGGER = Logger.getLogger(RestCaller.class.getName());

	public String doGet(String url) throws MalformedURLException {

		LOGGER.info("RestCaller::doGet Entering");

		try {
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");

			ByteArrayOutputStream into = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int n; 0 < (n = connection.getInputStream().read(buf));) {
				into.write(buf, 0, n);
			}
			into.close();

			return new String(into.toByteArray(), "UTF-8");

		} catch (ProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			LOGGER.info("RestCaller::doGet Exiting");
		}
	}

	public String doPost(String url, String json) {
		LOGGER.info("RestCaller::doPost Entering");

		try {
//			URL urlObj = new URL(url);
//			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
//			
//			connection.setRequestProperty("Accept", "application/json; charset=UTF-8");
//			connection.setDoOutput(true);
//			connection.setDoInput(true);
//			connection.setRequestMethod("POST");
//			
//			OutputStream os = connection.getOutputStream();
//			os.write(json.getBytes("UTF-8"));
//			os.close();
//			
//			ByteArrayOutputStream into = new ByteArrayOutputStream();
//		    byte[] buf = new byte[4096];
//		    for (int n; 0 < (n = connection.getInputStream().read(buf));) {
//		        into.write(buf, 0, n);
//		    }
//		    into.close();

			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpPost request = new HttpPost(url);
			StringEntity postingJson = new StringEntity(json);
			request.setEntity(postingJson);
			request.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(request);
			InputStream in = response.getEntity().getContent();
			ByteArrayOutputStream into = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			for (int n; 0 < (n = in.read(buf));) {
				into.write(buf, 0, n);
			}
			into.close();
			return new String(into.toByteArray(), "UTF-8");

		} catch (ProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			LOGGER.info("RestCaller::doPost Exiting");
		}
	}
}
