package com.ms.old.view.ejercicio_contable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class EjercicioContableCrl implements Initializable {

	private final EjercicioContable currentItem = new EjercicioContable();

	@FXML
	private TextField numero;

	@FXML
	private DatePicker apertura;

	@FXML
	private DatePicker cierre;

	@FXML
	private CheckBox cerrado;

	@FXML
	private CheckBox cerradoModulos;

	@FXML
	private TextArea comentario;

	@FXML
	void OnActionAceptar(ActionEvent event) {

	}

	@FXML
	void OnActionActivarEjercicio(ActionEvent event) {

	}

	@FXML
	void OnActionCancelar(ActionEvent event) {

	}

	public void initialize(URL url, ResourceBundle rb) {
		
//		numero.textProperty().bindBidirectional(currentItem.numeroProperty());
		
		numero.textProperty().bindBidirectional(currentItem.numeroProperty(), new NumberStringConverter());
		
//		Bindings.bindBidirectional(numero.textProperty(), currentItem.numeroProperty(), new IntegerStringConverter());
		
		apertura.valueProperty().bindBidirectional(currentItem.aperturaProperty());
		cierre.valueProperty().bindBidirectional(currentItem.cierreProperty());
//		cerrado..bindBidirectional(currentItem.cerradoProperty());
		comentario.textProperty().bindBidirectional(currentItem.comentarioProperty());
		

	}

	private void initializeItem(String id) {
		EjercicioContableServices services = new EjercicioContableServices();
		EjercicioContable item = services.findById(id);
		if (item != null) {
			currentItem.setByOther(item);
		}
	}

	public static FXMLLoader load() {
		return new FXMLLoader(EjercicioContableCrl.class.getResource("ejercicio_contable_form.fxml"));
	}

	public static void openWindow(String id) throws IOException {

		FXMLLoader loader = EjercicioContableCrl.load();

		GridPane grid = loader.load();
		
		EjercicioContableCrl ctrl = loader.getController();
		ctrl.initializeItem(id);

		Scene scene = new Scene(grid, 600, 400);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Do-It!!!");
		stage.setAlwaysOnTop(false);
		stage.setResizable(false);

		stage.show();

	}

}
