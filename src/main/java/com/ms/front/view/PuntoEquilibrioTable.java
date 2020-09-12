package com.ms.front.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ms.front.Session;
import com.ms.front.commons.views.Table;
import com.ms.front.model.Pagin;
import com.ms.front.services.PuntoEquilibrioFindAllPagin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import x.com.ms.front.model.TableItem1;
import x.com.ms.front.model.TableItem4;

public class PuntoEquilibrioTable extends Table<TableItem4> {

	private String ejercicioContableId;

	// =============================================================================================

	@FXML
	private Button copiar;

	@FXML
	private TableColumn<TableItem4, String> att2;

	@FXML
	private TableColumn<TableItem4, String> att3;

	@FXML
	private TableColumn<TableItem4, String> att4;

	// =============================================================================================

	// =============================================================================================

//	@FXML
//	private void onCopiar(ActionEvent event) {
//		if (table.getSelectionModel().getSelectedIndex() > -1) {
//			Stage window = new Stage();
//			window.initModality(Modality.APPLICATION_MODAL);
//			window.setTitle(table.getSelectionModel().getSelectedItem().toString());
//			window.setWidth(300);
//			window.setHeight(200);
//			window.setMaxWidth(300);
//			window.setMaxHeight(200);
//			window.show();
//		}
//	}

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

		PuntoEquilibrioFindAllPagin service = new PuntoEquilibrioFindAllPagin();

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

	public void initialize(URL url, ResourceBundle rb) {

		super.initialize(url, rb);

		// --------------------------------------------------------------------------

		att2.setCellValueFactory(new PropertyValueFactory<TableItem4, String>("att2"));
		att3.setCellValueFactory(new PropertyValueFactory<TableItem4, String>("att3"));
		att4.setCellValueFactory(new PropertyValueFactory<TableItem4, String>("att4"));

		// --------------------------------------------------------------------------

	}

	// **********************************************************************************************************
	// SHOW
	// **********************************************************************************************************

	public static TableItem1 showAndWait(Node owner, String ejercicioContableId) throws IOException {

		if (ejercicioContableId == null) {
			throw new IllegalArgumentException("ejercicioContableId is null");
		}

		ejercicioContableId = ejercicioContableId.trim();

		if (ejercicioContableId.length() == 0) {
			throw new IllegalArgumentException("ejercicioContableId is empty");
		}

//		return Table.showAndWait(PuntoEquilibrioTable.class, "Puntos de equilibrios", stage, owner);

		// ---------------------------------------------------------------------------

		Stage stage = new Stage();

		PuntoEquilibrioTable viewController = (PuntoEquilibrioTable) execShow(PuntoEquilibrioTable.class,
				"Puntos de equilibrios", stage, owner, MODE_SELECCIONAR);

		// ---------------------------------------------------------------------------

		viewController.onBuscarStart();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		// ---------------------------------------------------------------------------

		if (viewController.table.getSelectionModel().getSelectedIndex() > -1) {
			return (TableItem1) viewController.table.getSelectionModel().getSelectedItem();
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

		PuntoEquilibrioTable viewController = (PuntoEquilibrioTable) execShow(PuntoEquilibrioTable.class,
				"Puntos de equilibrios", stage, owner, MODE_NORMAL);

		// ---------------------------------------------------------------------------

//		viewController.args = filter;
		viewController.ejercicioContableId = ejercicioContableId;

		viewController.onBuscarStart();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.show();

		// ---------------------------------------------------------------------------
	}

} // END CLASS *****************************************************
