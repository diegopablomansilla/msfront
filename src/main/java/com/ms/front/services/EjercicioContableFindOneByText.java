package com.ms.front.services;

import com.ms.front.commons.services.AbstractFindOneByTextService;

import x.com.ms.front.model.IdDesc;

public class EjercicioContableFindOneByText extends AbstractFindOneByTextService {

	public IdDesc find(String db, String text) throws Exception {

		return exec("/EjercicioContable/findOneByText", db, text);

	}

}
