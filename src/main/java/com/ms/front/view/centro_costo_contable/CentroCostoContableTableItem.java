package com.ms.front.view.centro_costo_contable;

import com.ms.front.model.Entity;

import javafx.beans.property.SimpleStringProperty;

public class CentroCostoContableTableItem extends Entity {

	private final SimpleStringProperty numero = new SimpleStringProperty();
	private final SimpleStringProperty abreviatura = new SimpleStringProperty();
	private final SimpleStringProperty nombre = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty codigoProperty() {
		return numero;
	}

	public SimpleStringProperty abreviaturaProperty() {
		return abreviatura;
	}

	public SimpleStringProperty nombreProperty() {
		return nombre;
	}

	// ---------------------------------------------------------------

	public String getNumero() {
		this.numero.set(this.emptyIsNull(numero.get()));
		return numero.get();
	}

	public void setNumero(String numero) {
		this.numero.set(this.emptyIsNull(numero));
	}

	public String getNombre() {
		this.nombre.set(this.emptyIsNull(nombre.get()));
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(this.emptyIsNull(nombre));
	}

	public String getAbreviatura() {
		this.abreviatura.set(this.emptyIsNull(abreviatura.get()));
		return abreviatura.get();
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura.set(this.emptyIsNull(abreviatura));
	}

	// ---------------------------------------------------------------

}
