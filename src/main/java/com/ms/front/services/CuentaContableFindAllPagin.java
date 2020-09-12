package com.ms.front.services;

import java.util.HashMap;
import java.util.Map;

import com.ms.front.commons.services.AbstractFindAllPaginService;
import com.ms.front.model.Pagin;

public class CuentaContableFindAllPagin extends AbstractFindAllPaginService {

	protected static final String POR_CUENTA_CONTABLE = "CUENTA_CONTABLE";
	protected static final String POR_NOMBRE = "NOMBRE";
	protected static final String POR_CUENTA_AGRUPADORA = "CUENTA_AGRUPADORA";
	protected static final String POR_CENTRO_DE_COSTO = "CENTRO_DE_COSTO";
	protected static final String POR_PUNTO_DE_EQUILIBRIO = "PUNTO_DE_EQUILIBRIO";

	public static final String OP_SW_ICT_O = "SW_ICT_O";
	public static final String OP_C_ICT_A = "C_ICT_A";

	public Pagin findFirst(String db, String ejercicioContableId, String por, String operador, String filtroValue)
			throws Exception {
		return exec(db, PAGE_REQUEST_FIRST, null, ejercicioContableId, por, operador, filtroValue);
	}

	public Pagin findNext(String db, Integer lastIndexOld, String ejercicioContableId, String por, String operador,
			String filtroValue) throws Exception {
		return exec(db, PAGE_REQUEST_NEXT, lastIndexOld, ejercicioContableId, por, operador, filtroValue);
	}

	public Pagin findBack(String db, Integer lastIndexOld, String ejercicioContableId, String por, String operador,
			String filtroValue) throws Exception {
		return exec(db, PAGE_REQUEST_BACK, lastIndexOld, ejercicioContableId, por, operador, filtroValue);
	}

	public Pagin findLast(String db, String ejercicioContableId, String por, String operador, String filtroValue)
			throws Exception {
		return exec(db, PAGE_REQUEST_LAST, null, ejercicioContableId, por, operador, filtroValue);
	}

	private Pagin exec(String db, String pageRequest, Integer lastIndexOld, String ejercicioContableId, String por,
			String operador, String filtroValue) throws Exception {

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

		if (por.equals(POR_CUENTA_CONTABLE) == false && por.equals(POR_NOMBRE) == false
				&& por.equals(POR_CUENTA_AGRUPADORA) == false && por.equals(POR_CENTRO_DE_COSTO) == false
				&& por.equals(POR_PUNTO_DE_EQUILIBRIO) == false) {

			throw new IllegalArgumentException("por is not " + POR_CUENTA_CONTABLE + " | " + POR_NOMBRE + " | "
					+ POR_CUENTA_AGRUPADORA + " | " + POR_CENTRO_DE_COSTO + " | " + POR_PUNTO_DE_EQUILIBRIO + ".");
		}

		// ---------------------------------------------------------------------------

		if (operador == null) {
			throw new IllegalArgumentException("por is nulll.");
		}

		operador = operador.trim();

		if (operador.length() == 0) {
			throw new IllegalArgumentException("por is empty.");
		}

		if (operador.equals(OP_SW_ICT_O) == false && operador.equals(OP_C_ICT_A) == false) {

			throw new IllegalArgumentException("operador is not " + OP_SW_ICT_O + " | " + OP_C_ICT_A + ".");
		}

		// ---------------------------------------------------------------------------

		if (filtroValue != null) {
			filtroValue = filtroValue.trim();

			if (filtroValue.length() == 0) {
				filtroValue = null;
			}
		}

		// =============================================================================

		Map<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("ejercicio", ejercicioContableId);
		queryParams.put("por", por);
		queryParams.put("op", operador);
		if (filtroValue != null) {
			queryParams.put("filtro", filtroValue);
		}

		// ---------------------------------------------------------------------------

		return exec("/CuentaContable/findAllPagin", db, pageRequest, lastIndexOld, queryParams);

	}

}
