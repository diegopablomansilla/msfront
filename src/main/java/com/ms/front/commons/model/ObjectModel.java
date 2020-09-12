package com.ms.front.commons.model;

import javax.json.JsonObject;

public abstract class ObjectModel {

	protected String emptyIsNull(String value) {
		return (value == null || value.trim().length() == 0) ? null : value.trim();

	}

	protected Boolean falseIsNull(Boolean value) {
		return (value == null) ? false : value;
	}

	protected boolean equals(Object obj, Object other) {

		if (other == null && obj != null) {
			return false;
		}

		if (other != null && obj == null) {
			return false;
		}

		if (other != null && obj != null) {

			if (other.equals(obj) == false) {
				return false;
			}

		}

		return true;

	}

	public JsonObject toJson() {
		return null;
	}

}
