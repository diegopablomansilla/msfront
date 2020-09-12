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
import com.ms.front.model.Pagin;

public abstract class AbstractFindAllPaginService extends AbstractService {

	protected static final String PAGE_REQUEST_FIRST = "FIRST";
	protected static final String PAGE_REQUEST_NEXT = "NEXT";
	protected static final String PAGE_REQUEST_BACK = "BACK";
	protected static final String PAGE_REQUEST_LAST = "LAST";
	
	protected Pagin exec(String urlString, String db, String pageRequest, Integer lastIndexOld) throws Exception {

		return exec(urlString, db, pageRequest, lastIndexOld, null, null);
	}

	protected Pagin exec(String urlString, String db, String pageRequest, Integer lastIndexOld,
			Map<String, String> queryParams) throws Exception {

		return exec(urlString, db, pageRequest, lastIndexOld, null, queryParams);
	}

	protected Pagin exec(String urlString, String db, String pageRequest, Integer lastIndexOld,
			Map<String, String> headers, Map<String, String> queryParams) throws Exception {

		if (db == null) {
			throw new IllegalArgumentException("db is nulll.");
		}

		db = db.trim();

		if (db.length() == 0) {
			throw new IllegalArgumentException("db is empty.");
		}

		// ---------------------------------------------------------------------------

		if (pageRequest == null) {
			throw new IllegalArgumentException("pageRequest is nulll.");
		}

		pageRequest = pageRequest.trim();

		if (pageRequest.length() == 0) {
			throw new IllegalArgumentException("pageRequest is empty.");
		}

		if (pageRequest.equals(PAGE_REQUEST_FIRST) == false && pageRequest.equals(PAGE_REQUEST_LAST) == false
				&& pageRequest.equals(PAGE_REQUEST_NEXT) == false && pageRequest.equals(PAGE_REQUEST_BACK) == false) {
			throw new IllegalArgumentException("pageRequest is not " + PAGE_REQUEST_FIRST + " | " + PAGE_REQUEST_NEXT
					+ " | " + PAGE_REQUEST_BACK + " | " + PAGE_REQUEST_LAST + ".");
		}

		// =============================================================================

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		if (queryParams == null) {
			queryParams = new HashMap<String, String>();
		}

		headers.put("db", db);
		queryParams.put("pr", pageRequest);
		if (lastIndexOld != null) {
			queryParams.put("i", lastIndexOld.toString());
		}

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
			return Pagin.fromJson(payload.toString());
		} else if (responseCode == 200) {
			return Pagin.fromJson(payload.toString());
		} else {
			throw new IllegalStateException("Illegal state response, code " + responseCode);
		}

	}

}
