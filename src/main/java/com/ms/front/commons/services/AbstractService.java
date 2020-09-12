package com.ms.front.commons.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public abstract class AbstractService {
	
	protected URI applyParameters(URI baseUri, Map<String, String> queryParams) {

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
