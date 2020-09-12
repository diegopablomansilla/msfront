package com.ms.front.commons.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.ms.front.EnvVars;
import com.ms.front.model.IdDesc;

public abstract class AbstractFindOneByTextService extends AbstractService {

	protected IdDesc exec(String urlString, String db, String text) throws Exception {

		return exec(urlString, db, text, null, null);
	}

	protected IdDesc exec(String urlString, String db, String text, Map<String, String> queryParams) throws Exception {

		return exec(urlString, db, text, null, queryParams);
	}

	protected IdDesc exec(String urlString, String db, String text, Map<String, String> headers,
			Map<String, String> queryParams) throws Exception {

		if (db == null) {
			throw new IllegalArgumentException("db is nulll.");
		}

		db = db.trim();

		if (db.length() == 0) {
			throw new IllegalArgumentException("db is empty.");
		}

		// ---------------------------------------------------------------------------

		if (text == null) {
			throw new IllegalArgumentException("text is nulll.");
		}

		text = text.trim();

		if (text.length() == 0) {
			throw new IllegalArgumentException("text is empty.");
		}

		// =============================================================================

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		if (queryParams == null) {
			queryParams = new HashMap<String, String>();
		}

		headers.put("db", db);
		queryParams.put("text", text);

		// =============================================================================

		URI baseUri = new URI(EnvVars.getApiHome() + urlString);

		URI uri = applyParameters(baseUri, queryParams);

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

		// =============================================================================

		if (responseCode == 204) {
			return IdDesc.fromJson(payload.toString());
		} else if (responseCode == 200) {
			return IdDesc.fromJson(payload.toString());
		} else {
			throw new IllegalStateException("Illegal state response, code " + responseCode);
		}

	}

}
