package com.ms.view.ejercicio_contable;

import java.time.LocalDate;

import com.ms.Entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EjercicioContable extends Entity implements Cloneable, Comparable<EjercicioContable> {

	private final StringProperty id = new SimpleStringProperty();
	private final IntegerProperty numero = new SimpleIntegerProperty();
	private final ObjectProperty<LocalDate> apertura = new SimpleObjectProperty<>();
	private final ObjectProperty<LocalDate> cierre = new SimpleObjectProperty<>();
	private final BooleanProperty cerrado = new SimpleBooleanProperty();
	private final BooleanProperty cerradoModulos = new SimpleBooleanProperty();
	private final StringProperty comentario = new SimpleStringProperty();

	public EjercicioContable() {

	}

	public EjercicioContable(EjercicioContable other) {
		setByOther(other);
	}

	// -----------------------------------------------------

	public StringProperty idProperty() {
		return id;
	}

	public void setId(String value) {
		this.id.set(trim(value));
	}

	public String getId() {
		return this.id.get();
	}

	// -----------------------------------------------------

	public IntegerProperty numeroProperty() {
		return numero;
	}

	public void setNumero(Integer value) {
		this.numero.set(value);
	}

	public Integer getNumero() {
		return this.numero.get();
	}

	// -----------------------------------------------------

	public ObjectProperty<LocalDate> aperturaProperty() {
		return apertura;
	}

	public void setApertura(LocalDate value) {
		this.apertura.set(value);
	}

	public LocalDate getApertura() {
		return this.apertura.get();
	}

	// -----------------------------------------------------

	public ObjectProperty<LocalDate> cierreProperty() {
		return cierre;
	}

	public void setCierre(LocalDate value) {
		this.cierre.set(value);
	}

	public LocalDate getCierre() {
		return this.cierre.get();
	}
	// -----------------------------------------------------

	public BooleanProperty cerradoProperty() {
		return cerrado;
	}

	public void setCerrado(Boolean value) {
		this.cerrado.set(falseIsNull(value));
	}

	public Boolean getCerrado() {
		return this.cerrado.get();
	}

	// -----------------------------------------------------

	public BooleanProperty cerradoModulosProperty() {
		return cerrado;
	}

	public void setCerradoModulos(Boolean value) {
		this.cerradoModulos.set(falseIsNull(value));
	}

	public Boolean getCerradoModulos() {
		return this.cerradoModulos.get();
	}

	// -----------------------------------------------------

	public StringProperty comentarioProperty() {
		return comentario;
	}

	public void setComentario(String value) {
		this.comentario.set(trim(value));
	}

	public String getComentario() {
		return this.comentario.get();
	}

	// -----------------------------------------------------

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

		EjercicioContable other = (EjercicioContable) obj;

		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;

		return true;
	}

	@Override
	public int compareTo(EjercicioContable other) {

		return this.getId().compareTo(other.getId());
	}

	@Override
	public String toString() {

		if (this.getId() != null) {
			return this.getId();
		}

		return "";
	}

	public EjercicioContable clone() {

		EjercicioContable other = new EjercicioContable();

		other.setId(this.getId());

		other.setNumero(this.getNumero());
		other.setApertura(this.getApertura());
		other.setCierre(this.getCierre());
		other.setCerrado(this.getCerrado());
		other.setCerradoModulos(this.getCerradoModulos());
		other.setComentario(this.getComentario());

		// -------------------------------------------------------------------

		return other;

		// -------------------------------------------------------------------
	}
	
	public void setByOther(EjercicioContable other) {
		this.setId(other.getId());

		this.setNumero(other.getNumero());
		this.setApertura(other.getApertura());
		this.setCierre(other.getCierre());
		this.setCerrado(other.getCerrado());
		this.setCerradoModulos(other.getCerradoModulos());
		this.setComentario(other.getComentario());
	}

	public boolean equalsFull(Object obj) {

		if (super.equals(obj) == false) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		EjercicioContable other = (EjercicioContable) obj;

		// -------------------------------------------------------------------

		if (equals(this.getNumero(), other.getNumero()) == false) {
			return false;
		}

		if (equals(this.getApertura(), other.getApertura()) == false) {
			return false;
		}

		if (equals(this.getCierre(), other.getCierre()) == false) {
			return false;
		}

		if (equals(this.getCerrado(), other.getCerrado()) == false) {
			return false;
		}

		if (equals(this.getCerradoModulos(), other.getCerradoModulos()) == false) {
			return false;
		}

		if (equals(this.getComentario(), other.getComentario()) == false) {
			return false;
		}

		// -------------------------------------------------------------------

		return true;

		// -------------------------------------------------------------------
	}

}
