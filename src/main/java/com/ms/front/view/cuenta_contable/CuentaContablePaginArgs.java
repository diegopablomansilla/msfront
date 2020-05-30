package com.ms.front.view.cuenta_contable;

import java.util.HashMap;
import java.util.Map;

import com.ms.front.services.ServiceArgs;

import javafx.beans.property.SimpleStringProperty;

public class CuentaContablePaginArgs extends ServiceArgs implements Cloneable {

	private final String CUENTA_CONTABLE = "CUENTA_CONTABLE";
	private final String NOMBRE = "NOMBRE";
	private final String CUENTA_AGRUPADORA = "CUENTA_AGRUPADORA";
	private final String CENTRO_DE_COSTO = "CENTRO_DE_COSTO";
	private final String PUNTO_DE_EQUILIBRIO = "PUNTO_DE_EQUILIBRIO";

	public final String KEY_EJERCICIO_CONTABLE = "ejercicio";
	public final String KEY_FILTRO = "filtro";
	public final String KEY_POR = "por";
	public final String KEY_OPERADOR = "op";

	private final SimpleStringProperty ejercicioContable = new SimpleStringProperty();
	private final SimpleStringProperty filtro = new SimpleStringProperty();
	private final SimpleStringProperty por = new SimpleStringProperty();
	private final SimpleStringProperty operador = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public CuentaContablePaginArgs() {
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

	public SimpleStringProperty operadorProperty() {
		return operador;
	}

	// ---------------------------------------------------------------

	public String getEjercicioContable() {
		this.ejercicioContable
				.set(ejercicioContable.get() == null || ejercicioContable.get().trim().length() == 0 ? null
						: ejercicioContable.get().trim());
		return ejercicioContable.get();
	}

	public void setEjercicioContable(String v) {
		this.ejercicioContable.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	public String getFiltro() {
		this.filtro.set(filtro.get() == null || filtro.get().trim().length() == 0 ? null : filtro.get().trim());
		return filtro.get();
	}

	public void setFiltro(String v) {
		this.filtro.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	public String getPor() {
		return por.get();
	}

	public void setPorCuentaContable() {
		setPor(CUENTA_CONTABLE);
	}

	public void setPorNombre() {
		setPor(NOMBRE);
	}

	public void setPorCuentaAgrupadora() {
		setPor(CUENTA_AGRUPADORA);
	}

	public void setPorCentroDeCosto() {
		setPor(CENTRO_DE_COSTO);
	}

	public void setPorPuntoDeEquilibrio() {
		setPor(PUNTO_DE_EQUILIBRIO);
	}

	private void setPor(String v) {
		this.por.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	public String getOperador() {
		return operador.get();
	}

	public void setOperador(String v) {
		this.operador.set(v == null || v.trim().length() == 0 ? null : v.trim());
	}

	// ---------------------------------------------------------------

	public Map<String, String> toMap() {

		Map<String, String> map = new HashMap<String, String>();

		if (this.getEjercicioContable() != null) {
			map.put(KEY_EJERCICIO_CONTABLE, this.getEjercicioContable());
		}

		if (this.getFiltro() != null) {
			map.put(KEY_FILTRO, this.getFiltro());
		}

		if (this.getPor() != null) {
			map.put(KEY_POR, this.getPor());
		}

		if (this.getOperador() != null) {
			map.put(KEY_OPERADOR, this.getOperador());
		}

		return map;
	}

	@Override
	public CuentaContablePaginArgs clone() {

		CuentaContablePaginArgs other = new CuentaContablePaginArgs();

		other.setEjercicioContable(getEjercicioContable());
		other.setFiltro(this.getFiltro());
		other.setPor(getPor());
		other.setOperador(getOperador());

		return other;
	}

	@Override
	public String toString() {
		return "CuentaContableTableFilter [getEjercicioContable()=" + getEjercicioContable() + ", getFiltro()="
				+ getFiltro() + ", getPor()=" + getPor() + ", getOperador()=" + getOperador() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEjercicioContable() == null) ? 0 : getEjercicioContable().hashCode());
		result = prime * result + ((getFiltro() == null) ? 0 : getFiltro().hashCode());
		result = prime * result + ((getOperador() == null) ? 0 : getOperador().hashCode());
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
		CuentaContablePaginArgs other = (CuentaContablePaginArgs) obj;
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
		if (getOperador() == null) {
			if (other.getOperador() != null)
				return false;
		} else if (!getOperador().equals(other.getOperador()))
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
