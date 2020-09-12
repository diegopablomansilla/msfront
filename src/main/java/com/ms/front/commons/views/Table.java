package com.ms.front.commons.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ms.front.model.Pagin;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import x.com.ms.front.model.ServiceArgs;
import x.com.ms.front.model.TableItem1;

public abstract class Table<T> implements Initializable {

	protected static boolean MODE_SELECCIONAR = true;
	protected static boolean MODE_NORMAL = false;

	protected Stage stage;
	protected SimpleBooleanProperty modoSeleccionarProperty = new SimpleBooleanProperty();

	protected final String PAGE_REQUEST_NEXT = "NEXT";
	protected final String PAGE_REQUEST_BACK = "BACK";
	protected final String PAGE_REQUEST_FIRST = "FIRST";
	protected final String PAGE_REQUEST_LAST = "LAST";
	protected String pageRequest = PAGE_REQUEST_FIRST;
	protected Integer lastIndexOld = 0;

	protected ServiceArgs args;
	protected ServiceArgs argsOld;

	// =============================================================================================

	@FXML
	protected AnchorPane view;

	@FXML
	protected Button agregar;

	@FXML
	protected Button cambiar;

	@FXML
	protected Button eliminar;

//	@FXML
//	protected Button copiar;

	@FXML
	protected Button seleccionar;

//	@FXML
//	private Label status;

	@FXML
	protected ProgressIndicator progress;

//	@FXML
//	private Button buscar;

	@FXML
	protected Label totalItems;

	@FXML
	protected Label totalPages;

	@FXML
	protected HBox pagination;

	@FXML
	protected TableView<T> table;

	// =============================================================================================

	@FXML
	protected void onKeyPressedTable(KeyEvent event) {

		int index = table.getSelectionModel().getSelectedIndex();
		int size = table.getItems().size();

		if (event.getCode().equals(KeyCode.DOWN) && index > -1 && size > 0 && index == (size - 1)) {
			onBuscarNext();
		} else if (event.getCode().equals(KeyCode.UP) && index == 0) {
			onBuscarBack();
		} else if (event.getCode().equals(KeyCode.PAGE_DOWN)) {
			onBuscarNext();
		} else if (event.getCode().equals(KeyCode.PAGE_UP)) {
			onBuscarBack();
		} else if (event.getCode().equals(KeyCode.HOME)) {
			onBuscarFirst();
		} else if (event.getCode().equals(KeyCode.END)) {
			onBuscarLast();
		} else if (event.getCode().equals(KeyCode.DELETE)) {
			onEliminar(null);
		} else if (event.getCode().equals(KeyCode.INSERT)) {
			onAgregar(null);
		}

	}

	@FXML
	protected void onKeyTypedTable(KeyEvent event) {

		int key = (int) event.getCharacter().charAt(0);

		if (event.isControlDown() && key == 10) {
			onBuscarStart();
		} else if (key == 13) {
			if (modoSeleccionarProperty.get()) {
				onSeleccionar(null);
			} else if (cambiar.isDisable() == false) {
				onCambiar(null);
			}
		}

	}

	// =============================================================================================

