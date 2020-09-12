package x.com.ms.front.model;

import java.util.HashMap;
import java.util.Map;

public class ServiceArgs extends ObjectModel implements Cloneable {

	public static final String OP_C_ICT_A = "C_ICT_A";
	public static final String OP_C_ICT_A_TXT = "Contiene todas las palabras por búsqueda completa (ignorando mayúsculas y acentos, tildes, etc.)";

	public static final String OP_SW_ICT_O = "SW_ICT_O";
	public static final String OP_SW_ICT_O_TXT = "Comienza con alguna de las palabras por búsqueda completa (ignorando mayúsculas y acentos, tildes, etc.)";

	// se espera que solo se guarden tipos que pasan por valor
	private Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> toMap() {
		return map;
	}

	public Object get(String key) {
		return map.get(key);
	}

	public Object put(String key, String value) {
		return map.put(key, value);
	}
	
	

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public ServiceArgs clone() {

		ServiceArgs other = new ServiceArgs();
		other.map = (Map<String, String>) ((HashMap<String, String>) map).clone();

		return other;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		ServiceArgs other = (ServiceArgs) obj;

		return map.equals(other.map);

	}

}
