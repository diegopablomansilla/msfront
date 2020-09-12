package com.ms.front.services;

import com.ms.front.commons.services.AbstractFindAllPaginService;
import com.ms.front.model.Pagin;

public class EjercicioContableFindAllPagin extends AbstractFindAllPaginService {
	
	private static final String URL = "/EjercicioContable/findAllPagin";

	public Pagin findFirst(String db) throws Exception {
		return exec(URL, db, PAGE_REQUEST_FIRST, null);
	}

	public Pagin findNext(String db, Integer lastIndexOld) throws Exception {
		return exec(URL, db, PAGE_REQUEST_NEXT, lastIndexOld);
	}

	public Pagin findBack(String db, Integer lastIndexOld) throws Exception {
		return exec(URL, db, PAGE_REQUEST_BACK, lastIndexOld);
	}

	public Pagin findLast(String db) throws Exception {
		return exec(URL, db, PAGE_REQUEST_LAST, null);
	}

//	private Pagin exec(String db, String pageRequest, Integer lastIndexOld) throws Exception {
//
//		if (db == null) {
//			throw new IllegalArgumentException("db is nulll.");
//		}
//
//		db = db.trim();
//
//		if (db.length() == 0) {
//			throw new IllegalArgumentException("db is empty.");
//		}
//
//		// ---------------------------------------------------------------------------
//
//		if (pageRequest == null) {
//			throw new IllegalArgumentException("pageRequest is nulll.");
//		}
//
//		pageRequest = pageRequest.trim();
//
//		if (pageRequest.length() == 0) {
//			throw new IllegalArgumentException("pageRequest is empty.");
//		}
//
//		if (pageRequest.equals("FIRST") == false && pageRequest.equals("LAST") == false
//				&& pageRequest.equals("NEXT") == false && pageRequest.equals("BACK") == false) {
//			throw new IllegalArgumentException("pageRequest is not FIRST | NEXT | BACK | LAST .");
//		}
//
//		// =============================================================================
//
//		String urlString = "/EjercicioContable/findAllPagin";
//
//		Map<String, String> headers = new HashMap<String, String>();
//		Map<String, String> queryParams = new HashMap<String, String>();
//
//		// =============================================================================
//
//		headers.put("db", db);
//		queryParams.put("pr", pageRequest);
//		if (lastIndexOld != null) {
//			queryParams.put("i", lastIndexOld.toString());
//		}
//
//		// =============================================================================
//
//		URI baseUri = new URI(EnvVars.getApiHome() + urlString);
//
//		URI uri = applyParameters(baseUri, queryParams);
//
//		HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
//		conn.setRequestMethod("GET");
//
//		conn.setRequestProperty("'Content-Type", "application/json; charset=UTF-8");
//		headers.forEach((k, v) -> conn.setRequestProperty(k, v));
//
//		conn.connect();
//		int responseCode = conn.getResponseCode();
//
//		// ---------------------------------------------------
//
//		StringBuffer payload = new StringBuffer();
//
//		if (responseCode == HttpURLConnection.HTTP_OK) {
//			InputStream in = new BufferedInputStream(conn.getInputStream());
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				payload.append(line);
//			}
//		}
//
//		// ---------------------------------------------------
//
//		conn.disconnect();
//
//		// =============================================================================
//
//		if (responseCode == 204) {
//			return Pagin.fromJson(payload.toString());
//		} else if (responseCode == 200) {
//			return Pagin.fromJson(payload.toString());
//		} else {
//			throw new IllegalStateException("Illegal state response, code " + responseCode);
//		}
//
//	}

}
