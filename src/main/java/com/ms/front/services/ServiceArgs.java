package com.ms.front.services;

import java.util.Map;

public abstract class ServiceArgs {

	public static  final String OP_C_ICT_A = "C_ICT_A";
	public static final String OP_C_ICT_A_TXT = "Contiene todas las palabras por búsqueda completa (ignorando mayúsculas y acentos, tildes, etc.)";

	public static final String OP_SW_ICT_O = "SW_ICT_O";
	public static final String OP_SW_ICT_O_TXT = "Comienza con alguna de las palabras por búsqueda completa (ignorando mayúsculas y acentos, tildes, etc.)";

	abstract public Map<String, String> toMap();

}
