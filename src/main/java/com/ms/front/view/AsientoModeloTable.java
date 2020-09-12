package com.ms.front.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ms.front.Session;
import com.ms.front.commons.views.Table;
import com.ms.front.model.Pagin;
import com.ms.front.model.TableItem3;
import com.ms.front.services.AsientoModeloFindAllPagin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AsientoModeloTable extends Table<TableItem3> {

	private String ejercicioContableId;

	// =============================================================================================

	@FXML
	private TableColumn<TableItem3, String> att2;

	@FXML
	private TableColumn<TableItem3, String> att3;

	// ================================================================================================

	protected List<TableItem3> toList(Object[][] t) {

		List<TableItem3> items = new ArrayList<TableItem3>();

		for (int i = 0; i < t.length; i++) {
			items.add(new TableItem3(t[i]));
		}

		return items;
	}

	protected Pagin findPage() throws Exception {

		AsientoModeloFindAllPagin service = new AsientoModeloFindAllPagin();

		if (this.pageRequest.equals(PAGE_REQUEST_FIRST)) {
			return service.findFirst(Session.DB, ejercicioContableId);
		} else if (this.pageRequest.equals(PAGE_REQUEST_NEXT)) {
			return service.findNext(Session.DB, this.lastIndexOld, ejercicioContableId);
		} else if (this.pageRequest.equals(PAGE_REQUEST_BACK)) {
			return service.findBack(Session.DB, this.lastIndexOld, ejercicioContableId);
		} else if (this.pageRequest.equals(PAGE_REQUEST_LAST)) {
			return service.findLast(Session.DB, ejercicioContableId);
		}

		throw new IllegalStateException("pageRequest not found. " + pageRequest);
	}

	// ================================================================================================

	public void initialize(URL url, ResourceBundle rb) {

		super.initialize(url, rb);

		// --------------------------------------------------------------------------

		att2.setCellValueFactory(new PropertyValueFactory<TableItem3, String>("att2"));
		att3.setCellValueFactory(new PropertyValueFactory<TableItem3, String>("att3"));

	}

	// **********************************************************************************************************
	// SHOW
	// **********************************************************************************************************

	public static TableItem3 showAndWait(Node owner, String ejercicioContableId) throws IOException {

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is null");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty");
		}

		// ---------------------------------------------------------------------------

		Stage stage = new Stage();

		AsientoModeloTable viewController = (AsientoModeloTable) execShow(AsientoModeloTable.class,
				"Modelos de asientos", stage, owner, MODE_SELECCIONAR);

		// ---------------------------------------------------------------------------

		viewController.onBuscarStart();
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

	public static void show(Node owner, String ejercicioContableId) throws IOException {

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is null");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty");
		}

		// ---------------------------------------------------------------------------

		Stage stage = new Stage();

		AsientoModeloTable viewController = (AsientoModeloTable) execShow(AsientoModeloTable.class,
				"Modelos de asientos", stage, owner, MODE_NORMAL);

		// ---------------------------------------------------------------------------

		viewController.ejercicioContableId = ejercicioContableId;

		viewController.onBuscarStart();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.show();

		// ---------------------------------------------------------------------------
	}

} // END CLASS *****************************************************
