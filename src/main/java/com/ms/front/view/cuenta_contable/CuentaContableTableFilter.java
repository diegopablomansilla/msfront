package com.ms.front.view.cuenta_contable;

import javafx.beans.property.SimpleStringProperty;

public class CuentaContableTableFilter implements Cloneable {

	private final SimpleStringProperty ejercicioContable = new SimpleStringProperty();
	private final SimpleStringProperty filtro = new SimpleStringProperty();
	private final SimpleStringProperty por = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public CuentaContableTableFilter() {
		super();
	}

	// ---------------------------------------------------------------

	public SimpleStringProperty ejercicioContableProperty() {
		return ejercicioContable;
	}

	public SimpleStringProperty filtroProperty() {
		return filtro;
	}

	public SimpleStringProperty porProperty() {
		return por;
	}

	// ---------------------------------------------------------------

	public String getEjercicioContable() {
		return ejercicioContable.get();
	}

	public void setEjercicioContable(String v) {
		this.ejercicioContable.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	public String getFiltro() {
		return filtro.get();
	}

	public void setFiltro(String v) {
		this.filtro.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	public String getPor() {
		return por.get();
	}

	public void setPor(String v) {
		this.por.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	// ---------------------------------------------------------------

	@Override
	protected CuentaContableTableFilter clone() {

		CuentaContableTableFilter other = new CuentaContableTableFilter();

		other.setEjercicioContable(getEjercicioContable());
		other.setFiltro(this.getFiltro());
		other.setPor(getPor());

		return other;
	}

	@Override
	public String toString() {
		return "CuentaContableTableFilter [getEjercicioContable()=" + getEjercicioContable() + ", getFiltro()="
				+ getFiltro() + ", getPor()=" + getPor() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEjercicioContable() == null) ? 0 : getEjercicioContable().hashCode());
		result = prime * result + ((getFiltro() == null) ? 0 : getFiltro().hashCode());
		result = prime * result + (( getPor() == null) ? 0 :  getPor().hashCode());
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
		CuentaContableTableFilter other = (CuentaContableTableFilter) obj;
		if (getEjercicioContable() == null) {
			if (other.getEjercicioContable() != null)
				return false;
		} else if (!getEjercicioContable().equals(other.getEjercicioContable()))
			return false;
		if (getFiltro() == null) {
			if (other.getFiltro() != null)
				return false;
		} else if (!getFiltro().equals(other.getFiltro()))
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
