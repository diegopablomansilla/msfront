package com.ms;

public class EnvVars {

	public static final int LIMIT_PAGIN_DEFAULT = 20;

	private static String apiHome;
	private static String apiVersion;
	private static Integer defaultPaginLimit = LIMIT_PAGIN_DEFAULT;
	
	private EnvVars() {
		
	}

	public static String getApiHome() {
		return apiHome;
	}

	public static void setApiHome(String apiHome) {
		EnvVars.apiHome = apiHome.trim();
	}

	public static String getApiVersion() {
		return apiVersion;
	}

	public static void setApiVersion(String apiVersion) {
		EnvVars.apiVersion = apiVersion.trim();
	}

	public static Integer getPaginLimit() {
		EnvVars.defaultPaginLimit = defaultPaginLimit == null ? LIMIT_PAGIN_DEFAULT : defaultPaginLimit;
		return defaultPaginLimit;
	}

	public static void setDefaultPaginLimit(Integer defaultPaginLimit) {
		EnvVars.defaultPaginLimit = defaultPaginLimit == null ? LIMIT_PAGIN_DEFAULT : defaultPaginLimit;
	}

}
