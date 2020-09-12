package com.ms.front.view.centro_costo_contable;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import x.com.ms.front.model.ServiceArgs;

public class CentroCostoContablePaginArgs extends ServiceArgs implements Cloneable {

	private final String CENTRO_DE_COSTO = "CENTRO_DE_COSTO";
	private final String NOMBRE = "NOMBRE";

	public final String KEY_EJERCICIO_CONTABLE = "ejercicio";
	public final String KEY_POR = "por";

	private final SimpleStringProperty ejercicioContable = new SimpleStringProperty();
	private final SimpleStringProperty por = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public CentroCostoContablePaginArgs() {
		super();
	}

	// ---------------------------------------------------------------

	public SimpleStringProperty ejercicioContableProperty() {
		return ejercicioContable;
	}

	public SimpleStringProperty porProperty() {
		return por;
	}

	// ---------------------------------------------------------------

	public String getEjercicioContable() {
		this.ejercicioContable.set(this.emptyIsNull(ejercicioContable.get()));
		return ejercicioContable.get();
	}

	public void setEjercicioContable(String v) {
		this.ejercicioContable.set(this.emptyIsNull(v));
	}

	public String getPor() {
		return por.get();
	}

	public void setPorCentroDeCosto() {
		setPor(CENTRO_DE_COSTO);
	}

	public void setPorNombre() {
		setPor(NOMBRE);
	}

	private void setPor(String v) {
		this.por.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	// ---------------------------------------------------------------

	public Map<String, String> toMap() {

		Map<String, String> map = new HashMap<String, String>();

		if (this.getEjercicioContable() != null) {
			map.put(KEY_EJERCICIO_CONTABLE, this.getEjercicioContable());
		}

		if (this.getPor() != null) {
			map.put(KEY_POR, this.getPor());
		}

		return map;
	}

	@Override
	public CentroCostoContablePaginArgs clone() {

		CentroCostoContablePaginArgs other = new CentroCostoContablePaginArgs();

		other.setEjercicioContable(getEjercicioContable());
		other.setPor(getPor());

		return other;
	}

	@Override
	public String toString() {
		return "CuentaContableTableFilter [getEjercicioContable()=" + getEjercicioContable() + ", getPor()=" + getPor()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEjercicioContable() == null) ? 0 : getEjercicioContable().hashCode());
		result = prime * result + ((getPor() == null) ? 0 : getPor().hashCode());
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
		CentroCostoContablePaginArgs other = (CentroCostoContablePaginArgs) obj;
		if (getEjercicioContable() == null) {
			if (other.getEjercicioContable() != null)
				return false;
		} else if (!getEjercicioContable().equals(other.getEjercicioContable()))
			return false;
		if (getPor() == null) {
			if (other.getPor() != null)
				return false;
		} else if (!getPor().equals(other.getPor()))
			return false;
		return true;
	}

	// ---------------------------------------------------------------

}
