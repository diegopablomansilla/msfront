package com.ms.front.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.ms.EnvVars;
import com.ms.front.commons.services.Service;

public class EjercicioContableFinAllPagin {
	
	public String exec(String db, String pageRequest, Integer lastIndexOld) throws Exception {
		
		String urlString = "EjercicioContable/findAllPagin";
		
		Map<String, String> headers = new HashMap<String, String>();

		if (db != null) {
			headers.put("db", db);
		}

		Map<String, String> queryParams = new HashMap<String, String>();

		if (pageRequest != null) {
			queryParams.put("pr", pageRequest);
		}

		if (lastIndexOld != null) {
			queryParams.put("i", lastIndexOld.toString());
		}
		
		String r = get(urlString, headers, queryParams);
		
		System.out.println(r);

//		if (r.getStatus() == 500) {
//			JavaFXUtil.buildAlertService500();
//		} else if (r.getStatus() == 400) {
//			JavaFXUtil.buildAlertService400();
//		} else if (r.getStatus() == 204) {
//			return Pagin.fromJson(r.getPayload());
//		} else if (r.getStatus() == 200) {
//			return Pagin.fromJson(r.getPayload());
//		}

//		throw new IllegalStateException("Illegal state response, code " + r.getStatus());
		
		return null;
	}
	
	protected String get(String urlString, Map<String, String> headers,
			Map<String, String> queryParams) throws IOException, URISyntaxException {

		URI baseUri = new URI(EnvVars.getApiHome() + "/" + EnvVars.getApiVersion() + "/RPC/" + urlString);

		URI uri = Service.applyParameters(baseUri, queryParams);

		HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
		conn.setRequestMethod("GET");

		conn.setRequestProperty("'Content-Type", "application/json; charset=UTF-8");
		headers.forEach((k, v) -> conn.setRequestProperty(k, v));

		conn.connect();
		int responseCode = conn.getResponseCode();

		// ---------------------------------------------------
		StringBuffer payload = new StringBuffer();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = new BufferedInputStream(conn.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				payload.append(line);
			}
		}

		// ---------------------------------------------------

		conn.disconnect();

//		return new String(payload.toString().getBytes(), "UTF-8");
		return payload.toString();
	}

}
