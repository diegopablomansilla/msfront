package com.ms.front.model;

import javafx.beans.property.SimpleStringProperty;

public abstract class Entity extends ObjectModel {

	protected final SimpleStringProperty id = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty idProperty() {
		return id;
	}

	// ---------------------------------------------------------------

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
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
		Entity other = (Entity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CuentaContableTableItem [id=" + this.getId() + "]";
	}

}
