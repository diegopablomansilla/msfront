package com.ms.front.view.cuenta_contable;

import javafx.beans.property.SimpleStringProperty;

public class CuentaContableTableItem {

	private final SimpleStringProperty id = new SimpleStringProperty();
	private final SimpleStringProperty codigo = new SimpleStringProperty();
	private final SimpleStringProperty nombre = new SimpleStringProperty();
	private final SimpleStringProperty centroCostoContable = new SimpleStringProperty();
	private final SimpleStringProperty cuentaAgrupadora = new SimpleStringProperty();
	private final SimpleStringProperty porcentaje = new SimpleStringProperty();
	
	// ---------------------------------------------------------------

	public SimpleStringProperty idProperty() {
		return id;
	}

	public SimpleStringProperty codigoProperty() {
		return codigo;
	}

	public SimpleStringProperty nombreProperty() {
		return nombre;
	}

	public SimpleStringProperty centroCostoContableProperty() {
		return centroCostoContable;
	}

	public SimpleStringProperty cuentaAgrupadoraProperty() {
		return cuentaAgrupadora;
	}

	public SimpleStringProperty porcentajeProperty() {
		return porcentaje;
	}

	// ---------------------------------------------------------------

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getCodigo() {
		return codigo.get();
	}

	public void setCodigo(String codigo) {
		this.codigo.set(codigo);
	}

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public String getCentroCostoContable() {
		return centroCostoContable.get();
	}

	public void setCentroCostoContable(String centroCostoContable) {
		this.centroCostoContable.set(centroCostoContable);
	}

	public String getCuentaAgrupadora() {
		return cuentaAgrupadora.get();
	}

	public void setCuentaAgrupadora(String cuentaAgrupadora) {
		this.cuentaAgrupadora.set(cuentaAgrupadora);
	}

	public String getPorcentaje() {
		return porcentaje.get();
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje.set(porcentaje);
	}

	// ---------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		CuentaContableTableItem other = (CuentaContableTableItem) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CuentaContableTableItem [id=" + this.getId() + ", codigo=" + this.getCodigo() + ", nombre=" + this.getNombre() + "]";
	}
	
	

}
