package com.ms.front.services;

import java.util.HashMap;
import java.util.Map;

import com.ms.front.commons.services.AbstractFindOneByTextService;

import x.com.ms.front.model.IdDesc;

public class CentroCostoContableFindOneByText extends AbstractFindOneByTextService {

	public IdDesc find(String db, String text, String ejercicioContableId) throws Exception {

		// ---------------------------------------------------------------------------

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is nulll.");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty.");
		}

		// ---------------------------------------------------------------------------

		// =============================================================================

		Map<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("ejercicio", ejercicioContableId);

		// ---------------------------------------------------------------------------

		return exec("/CentroCostoContable/findOneByText", db, text, queryParams);

	}

}
