package com.ms.front;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

// java -jar ms_front.jar --module-path D:\dev\jdk\openjfx-13.0.1_windows-x64_bin-sdk\javafx-sdk-13.0.1\lib --add-modules=javafx.controls


// java --module-path %PATH_TO_FX% --add-modules javafx.controls HelloFX

set PATH_TO_FX="D:\\dev\\jdk\\openjfx-13.0.1_windows-x64_bin-sdk\\javafx-sdk-13.0.1\\lib"

public class EnvVars {

	private static String apiHome;

	private EnvVars() {

	}

	public static String getApiHome() {
		if (apiHome == null || apiHome.trim().length() == 0) {
			loadApiHome();
		}

		return apiHome;
	}

	private static void setApiHome(String apiHome) {
		EnvVars.apiHome = apiHome.trim();
	}

	private static void loadApiHome() {
		try {

			String API_FILES = System.getenv().get("MS_API_FILES");

			if (API_FILES == null || API_FILES.trim().length() == 0) {
//				API_FILES = "C:/mssapi";
				API_FILES = "D:/dev/source/msapi/configuracion/msapi";

				System.out.println(
						"[WARNING] No se encontro la variable de entorno MS_API_FILES. Se usa como valor por defecto "
								+ API_FILES);
			}

			FileReader reader = new FileReader(API_FILES + File.separatorChar + "tareasapi.properties");

			Properties p = new Properties();
			p.load(reader);

			setApiHome(p.getProperty("api.host"));

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
