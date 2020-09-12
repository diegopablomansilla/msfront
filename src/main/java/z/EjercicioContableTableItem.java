package z;

import javafx.beans.property.SimpleStringProperty;
import x.com.ms.front.model.Entity;

class EjercicioContableTableItem extends Entity {

	private final SimpleStringProperty numero = new SimpleStringProperty();
	private final SimpleStringProperty apertura = new SimpleStringProperty();
	private final SimpleStringProperty cierre = new SimpleStringProperty();
	private final SimpleStringProperty cerrado = new SimpleStringProperty();
	private final SimpleStringProperty cerradoModulos = new SimpleStringProperty();
	private final SimpleStringProperty principal = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public SimpleStringProperty numeroProperty() {
		return numero;
	}

	public SimpleStringProperty aperturaProperty() {
		return apertura;
	}

	public SimpleStringProperty cierreProperty() {
		return cierre;
	}

	public SimpleStringProperty cerradoProperty() {
		return cerrado;
	}

	public SimpleStringProperty cerradoModulosProperty() {
		return cerradoModulos;
	}

	public SimpleStringProperty principalProperty() {
		return principal;
	}

	// ---------------------------------------------------------------

	public String getNumero() {
		this.numero.set(this.emptyIsNull(numero.get()));
		return numero.get();
	}

	public void setNumero(String numero) {
		this.numero.set(this.emptyIsNull(numero));
	}

	public String getApertura() {
		this.apertura.set(this.emptyIsNull(apertura.get()));
		return apertura.get();
	}

	public void setApertura(String apertura) {
		this.apertura.set(this.emptyIsNull(apertura));
	}

	public String getCierre() {
		this.cierre.set(this.emptyIsNull(cierre.get()));
		return cierre.get();
	}

	public void setCierre(String cierre) {
		this.cierre.set(this.emptyIsNull(cierre));
	}

	public String getCerrado() {
//		this.cerrado.set(this.emptyIsNull(cerrado.get()));
		return cerrado.get();
	}

	public void setCerrado(String cerrado) {
		this.cerrado.set(cerrado);
	}

	public String getCerradoModulos() {
//		this.cerradoModulos.set(this.emptyIsNull(cerradoModulos.get()));
		return cerradoModulos.get();
	}

	public void setCerradoModulos(String cerradoModulos) {
		this.cerradoModulos.set(cerradoModulos);
	}

	public String getPrincipal() {
//		this.principal.set(this.emptyIsNull(principal.get()));
		return principal.get();
	}

	public void setPrincipal(String principal) {
		this.principal.set(principal);
	}

	// ---------------------------------------------------------------

}
