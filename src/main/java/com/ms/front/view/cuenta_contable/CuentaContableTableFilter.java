package com.ms.front.view.cuenta_contable;

import javafx.beans.property.SimpleStringProperty;

public class CuentaContableTableFilter implements Cloneable {

	private final SimpleStringProperty filtro = new SimpleStringProperty();
	private final SimpleStringProperty por = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public CuentaContableTableFilter() {
		super();
	}

	// ---------------------------------------------------------------

	public SimpleStringProperty filtroProperty() {
		return filtro;
	}

	public SimpleStringProperty porProperty() {
		return por;
	}

	// ---------------------------------------------------------------

	public String getFiltro() {
		return filtro.get();
	}

	public void setFiltro(String filtro) {
		this.filtro.set(filtro);
	}

	public String getPor() {
		return por.get();
	}

	public void setPor(String por) {
		this.por.set(por);
	}

	// ---------------------------------------------------------------

	@Override
	protected CuentaContableTableFilter clone() {

		CuentaContableTableFilter other = new CuentaContableTableFilter();

		other.setFiltro(this.getFiltro());

		return other;
	}

	@Override
	public String toString() {
		return "CuentaContableTableFilter [filtro=" + this.getFiltro() + ", por=" + this.getPor() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filtro == null) ? 0 : filtro.hashCode());
		result = prime * result + ((por == null) ? 0 : por.hashCode());
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
