package com.ms.front.view.centro_costo_contable;

import java.util.HashMap;
import java.util.Map;

import com.ms.front.model.ServiceArgs;

import javafx.beans.property.SimpleStringProperty;

public class CentroCostoIdDescArgs extends ServiceArgs implements Cloneable {

	public final String KEY_EJERCICIO_CONTABLE = "ejercicio";

	private final SimpleStringProperty ejercicioContable = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public CentroCostoIdDescArgs() {
		super();
	}

	// ---------------------------------------------------------------

	public SimpleStringProperty ejercicioContableProperty() {
		return ejercicioContable;
	}

	// ---------------------------------------------------------------

	public String getEjercicioContable() {
		this.ejercicioContable.set(this.emptyIsNull(ejercicioContable.get()));
		return ejercicioContable.get();
	}

	public void setEjercicioContable(String v) {
		this.ejercicioContable.set(this.emptyIsNull(v));
	}

	// ---------------------------------------------------------------

	public Map<String, String> toMap() {

		Map<String, String> map = new HashMap<String, String>();

		if (this.getEjercicioContable() != null) {
			map.put(KEY_EJERCICIO_CONTABLE, this.getEjercicioContable());
		}

		return map;
	}

	@Override
	public CentroCostoIdDescArgs clone() {

		CentroCostoIdDescArgs other = new CentroCostoIdDescArgs();

		other.setEjercicioContable(getEjercicioContable());

		return other;
	}

	@Override
	public String toString() {
		return "CentroCostoIdDescArgs [getEjercicioContable()=" + getEjercicioContable() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEjercicioContable() == null) ? 0 : getEjercicioContable().hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentroCostoIdDescArgs other = (CentroCostoIdDescArgs) obj;
		if (getEjercicioContable() == null) {
			if (other.getEjercicioContable() != null)
				return false;
		} else if (!getEjercicioContable().equals(other.getEjercicioContable()))
			return false;

		return true;
	}

	// ---------------------------------------------------------------

}
