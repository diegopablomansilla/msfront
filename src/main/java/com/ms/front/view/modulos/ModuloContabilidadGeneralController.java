package com.ms.front.view.modulos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ms.front.view.cuenta_contable.CuentaContableTable;
import com.ms.front.view.JavaFXUtil;
import com.ms.front.view.cuenta_contable.CuentaContablePaginArgs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModuloContabilidadGeneralController implements Initializable {

	@FXML
	public VBox view;

	// ----------------------------------------------------------------------

	private static final String TITLE = "Contabilidad general";

	// ----------------------------------------------------------------------

	public void initialize(URL url, ResourceBundle rb) {
	}

	public static ModuloContabilidadGeneralController loadView() throws IOException {

		FXMLLoader loader = new FXMLLoader(
				ModuloContabilidadGeneralController.class.getResource("ModuloContabilidadGeneral.fxml"));

		loader.load();

		ModuloContabilidadGeneralController viewController = loader.getController();

		return viewController;
	}

	public static void show(Stage stage) throws IOException {

		ModuloContabilidadGeneralController viewController = loadView();

		stage.setScene(new Scene(viewController.view));
		stage.setTitle(TITLE);
		stage.setAlwaysOnTop(false);
//		stage.setResizable(false);
		stage.centerOnScreen();

//		stage.initStyle(StageStyle.UTILITY);
//		stage.initStyle(StageStyle.DECORATED);
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.initStyle(StageStyle.TRANSPARENT);

		stage.show();

	}

	// ----------------------------------------------------------------------

	@FXML
	void onOpenCuentaContableList(ActionEvent event) {
		try {
			
			CuentaContablePaginArgs filter = new CuentaContablePaginArgs();
			filter.setEjercicioContable("2002");
			
//			CuentaContableTableController.show(new Stage(), view, filter);
			Object o = CuentaContableTable.showAndWait(new Stage(), view, filter);
			System.out.println("=================================================================");
			System.out.println("O : " + o);
			System.out.println("=================================================================");
		} catch (IOException e) {
			JavaFXUtil.buildAlertException(e);
		}

	}

}
