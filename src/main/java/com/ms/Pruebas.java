package com.ms;

public class Pruebas {

	public static void main(String[] args) {
		try {
			String apiHome = "http://localhost:4567";
			String apiVersion = "v1";
			EnvVars.setApiHome(apiHome);
			EnvVars.setApiVersion(apiVersion);
			
//			EjercicioContableFinAllPagin x = new EjercicioContableFinAllPagin();
//			x.exec("db1", "FIRST", 0);	
		} catch(Exception e) {
			e.printStackTrace();
		}		

	}

}
