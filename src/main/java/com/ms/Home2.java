package com.ms;

import com.ms.view.ejercicio_contable.EjercicioContableCrl;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Home2 extends Application {

	EjercicioContableCrl controller;

	@Override
	public void start(Stage stage) throws Exception {

		EjercicioContableCrl.openWindow("123");
		
//		FXMLLoader loader = EjercicioContableCrl.load();
//		GridPane grid = loader.load();
//		controller = loader.getController();
//		
//		
//
//		Scene scene = new Scene(grid, 600, 400);
//
//		stage.setScene(scene);
//		stage.setTitle("Do-It!!!");
//		stage.setAlwaysOnTop(false);
//		stage.setResizable(false);
//
//		stage.setOnCloseRequest(this::onClose);
////		controller.setTasksMap(readTasksFile());
//		stage.show();
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
}
