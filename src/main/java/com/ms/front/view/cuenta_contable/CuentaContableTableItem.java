package com.ms.front.view.cuenta_contable;

import com.ms.front.model.Entity;

import javafx.beans.property.SimpleStringProperty;

public class CuentaContableTableItem extends Entity {

	private final SimpleStringProperty codigo = new SimpleStringProperty();
	private final SimpleStringProperty nombre = new SimpleStringProperty();
	private final SimpleStringProperty centroCostoContable = new SimpleStringProperty();
	private final SimpleStringProperty cuentaAgrupadora = new SimpleStringProperty();
	private final SimpleStringProperty porcentaje = new SimpleStringProperty();

	// ---------------------------------------------------------------

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

	public String getCodigo() {
		this.codigo.set(this.emptyIsNull(codigo.get()));
		return codigo.get();
	}

	public void setCodigo(String codigo) {
		this.codigo.set(this.emptyIsNull(codigo));
	}

	public String getNombre() {
		this.nombre.set(this.emptyIsNull(nombre.get()));
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(this.emptyIsNull(nombre));
	}

	public String getCentroCostoContable() {
		this.centroCostoContable.set(this.emptyIsNull(centroCostoContable.get()));
		return centroCostoContable.get();
	}

	public void setCentroCostoContable(String centroCostoContable) {
		this.centroCostoContable.set(this.emptyIsNull(centroCostoContable));
	}

	public String getCuentaAgrupadora() {
		this.cuentaAgrupadora.set(this.emptyIsNull(cuentaAgrupadora.get()));
		return cuentaAgrupadora.get();
	}

	public void setCuentaAgrupadora(String cuentaAgrupadora) {
		this.cuentaAgrupadora.set(this.emptyIsNull(cuentaAgrupadora));
	}

	public String getPorcentaje() {
		this.porcentaje.set(this.emptyIsNull(porcentaje.get()));
		return porcentaje.get();
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje.set(this.emptyIsNull(porcentaje));
	}

	// ---------------------------------------------------------------

	@Override
	public String toString() {
		return "CuentaContableTableItem [id=" + this.getId() + ", codigo=" + this.getCodigo() + ", nombre="
				+ this.getNombre() + "]";
	}

}
