package com.ms.front.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;

public class IdDescArgs extends ServiceArgs implements Cloneable {

	public final String KEY_DB = "db";
	public final String KEY_TEXT = "text";

	private final SimpleStringProperty db = new SimpleStringProperty();
	private final SimpleStringProperty text = new SimpleStringProperty();

	// ---------------------------------------------------------------

	public IdDescArgs() {
		super();
	}

	// ---------------------------------------------------------------

	public SimpleStringProperty dbProperty() {
		return db;
	}

	public SimpleStringProperty textProperty() {
		return text;
	}

	// ---------------------------------------------------------------

	public String getDb() {
		this.db.set(this.emptyIsNull(db.get()));
		return db.get();
	}

	public void setDb(String db) {
		this.db.set(this.emptyIsNull(db));
	}

	public String getText() {
		this.text.set(this.emptyIsNull(text.get()));
		return text.get();
	}

	public void setText(String text) {
		this.text.set(this.emptyIsNull(text));
	}

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();

		if (this.getDb() != null) {
			map.put(KEY_DB, this.getDb());
		}

		if (this.getText() != null) {
			map.put(KEY_TEXT, this.getText());
		}

		return map;
	}

	@Override
	protected IdDescArgs clone() {

		IdDescArgs other = new IdDescArgs();

		other.setDb(getDb());
		other.setText(getText());

		return other;
	}

	@Override
	public String toString() {
		return "IdDescArgs [getDb()=" + getDb() + ", getText()=" + getText() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getDb() == null) ? 0 : getDb().hashCode());
		result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
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
		IdDescArgs other = (IdDescArgs) obj;
		if (getDb() == null) {
			if (other.getDb() != null)
				return false;
		} else if (!getDb().equals(other.getDb()))
			return false;
		if (getText() == null) {
			if (other.getText() != null)
				return false;
		} else if (!getText().equals(other.getText()))
			return false;

		return true;
	}

	// ---------------------------------------------------------------

}
