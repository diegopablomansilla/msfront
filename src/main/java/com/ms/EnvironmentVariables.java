package com.ms;

public class EnvironmentVariables {

	public static final int LIMIT_PAGIN_DEFAULT = 20;

	private static String apiHome;
	private static String apiVersion;
	private static Integer defaultPaginLimit = LIMIT_PAGIN_DEFAULT;
	
	private EnvironmentVariables() {
		
	}

	public static String getApiHome() {
		return apiHome;
	}

	public static void setApiHome(String apiHome) {
		EnvironmentVariables.apiHome = apiHome.trim();
	}

	public static String getApiVersion() {
		return apiVersion;
	}

	public static void setApiVersion(String apiVersion) {
		EnvironmentVariables.apiVersion = apiVersion.trim();
	}

	public static Integer getDefaultPaginLimit() {
		EnvironmentVariables.defaultPaginLimit = defaultPaginLimit == null ? LIMIT_PAGIN_DEFAULT : defaultPaginLimit;
		return defaultPaginLimit;
	}

	public static void setDefaultPaginLimit(Integer defaultPaginLimit) {
		EnvironmentVariables.defaultPaginLimit = defaultPaginLimit == null ? LIMIT_PAGIN_DEFAULT : defaultPaginLimit;
	}

}
