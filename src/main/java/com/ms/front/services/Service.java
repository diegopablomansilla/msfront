package com.ms.front.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;

import com.ms.EnvironmentVariables;

public class Service {

	public static ResponseJsonObject GET(String type, String urlString, String dbKey) throws IOException {

		URL url = new URL(EnvironmentVariables.getApiHome() + "/" + type + "/" + EnvironmentVariables.getApiVersion() + "/" + urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("dbKey", dbKey);
		conn.connect();
		int responseCode = conn.getResponseCode();

		// ---------------------------------------------------

		StringBuffer payload = new StringBuffer();

		InputStream in = new BufferedInputStream(conn.getInputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			payload.append(line);
		}

		// ---------------------------------------------------

		conn.disconnect();

		return new ResponseJsonObject(responseCode, payload.toString());

	}

	public static JsonArray toJsonArray(String payload) {

		return Json.createReader(new StringReader(payload)).readArray();
	}

}
