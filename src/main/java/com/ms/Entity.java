package com.ms;

public class Entity {

	public String trim(String value) {
		return (value == null || value.trim().length() == 0) ? null : value.trim();
	}

	public Boolean falseIsNull(Boolean value) {
		return (value == null) ? false : value;
	}
	
	public boolean equals(Object obj, Object other) {
		
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

} // END CLASS -----------------------------------------------------------------