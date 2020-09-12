package com.ms.front.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ms.front.Session;
import com.ms.front.commons.views.Table;
import com.ms.front.model.Pagin;
import com.ms.front.model.TableItem7;
import com.ms.front.services.EjercicioContableFindAllPagin;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EjercicioContableTable extends Table<TableItem7> {

	// =============================================================================================

	@FXML
	private TableColumn<TableItem7, String> att2;

	@FXML
	private TableColumn<TableItem7, String> att3;

	@FXML
	private TableColumn<TableItem7, String> att4;

	@FXML
	private TableColumn<TableItem7, String> att5;

	@FXML
	private TableColumn<TableItem7, String> att6;

	// ================================================================================================

	protected List<TableItem7> toList(Object[][] t) {

		List<TableItem7> items = new ArrayList<TableItem7>();

		for (int i = 0; i < t.length; i++) {
			items.add(new TableItem7(t[i]));
		}

		return items;
	}

	protected Pagin findPage() throws Exception {

		EjercicioContableFindAllPagin service = new EjercicioContableFindAllPagin();

		if (this.pageRequest.equals(PAGE_REQUEST_FIRST)) {
			return service.findFirst(Session.DB);
		} else if (this.pageRequest.equals(PAGE_REQUEST_NEXT)) {
			return service.findNext(Session.DB, this.lastIndexOld);
		} else if (this.pageRequest.equals(PAGE_REQUEST_BACK)) {
			return service.findBack(Session.DB, this.lastIndexOld);
		} else if (this.pageRequest.equals(PAGE_REQUEST_LAST)) {
			return service.findLast(Session.DB);
		}

		throw new IllegalStateException("pageRequest not found. " + pageRequest);
	}

	// ================================================================================================

	public void initialize(URL url, ResourceBundle rb) {

		super.initialize(url, rb);

		// --------------------------------------------------------------------------

		att2.setCellValueFactory(new PropertyValueFactory<TableItem7, String>("att2"));
		att3.setCellValueFactory(new PropertyValueFactory<TableItem7, String>("att3"));
		att4.setCellValueFactory(new PropertyValueFactory<TableItem7, String>("att4"));
		att5.setCellValueFactory(new PropertyValueFactory<TableItem7, String>("att5"));
		att6.setCellValueFactory(new PropertyValueFactory<TableItem7, String>("att6"));

		// --------------------------------------------------------------------------

		table.setRowFactory(tv -> new TableRow<TableItem7>() {

			protected void updateItem(TableItem7 item, boolean empty) {
				super.updateItem(item, empty);

				// https://stackoverflow.com/questions/30889732/javafx-tableview-change-row-color-based-on-column-value
				// https://material-ui.com/customization/color/
				if (item != null && item.getAtt7() != null && item.getAtt7().equals("Si")) {

					if (this.isSelected()) {
						setStyle("-fx-background-color: #009688; "); // teal 500
					} else {
						setStyle("-fx-background-color: #80cbc4; "); // teal 200
					}

//					setBackground(
//							new Background(new BackgroundFill(Color.AQUA, new CornerRadii(5.0), Insets.EMPTY)));
				} else {
					setStyle("");
				}

			}
		});

//		table.setRowFactory(new Callback<ListView<String>, ListCell<String>>() {
//			@Override
//			public ListCell<String> call(ListView<String> param) {
//
//				ListCell<String> cell = new ListCell<String>() {
//
//					@Override
//					protected void updateItem(EjercicioContableTableItem item, boolean empty) {
//
//						super.updateItem(item, empty);
//
//						setText(item);
//						
//						if (item != null && item.getPrincipal() != null && item.getPrincipal().equals("Si")) {
//							setBackground(
//									new Background(new BackgroundFill(Color.RED, new CornerRadii(5.0), Insets.EMPTY)));
//						} else {
//							setStyle("");
//						}
//
//
//						if ("high".equalsIgnoreCase(item)) {
//							setBackground(
//									new Background(new BackgroundFill(Color.RED, new CornerRadii(5.0), Insets.EMPTY)));
//						} else if ("medium".equalsIgnoreCase(item)) {
//							setBackground(new Background(
//									new BackgroundFill(Color.YELLOW, new CornerRadii(5.0), Insets.EMPTY)));
//						} else if ("low".equalsIgnoreCase(item)) {
//							setBackground(new Background(
//									new BackgroundFill(Color.GREEN, new CornerRadii(5.0), Insets.EMPTY)));
//						}
//					}
//
//				};
//
//				return cell;
//
//			}
//		});

	}

	// **********************************************************************************************************
	// SHOW
	// **********************************************************************************************************

	public static TableItem7 showAndWait(Stage stage, Node owner) throws IOException {

		// ---------------------------------------------------------------------------

		EjercicioContableTable viewController = (EjercicioContableTable) execShow(EjercicioContableTable.class,
				"Ejercicios contables", stage, owner, MODE_SELECCIONAR);

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

	public static void show(Stage stage, Node owner) throws IOException {

		EjercicioContableTable viewController = (EjercicioContableTable) execShow(EjercicioContableTable.class,
				"Ejercicios contables", stage, owner, MODE_NORMAL);

		// ---------------------------------------------------------------------------

		viewController.onBuscarStart();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.show();

		// ---------------------------------------------------------------------------
	}

} // END CLASS *****************************************************
