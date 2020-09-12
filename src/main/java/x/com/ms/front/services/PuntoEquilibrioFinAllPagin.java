package x.com.ms.front.services;

import java.util.HashMap;
import java.util.Map;

import x.com.ms.front.commons.services.FinAllPagin;
import x.com.ms.front.model.Pagin;

public class PuntoEquilibrioFinAllPagin extends FinAllPagin {

	protected String getUrlString() {
		return "PuntoEquilibrio/findAllPagin";
	}

	public Pagin finFirst(String db, String ejercicioContableId) throws Exception {
		Map<String, String> args = new HashMap<String, String>();
		args.put("ejercicio", ejercicioContableId);

		return super.findFirst(db, args);
	}

	public Pagin finNext(String db, Integer lastIndexOld, String ejercicioContableId) throws Exception {
		Map<String, String> args = new HashMap<String, String>();
		args.put("ejercicio", ejercicioContableId);

		return super.findNext(db, lastIndexOld, args);
	}

	public Pagin finBack(String db, Integer lastIndexOld, String ejercicioContableId) throws Exception {
		Map<String, String> args = new HashMap<String, String>();
		args.put("ejercicio", ejercicioContableId);

		return super.findBack(db, lastIndexOld, args);
	}

	public Pagin finLast(String db, String ejercicioContableId) throws Exception {
		Map<String, String> args = new HashMap<String, String>();
		args.put("ejercicio", ejercicioContableId);

		return super.findLast(db, args);
	}

}