	@FXML
	protected void onAgregar(ActionEvent event) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Cuenta contable");
		window.setWidth(300);
		window.setHeight(200);
		window.setMaxWidth(300);
		window.setMaxHeight(200);
		window.show();
	}

	@FXML
	protected void onCambiar(ActionEvent event) {
		if (table.getSelectionModel().getSelectedIndex() > -1) {
			Stage window = new Stage();
			window.initModality(Modality.APPLICATION_MODAL);
			window.setTitle(table.getSelectionModel().getSelectedItem().toString());
			window.setWidth(300);
			window.setHeight(200);
			window.setMaxWidth(300);
			window.setMaxHeight(200);
			window.show();
		}
	}

	@FXML
	protected void onCopiar(ActionEvent event) {
		if (table.getSelectionModel().getSelectedIndex() > -1) {
			Stage window = new Stage();
			window.initModality(Modality.APPLICATION_MODAL);
			window.setTitle(table.getSelectionModel().getSelectedItem().toString());
			window.setWidth(300);
			window.setHeight(200);
			window.setMaxWidth(300);
			window.setMaxHeight(200);
			window.show();
		}
	}

	@FXML
	protected void onEliminar(ActionEvent event) {
		if (table.getSelectionModel().getSelectedIndex() > -1) {

			Alert alertDeleteItem = JavaFXUtil
					.buildAlertConfirmDeleteItem(table.getSelectionModel().getSelectedItem().toString());

			Optional<ButtonType> result = alertDeleteItem.showAndWait();
			table.requestFocus();
			if ((result.isPresent())) {
				if ((result.get() == ButtonType.OK)) {
					System.out.println("ELIMINAR " + table.getSelectionModel().getSelectedItem());
				}
			}

		}
	}

	@FXML
	protected void onSeleccionar(ActionEvent event) {
		stage.close();
	}

	@FXML
	protected void onBuscar(ActionEvent event) {
		onBuscarStart();
	}

	@FXML
	protected void onActionBack(ActionEvent event) {
		onBuscarBack();
	}

	@FXML
	protected void onActionFirst(ActionEvent event) {
		onBuscarFirst();
	}

	@FXML
	protected void onActionLast(ActionEvent event) {
		onBuscarLast();
	}

	@FXML
	protected void onActionNext(ActionEvent event) {
		onBuscarNext();
	}

	protected void onBuscarStart() {

		if (args != null && argsOld != null && argsOld.equals(args)) {
			return;
		}

		onBuscarFirst();

		if (args != null) {
			argsOld = args.clone();
		}
	}

	protected void onBuscarNext() {
		pageRequest = this.PAGE_REQUEST_NEXT;
		onBuscar("Buscando siguiente página...");
	}

	protected void onBuscarBack() {
		pageRequest = this.PAGE_REQUEST_BACK;
		onBuscar("Buscando página anterior...");
	}

	protected void onBuscarFirst() {
		pageRequest = this.PAGE_REQUEST_FIRST;
		onBuscar("Buscando la primer página...");
	}

	protected void onBuscarLast() {
		pageRequest = this.PAGE_REQUEST_LAST;
		onBuscar("Buscando última página...");
	}

	protected void onBuscar(String msg) {

		progress.setVisible(true);

		String lastId = getSelectedItemId();

		List<T> items = findAll();

		table.getItems().clear();
		table.getItems().addAll(items);
		if (table.getItems().size() > 0) {
			table.getSelectionModel().selectFirst();
			selectedItemById(lastId);
		}
		table.requestFocus();

		progress.setVisible(false);

	}

	protected String getSelectedItemId() {
		if (table.getSelectionModel().getSelectedIndex() > -1) {
			return ((TableItem1) table.getSelectionModel().getSelectedItem()).getAtt1();
		}
		return null;
	}

	protected boolean selectedItemById(String id) {

		if (id == null) {
			return false;
		}

		for (int i = 0; i < table.getItems().size(); i++) {
			if (((TableItem1) table.getItems().get(i)).getAtt1().equals(id)) {
				table.getSelectionModel().select(i);
				return true;
			}
		}

		return false;
	}

	protected List<T> findAll() {

		List<T> items = new ArrayList<T>();

		Object[][] t = findDataTable();

		if (t == null) {
			return items;
		}

		return toList(t);
	}

	abstract protected List<T> toList(Object[][] t);

	protected Object[][] findDataTable() {

		totalItems.setText("");
		totalPages.setText("");

		try {

			Pagin pagin = findPage();

			lastIndexOld = pagin.getLastIndex();

			if (pagin.getThisPageItems() == null || pagin.getThisPageItems().length == 0) {
				return null;
			}

			totalItems.setText(pagin.getCantRows().toString() + " ítems");
			totalPages.setText("Página " + (pagin.getThisPage().getPageNumber() + 1) + " de " + (pagin.getCantPages()));

			return pagin.getThisPageItems();

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
		return null;

	}

	abstract protected Pagin findPage() throws Exception;

	// ===============================================================================================

	public void initialize(URL url, ResourceBundle rb) {
//		agregar.setTooltip(new Tooltip("Agregar (ALT+A)"));
//		cambiar.setTooltip(new Tooltip("Cambiar (ALT+C)"));
//		eliminar.setTooltip(new Tooltip("Eliminar (ALT+E)"));
//		copiar.setTooltip(new Tooltip("Copiar (ALT+I)"));
//		seleccionar.setTooltip(new Tooltip("Seleccionar (ALT+S)"));
//		buscar.setTooltip(new Tooltip("Buscar (ALT+B)"));
//		table.setTooltip(new Tooltip("Buscar (CTRL+ENTER)"));

		// --------------------------------------------------------------------------

		seleccionar.visibleProperty().bindBidirectional(modoSeleccionarProperty);

		// --------------------------------------------------------------------------

		table.setItems(FXCollections.observableArrayList());

		// --------------------------------------------------------------------------

		cambiar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		eliminar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
//		copiar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		seleccionar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		pagination.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
	}

	// ===============================================================================================

	@SuppressWarnings("rawtypes")
	protected static Table execShow(Class c, String title, Stage stage, Node owner, boolean mode) throws IOException {
		// ---------------------------------------------------------------------------

		FXMLLoader loader = new FXMLLoader(c.getResource(c.getSimpleName() + ".fxml"));
		loader.load();

		// ---------------------------------------------------------------------------

		Table viewController = loader.getController();
		viewController.modoSeleccionarProperty.set(mode);
		viewController.setStage(stage);

		// ---------------------------------------------------------------------------

		Scene scene = new Scene(viewController.view);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					stage.close();
				}
			}
		});

		// ---------------------------------------------------------------------------

		stage.setScene(scene);
		stage.setTitle(title);
		stage.setAlwaysOnTop(false);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.initOwner(owner.getScene().getWindow());
		stage.initStyle(StageStyle.UTILITY);
//		stage.initStyle(StageStyle.DECORATED);
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.initStyle(StageStyle.TRANSPARENT);

		// ---------------------------------------------------------------------------

		return viewController;
	}

	// ===============================================================================================

	private void setStage(Stage stage) {
		this.stage = stage;
	}

} // END CLASS *****************************************************
