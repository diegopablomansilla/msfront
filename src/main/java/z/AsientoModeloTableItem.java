package z;

import javafx.beans.property.SimpleStringProperty;
import x.com.ms.front.model.Entity;

class AsientoModeloTableItem extends Entity {

	private final SimpleStringProperty numero = new SimpleStringProperty();
	private final SimpleStringProperty nombre = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty numeroProperty() {
		return numero;
	}

	public SimpleStringProperty nombreProperty() {
		return nombre;
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

	// ---------------------------------------------------------------

}
