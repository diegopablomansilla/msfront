package com.ms.old;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ms.front.view.cuenta_contable.CuentaContableTableItem;

class CuentaContableService {

	public static List<CuentaContableTableItem> items = new ArrayList<CuentaContableTableItem>();

	static {

		for (int i = 0; i < 200; i++) {

			CuentaContableTableItem item = new CuentaContableTableItem();

			item.setId(UUID.randomUUID().toString());
			item.setCodigo("" + i);
			item.setNombre("CC " + i);
			item.setCentroCostoContable("(##) Centro costo contable");
			item.setCuentaAgrupadora(item.getNombre());
			item.setPorcentaje("" + i + 0.0);

			items.add(item);

		}

	}

}
