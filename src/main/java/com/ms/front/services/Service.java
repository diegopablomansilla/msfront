package com.ms.front.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.json.Json;
import javax.json.JsonArray;

import com.ms.EnvVars;

public class Service {

	public static final String TYPE_RPC = "rpc";

	public static ResponseJsonObject GET(String type, String urlString, String dbKey, String... urlParameters)
			throws IOException, URISyntaxException {

		URI baseUri = new URI(EnvVars.getApiHome() + "/" + type + "/"
				+ EnvVars.getApiVersion() + "/" + urlString);

		URI uri = applyParameters(baseUri, urlParameters);

		HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
		conn.setRequestMethod("GET");
		
		conn.setRequestProperty("'Content-Type", "application/json; charset=UTF-8");
		conn.setRequestProperty("dbKey", dbKey);

		conn.connect();
		int responseCode = conn.getResponseCode();

		// ---------------------------------------------------
		StringBuffer payload = new StringBuffer();		
		
		if (responseCode != HttpURLConnection.HTTP_INTERNAL_ERROR) {
			InputStream in = new BufferedInputStream(conn.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				payload.append(line);
			}
		}

		// ---------------------------------------------------

		conn.disconnect();

		return new ResponseJsonObject(responseCode, new String(payload.toString().getBytes(), "UTF-8"));

	}

	private static URI applyParameters(URI baseUri, String... urlParameters) {

		if (urlParameters == null || urlParameters.length == 0) {
			return baseUri;
		}

		StringBuilder query = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < urlParameters.length; i += 2) {
			if (first) {
				first = false;
			} else {
				query.append("&");
			}
			try {
				
				if(urlParameters[i + 1] != null) {
					query.append(urlParameters[i]).append("=").append(URLEncoder.encode(urlParameters[i + 1], "UTF-8"));	
				}				
			} catch (UnsupportedEncodingException ex) {
				/*
				 * As URLEncoder are always correct, this exception should never be thrown.
				 */
				throw new RuntimeException(ex);
			}
		}
		try {
			return new URI(baseUri.getScheme(), baseUri.getAuthority(), baseUri.getPath(), query.toString(), null);
		} catch (URISyntaxException ex) {
			/*
			 * As baseUri and query are correct, this exception should never be thrown.
			 */
			throw new RuntimeException(ex);
		}
	}

	public static JsonArray toJsonArray(String payload) {

		return Json.createReader(new StringReader(payload)).readArray();
	}

//	public static ResponseError500Object toResponseError500Object(String payload) {
//
//		ResponseError500Object r = new ResponseError500Object();
//		
//		System.out.println(payload);
//
//		JsonObject json = Json.createReader(new StringReader(payload)).readObject();
//
//		if (json.containsKey("message") && json.isNull("message") == false) {
//			r.setMessage(json.getString("message"));
//		}
//
//		if (json.containsKey("reason") && json.isNull("reason") == false) {
//			r.setReason(json.getString("reason"));
//		}
//
//		return r;
//	}

}
