package com.ms.front.services;

import java.util.HashMap;
import java.util.Map;

import com.ms.front.commons.services.AbstractFindAllPaginService;
import com.ms.front.model.Pagin;

public class CentroCostoContableFindAllPagin extends AbstractFindAllPaginService {

	protected static final String POR_CENTRO_DE_COSTO = "CENTRO_DE_COSTO";
	protected static final String POR_NOMBRE = "NOMBRE";

	public Pagin findFirst(String db, String ejercicioContableId, String por) throws Exception {
		return exec(db, PAGE_REQUEST_FIRST, null, ejercicioContableId, por);
	}

	public Pagin findNext(String db, Integer lastIndexOld, String ejercicioContableId, String por) throws Exception {
		return exec(db, PAGE_REQUEST_NEXT, lastIndexOld, ejercicioContableId, por);
	}

	public Pagin findBack(String db, Integer lastIndexOld, String ejercicioContableId, String por) throws Exception {
		return exec(db, PAGE_REQUEST_BACK, lastIndexOld, ejercicioContableId, por);
	}

	public Pagin findLast(String db, String ejercicioContableId, String por) throws Exception {
		return exec(db, PAGE_REQUEST_LAST, null, ejercicioContableId, por);
	}

	private Pagin exec(String db, String pageRequest, Integer lastIndexOld, String ejercicioContableId, String por)
			throws Exception {

		// ---------------------------------------------------------------------------

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is nulll.");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty.");
		}

		// ---------------------------------------------------------------------------

		if (por == null) {
			throw new IllegalArgumentException("por is nulll.");
		}

		por = por.trim();

		if (por.length() == 0) {
			throw new IllegalArgumentException("por is empty.");
		}

		if (por.equals(POR_CENTRO_DE_COSTO) == false && por.equals(POR_NOMBRE) == false) {
			throw new IllegalArgumentException(
					"por is not " + POR_CENTRO_DE_COSTO + " | " + POR_NOMBRE + ".");
		}

		// =============================================================================

		Map<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("ejercicio", ejercicioContableId);
		queryParams.put("por", por);

		// ---------------------------------------------------------------------------

		return exec("/CentroCostoContable/findAllPagin", db, pageRequest, lastIndexOld, queryParams);

	}

}
