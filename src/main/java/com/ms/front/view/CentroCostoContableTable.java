package com.ms.front.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ms.front.Session;
import com.ms.front.commons.views.Table;
import com.ms.front.model.Pagin;
import com.ms.front.model.TableItem4;
import com.ms.front.services.CentroCostoContableFindAllPagin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CentroCostoContableTable extends Table<TableItem4> {

	public static final String POR_CENTRO_DE_COSTO = "CENTRO_DE_COSTO";
	public static final String POR_NOMBRE = "NOMBRE";

	private String ejercicioContableId;
	private String por;

	// =============================================================================================

	@FXML
	private ToggleGroup porToogleGroup;

	@FXML
	private ToggleButton porCentroDeCosto;

	@FXML
	private ToggleButton porNombre;

	// =============================================================================================

	@FXML
	private TableColumn<TableItem4, String> att2;

	@FXML
	private TableColumn<TableItem4, String> att3;

	@FXML
	private TableColumn<TableItem4, String> att4;

	// ================================================================================================

	@Override
	protected List<TableItem4> toList(Object[][] t) {

		List<TableItem4> items = new ArrayList<TableItem4>();

		for (int i = 0; i < t.length; i++) {
			items.add(new TableItem4(t[i]));
		}

		return items;
	}

	protected Pagin findPage() throws Exception {

		CentroCostoContableFindAllPagin service = new CentroCostoContableFindAllPagin();

		if (this.pageRequest.equals(PAGE_REQUEST_FIRST)) {
			return service.findFirst(Session.DB, ejercicioContableId, por);
		} else if (this.pageRequest.equals(PAGE_REQUEST_NEXT)) {
			return service.findNext(Session.DB, this.lastIndexOld, ejercicioContableId, por);
		} else if (this.pageRequest.equals(PAGE_REQUEST_BACK)) {
			return service.findBack(Session.DB, this.lastIndexOld, ejercicioContableId, por);
		} else if (this.pageRequest.equals(PAGE_REQUEST_LAST)) {
			return service.findLast(Session.DB, ejercicioContableId, por);
		}

		throw new IllegalStateException("pageRequest not found. " + pageRequest);
	}

	public void initialize(URL url, ResourceBundle rb) {

		super.initialize(url, rb);

		// --------------------------------------------------------------------------

		att2.setCellValueFactory(new PropertyValueFactory<TableItem4, String>("att2"));
		att3.setCellValueFactory(new PropertyValueFactory<TableItem4, String>("att3"));
		att4.setCellValueFactory(new PropertyValueFactory<TableItem4, String>("att4"));

		// --------------------------------------------------------------------------

	}

	@FXML
	private void onBuscarStartPor() {

		if (por == null) {
			por = POR_CENTRO_DE_COSTO;
		}

		if (por.equals(POR_CENTRO_DE_COSTO)) {
			porToogleGroup.selectToggle(porCentroDeCosto);
			onPorCentroDeCosto(null);
		} else {
			porToogleGroup.selectToggle(porNombre);
			onPorNombre(null);
		}

	}

	@FXML
	private void onPorCentroDeCosto(ActionEvent event) {

		if (porCentroDeCosto.isSelected()) {
			por = POR_CENTRO_DE_COSTO;
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porNombre);
			onPorNombre(null);
		}

	}

	@FXML
	private void onPorNombre(ActionEvent event) {

		if (porNombre.isSelected()) {
			por = POR_NOMBRE;
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porCentroDeCosto);
			onPorCentroDeCosto(null);
		}

	}

	@FXML
	private void onKeyReleased(KeyEvent event) {

		if (event.getCode().equals(KeyCode.TAB) && event.isControlDown() && event.isShiftDown()) {
			if (porCentroDeCosto.isSelected()) {
				porToogleGroup.selectToggle(porNombre);
				onPorNombre(null);
			} else if (porNombre.isSelected()) {
				porToogleGroup.selectToggle(porCentroDeCosto);
				onPorCentroDeCosto(null);
			}
		} else if (event.getCode().equals(KeyCode.TAB) && event.isControlDown()) {

			if (porCentroDeCosto.isSelected()) {
				porToogleGroup.selectToggle(porNombre);
				onPorNombre(null);
			} else if (porNombre.isSelected()) {
				porToogleGroup.selectToggle(porCentroDeCosto);
				onPorCentroDeCosto(null);
			}
		}
	}

	// **********************************************************************************************************
	// SHOW
	// **********************************************************************************************************

	public static TableItem4 showAndWait(Node owner, String ejercicioContableId, String por) throws IOException {

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is null");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty");
		}

		// ---------------------------------------------------------------------------

		if (por != null) {

			por = por.trim();

			if (por.length() == 0) {
				throw new IllegalArgumentException("por is empty");
			}

		}

		// ---------------------------------------------------------------------------

		Stage stage = new Stage();

		CentroCostoContableTable viewController = (CentroCostoContableTable) execShow(CentroCostoContableTable.class,
				"Centros de costos", stage, owner, MODE_SELECCIONAR);

		// ---------------------------------------------------------------------------

		viewController.ejercicioContableId = ejercicioContableId;
		viewController.por = por;

		viewController.onBuscarStartPor();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		// ---------------------------------------------------------------------------

		if (viewController.table.getSelectionModel().getSelectedIndex() > -1) {
			return viewController.table.getSelectionModel().getSelectedItem();
		}

		return null;

		// ---------------------------------------------------------------------------
	}

	public static void show(Node owner, String ejercicioContableId, String por) throws IOException {

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is null");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty");
		}

		// ---------------------------------------------------------------------------

		if (por != null) {

			por = por.trim();

			if (por.length() == 0) {
				throw new IllegalArgumentException("por is empty");
			}

		}

		// ---------------------------------------------------------------------------

		Stage stage = new Stage();

		CentroCostoContableTable viewController = (CentroCostoContableTable) execShow(CentroCostoContableTable.class,
				"Centros de costos", stage, owner, MODE_NORMAL);

		// ---------------------------------------------------------------------------

		viewController.ejercicioContableId = ejercicioContableId;
		viewController.por = por;

		viewController.onBuscarStartPor();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.show();

		// ---------------------------------------------------------------------------
	}

} // END CLASS *****************************************************
