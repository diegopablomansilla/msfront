package com.ms.front.services;

import java.util.HashMap;
import java.util.Map;

import com.ms.front.commons.services.AbstractFindAllPaginService;
import com.ms.front.model.Pagin;

public class AsientoModeloFindAllPagin extends AbstractFindAllPaginService {

	public Pagin findFirst(String db, String ejercicioContableId) throws Exception {
		return exec(db, PAGE_REQUEST_FIRST, null, ejercicioContableId);
	}

	public Pagin findNext(String db, Integer lastIndexOld, String ejercicioContableId) throws Exception {
		return exec(db, PAGE_REQUEST_NEXT, lastIndexOld, ejercicioContableId);
	}

	public Pagin findBack(String db, Integer lastIndexOld, String ejercicioContableId) throws Exception {
		return exec(db, PAGE_REQUEST_BACK, lastIndexOld, ejercicioContableId);
	}

	public Pagin findLast(String db, String ejercicioContableId) throws Exception {
		return exec(db, PAGE_REQUEST_LAST, null, ejercicioContableId);
	}

	private Pagin exec(String db, String pageRequest, Integer lastIndexOld, String ejercicioContableId)
			throws Exception {

		// ---------------------------------------------------------------------------

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is nulll.");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty.");
		}

		// =============================================================================

		Map<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("ejercicio", ejercicioContableId);

		// ---------------------------------------------------------------------------

		return exec("/AsientoModelo/findAllPagin", db, pageRequest, lastIndexOld, queryParams);

	}

}
