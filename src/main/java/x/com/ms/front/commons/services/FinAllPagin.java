package x.com.ms.front.commons.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ms.EnvVars;

import x.com.ms.front.model.Pagin;

public abstract class FinAllPagin {

	protected String getUrlString() {
		return null;
	}

	protected Pagin findFirst(String db) throws Exception {
		return find(db, "FIRST", null, null);
	}

	protected Pagin findFirst(String db, Map<String, String> args) throws Exception {
		return find(db, "FIRST", null, null);
	}

	protected Pagin findNext(String db, Integer lastIndexOld) throws Exception {
		return find(db, "NEXT", lastIndexOld, null);
	}

	protected Pagin findNext(String db, Integer lastIndexOld, Map<String, String> args) throws Exception {
		return find(db, "NEXT", lastIndexOld, null);
	}

	protected Pagin findBack(String db, Integer lastIndexOld) throws Exception {
		return find(db, "BACK", lastIndexOld, null);
	}

	protected Pagin findBack(String db, Integer lastIndexOld, Map<String, String> args) throws Exception {
		return find(db, "BACK", lastIndexOld, null);
	}

	protected Pagin findLast(String db) throws Exception {
		return find(db, "LAST", null, null);
	}

	protected Pagin findLast(String db, Map<String, String> args) throws Exception {
		return find(db, "LAST", null, null);
	}

	// -------------------------------------------------------------------------------

	protected Pagin find(String db, String pageRequest, Integer lastIndexOld) throws Exception {
		return find(db, pageRequest, lastIndexOld, null);
	}

	protected Pagin find(String db, String pageRequest, Integer lastIndexOld, Map<String, String> args)
			throws Exception {

		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> queryParams = new HashMap<String, String>();

		// --------------------------------------------------------------------------

		if (db == null) {
			throw new IllegalArgumentException("db is null.");
		}

		db = db.trim();

		if (db.length() == 0) {
			throw new IllegalArgumentException("db is empty.");
		}

		headers.put("db", db);

		if (pageRequest == null) {
			throw new IllegalArgumentException("pageRequest is null.");
		}

		pageRequest = pageRequest.trim();

		if (pageRequest.length() == 0) {
			throw new IllegalArgumentException("pageRequest is empty.");
		}

		queryParams.put("pr", pageRequest);

		if (pageRequest.equals("NEXT") || pageRequest.equals("BACK")) {

			if (lastIndexOld == null) {
				throw new IllegalArgumentException("lastIndexOld is null.");
			}

			if (lastIndexOld < 0) {
				throw new IllegalArgumentException("lastIndexOld < 0.");
			}

			queryParams.put("i", lastIndexOld.toString());
		}

		if (args != null) {
			queryParams.putAll(args);
		}

		return exec(headers, queryParams);

	}

	protected Pagin exec(Map<String, String> headers, Map<String, String> queryParams)
			throws IOException, URISyntaxException {

		URI baseUri = new URI(EnvVars.getApiHome() + "/" + EnvVars.getApiVersion() + "/RPC/" + getUrlString());

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

		if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
			return Pagin.fromJson(payload.toString());
		}

		throw new IllegalStateException("Error: El servicio retorno un código " + responseCode + ".");

	}

	private URI applyParameters(URI baseUri, Map<String, String> queryParams) {

		String[] urlParameters = new String[queryParams.size() * 2];

		Set<String> keys = queryParams.keySet();

		int i = 0;
		for (String key : keys) {
			urlParameters[i] = key;
			i++;
			urlParameters[i] = queryParams.get(key);
			i++;
		}

		return applyParameters(baseUri, urlParameters);
	}

	private URI applyParameters(URI baseUri, String... urlParameters) {

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

				if (urlParameters[i + 1] != null) {
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

}
