package com.ms.view.ejercicio_contable;

import java.time.LocalDate;

public class EjercicioContableServices {

	public EjercicioContable findById(String id) {

		EjercicioContable item = new EjercicioContable();
		item.setId(id);
		item.setNumero(2020);
		item.setCerrado(true);
		item.setApertura(LocalDate.now());
		item.setComentario("dfsaf  sdfj sadfs sdf sdf s");

		return item;
	}

}
