package com.ms;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.ms.front.view.modulos.ModuloContabilidadGeneralController;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Home extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		// --------------------------------------------------------------------------------------

//		String apiHome = "https://api.massoftware.com";
		String apiHome = "http://localhost:4567";
		String apiVersion = "v1";
		Integer defaultPaginLimit = 100;
		// --------------------------------------------------------------------------------------

		System.out.println("Start");

		try {

			EnvVars.setApiHome(apiHome);
			EnvVars.setApiVersion(apiVersion);
			EnvVars.setDefaultPaginLimit(defaultPaginLimit);
			// --------------------------------------------------------------------------------------

			ModuloContabilidadGeneralController.show(stage);

			// --------------------------------------------------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("End");

	}

	private void onClose(WindowEvent event) {
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream("tasks.xml");
//			XMLEncoder encoder = new XMLEncoder(out);
//			encoder.writeObject(controller.getTasksMap());
//			encoder.close();
//		} catch (Exception e) {
//			if (out != null)
//				try {
//					out.close();
//				} catch (IOException ex) {
//					ex.printStackTrace();
//				}
//		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void x() {

		// inline will store the JSON data streamed in string format
		String inline = "";

		try {
			URL url = new URL("http://localhost:4567/rpc/v1/CuentaContable/findAll?ejercicioContable=2002");
			// Parse URL into HttpURLConnection in order to open the connection in order to
			// get the JSON data
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Set the request to GET or POST as per the requirements
			conn.setRequestMethod("GET");

//			List<String> params = new ArrayList<String>();
//			params.add("db1");
//			
//			conn.getHeaderFields().put("dbKey", params);

			conn.setRequestProperty("dbKey", "db1");

			System.out.println(conn.getRequestProperty("dbKey"));

			// Use the connect method to create the connection bridge
			conn.connect();
			System.out.println(conn);
			// Get the response status of the Rest API
			int responsecode = conn.getResponseCode();
			System.out.println("Response code is: " + responsecode);

			// Iterating condition to if response code is not 200 then throw a runtime
			// exception
			// else continue the actual process of getting the JSON data
			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				// Scanner functionality will read the JSON data from the stream
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				System.out.println("\nJSON Response in String format");
				System.out.println(inline);
				// Close the stream when reading the data has been finished
				sc.close();
			}

			// Disconnect the HttpURLConnection stream
			conn.disconnect();

			System.out.println(inline);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
