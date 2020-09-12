package com.ms.front.view.punto_equilibrio;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ms.front.commons.views.JavaFXUtil;
import com.ms.front.view.PuntoEquilibrioTable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import x.com.ms.front.model.Entity;
import x.com.ms.front.model.TableItem4;

public class PuntoEquilibrioBox implements Initializable {

	// =============================================================================================

	private Entity entity = new Entity();

	// =============================================================================================

	@FXML
	private AnchorPane view;

	@FXML
	private TextField descriptionTXT;

	@FXML
	private TextField searchTXT;

	@FXML
	private Button openBtn;

	// =============================================================================================

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	private void onFocusedValue(Boolean oldVal, Boolean newVal) {

		if (oldVal == false && newVal == true) {
			searchTXT.setVisible(true);
			descriptionTXT.setVisible(false);
		}

	}

	private void onFocusedSearch(Boolean oldVal, Boolean newVal) {

		if (oldVal == true && newVal == false) {
			descriptionTXT.setVisible(false);
			searchTXT.setVisible(true);
		}

	}

	@FXML
	private void onKeyTypedFiltro(KeyEvent event) {

		try {

			int key = (int) event.getCharacter().charAt(0);

//			if (event.isControlDown() && key == 13) {
//			if (event.isControlDown() && key == 10) {
			if (key == 13) {

//				PuntoEquilibrioPaginArgs filter = new PuntoEquilibrioPaginArgs();
//				filter.setEjercicioContable("2002");
//				ServiceArgs filter = new ServiceArgs();
//				filter.put("ejercicio", "2002");
				
				TableItem4 item = (TableItem4) PuntoEquilibrioTable.showAndWait(view, "2002");
				if (item != null) {
					descriptionTXT.setText(item.getAtt2() + " - " + item.getAtt3());
					searchTXT.setText("");
					
//					descriptionTXT.setVisible(true);
//					searchTXT.setVisible(false);
					
				} else {
					descriptionTXT.setText("");
					
//					descriptionTXT.setVisible(false);
//					searchTXT.setVisible(true);
				}

			}

		} catch (IOException e) {
			JavaFXUtil.buildAlertException(e);
		}

	}

	// ================================================================================================

	public void initialize(URL url, ResourceBundle rb) {
		descriptionTXT.focusedProperty().addListener((obs, oldVal, newVal) -> onFocusedValue(oldVal, newVal));
		searchTXT.focusedProperty().addListener((obs, oldVal, newVal) -> onFocusedSearch(oldVal, newVal));
	}

	// ================================================================================================

	public static AnchorPane loadView() throws IOException {
		return loadView(null);
	}

	public static AnchorPane loadView(String id) throws IOException {

		FXMLLoader loader = new FXMLLoader(PuntoEquilibrioBox.class.getResource("PuntoEquilibrioBox.fxml"));

		AnchorPane node = loader.load();

		PuntoEquilibrioBox controller = loader.getController();
		controller.getEntity().setId(id);

		return node;
	}

}
