package com.ms.front.view.punto_equilibrio;

import com.ms.front.model.Entity;

import javafx.beans.property.SimpleStringProperty;

public class PuntoEquilibrioTableItem extends Entity {

	private final SimpleStringProperty numero = new SimpleStringProperty();
	private final SimpleStringProperty nombre = new SimpleStringProperty();
	private final SimpleStringProperty tipoPuntoEquilibrio = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty numeroProperty() {
		return numero;
	}
	
	public SimpleStringProperty nombreProperty() {
		return nombre;
	}

	public SimpleStringProperty tipoPuntoEquilibrioProperty() {
		return tipoPuntoEquilibrio;
	}

	// ---------------------------------------------------------------

	public String getNumero() {
		return numero.get();
	}

	public void setNumero(String numero) {
		this.numero.set(this.emptyIsNull(numero));
	}

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(this.emptyIsNull(nombre));
	}

	public String getTipoPuntoEquilibrio() {
		return tipoPuntoEquilibrio.get();
	}

	public void setTipoPuntoEquilibrio(String tipoPuntoEquilibrio) {
		this.tipoPuntoEquilibrio.set(tipoPuntoEquilibrio);
	}

	// ---------------------------------------------------------------

}
