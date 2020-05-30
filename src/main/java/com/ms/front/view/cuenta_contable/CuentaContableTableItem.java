package com.ms.front.view.cuenta_contable;

import com.ms.front.model.Entity;

import javafx.beans.property.SimpleStringProperty;

public class CuentaContableTableItem extends Entity {

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
	public String toString() {
		return "CuentaContableTableItem [id=" + this.getId() + ", codigo=" + this.getCodigo() + ", nombre="
				+ this.getNombre() + "]";
	}

}
