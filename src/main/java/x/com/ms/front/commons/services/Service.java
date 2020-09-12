package x.com.ms.front.commons.services;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;

import com.ms.EnvVars;
import com.ms.front.commons.views.JavaFXUtil;

import x.com.ms.front.model.IdDesc;
import x.com.ms.front.model.IdDescArgs;
import x.com.ms.front.model.Pagin;
import x.com.ms.front.model.PaginArgs;
import x.com.ms.front.model.ServiceArgs;

public class Service {

	public static final String TYPE_RPC = "RPC";

	public static IdDesc GETIdDesc(String urlString, IdDescArgs idDescArgs) throws IOException, URISyntaxException {
		return GETIdDesc(urlString, idDescArgs, null);
	}

	public static IdDesc GETIdDesc(String urlString, IdDescArgs idDescArgs, ServiceArgs args)
			throws IOException, URISyntaxException {

		idDescArgs.setDb("db1");

		Map<String, String> headers = new HashMap<String, String>();

		if (idDescArgs.getDb() != null) {
			headers.put(idDescArgs.KEY_DB, idDescArgs.getDb());
		}

		Map<String, String> queryParams = new HashMap<String, String>();

		if (idDescArgs.getText() != null) {
			queryParams.put(idDescArgs.KEY_TEXT, idDescArgs.getText());
		}

		if (args != null) {
			queryParams.putAll(args.toMap());
		}

		ResponseJsonObject r = GET(TYPE_RPC, urlString, headers, queryParams);

		if (r.getStatus() == 500) {
			JavaFXUtil.buildAlertService500();
		} else if (r.getStatus() == 204) {

			return IdDesc.fromJson(r.getPayload());
		} else if (r.getStatus() == 200) {
			return IdDesc.fromJson(r.getPayload());
		}

		throw new IllegalStateException("Illegal state response, code " + r.getStatus());
	}

	public static Pagin GETPagin(String urlString, PaginArgs paginArgs) throws IOException, URISyntaxException {
		return GETPagin(urlString, paginArgs, null);
	}

	public static Pagin GETPagin(String urlString, PaginArgs paginArgs, ServiceArgs args)
			throws IOException, URISyntaxException {

		paginArgs.setDb("db1");

		Map<String, String> headers = new HashMap<String, String>();

		if (paginArgs.getDb() != null) {
			headers.put(paginArgs.KEY_DB, paginArgs.getDb());
		}

		Map<String, String> queryParams = new HashMap<String, String>();

		if (paginArgs.getPageRequest() != null) {
			queryParams.put(paginArgs.KEY_PAGE_REQUEST, paginArgs.getPageRequest());
		}

		if (paginArgs.getLastIndexOld() != null) {
			queryParams.put(paginArgs.KEY_LAST_INDEX_OLD, paginArgs.getLastIndexOld().toString());
		}

		if (args != null) {
			queryParams.putAll(args.toMap());
		}

		ResponseJsonObject r = GET(TYPE_RPC, urlString, headers, queryParams);

		if (r.getStatus() == 500) {
			JavaFXUtil.buildAlertService500();
		} else if (r.getStatus() == 400) {
			JavaFXUtil.buildAlertService400();
		} else if (r.getStatus() == 204) {
			return Pagin.fromJson(r.getPayload());
		} else if (r.getStatus() == 200) {
			return Pagin.fromJson(r.getPayload());
		}

		throw new IllegalStateException("Illegal state response, code " + r.getStatus());
	}

	public static ResponseJsonObject GET(String type, String urlString, Map<String, String> headers,
			Map<String, String> queryParams) throws IOException, URISyntaxException {

		URI baseUri = new URI(EnvVars.getApiHome() + "/" + EnvVars.getApiVersion() + "/" + type + "/" + urlString);

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

		return new ResponseJsonObject(responseCode, new String(payload.toString().getBytes(), "UTF-8"));
	}

	public static URI applyParameters(URI baseUri, Map<String, String> queryParams) {

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
