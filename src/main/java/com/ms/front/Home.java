package com.ms.front;

import com.ms.front.view.ModuloContabilidadGeneralController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Home extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		try {

			ModuloContabilidadGeneralController.show(stage);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	private void onClose(WindowEvent event) {
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
//	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
