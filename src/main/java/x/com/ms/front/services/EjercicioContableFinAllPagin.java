package x.com.ms.front.services;

import x.com.ms.front.commons.services.FinAllPagin;
import x.com.ms.front.model.Pagin;

public class EjercicioContableFinAllPagin extends FinAllPagin {
	
	protected String getUrlString() {
		return "EjercicioContable/findAllPagin";
	}


	public Pagin finFirst(String db) throws Exception {
		return super.findFirst(db);
	}

	public Pagin finNext(String db, Integer lastIndexOld) throws Exception {
		return super.findNext(db, lastIndexOld);
	}

	public Pagin finBack(String db, Integer lastIndexOld) throws Exception {
		return super.findBack(db, lastIndexOld);
	}

	public Pagin finLast(String db) throws Exception {
		return super.findLast(db);
	}

}