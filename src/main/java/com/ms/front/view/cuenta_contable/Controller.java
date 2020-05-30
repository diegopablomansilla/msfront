package com.ms.front.view.cuenta_contable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ms.front.model.Pagin;
import com.ms.front.services.PaginArgs;
import com.ms.front.services.Service;
import com.ms.front.services.ServiceArgs;
import com.ms.front.view.JavaFXUtil;

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
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class Controller implements Initializable {

	// =============================================================================================

	protected static boolean MODE_SELECCIONAR = true;
	protected static boolean MODE_NORMAL = false;

	protected Stage stage;
	protected PaginArgs paginArgs = new PaginArgs();
	protected Pagin pagin;
	protected SimpleBooleanProperty modoSeleccionarProperty = new SimpleBooleanProperty();

	// =============================================================================================

	@FXML
	protected AnchorPane view;

	@FXML
	protected Button agregar;

	@FXML
	protected Button cambiar;

	@FXML
	protected Button eliminar;

	@FXML
	protected Button copiar;

	@FXML
	protected Button seleccionar;
	
	@FXML
	protected TableView<CuentaContableTableItem> table;

	@FXML
	protected Label status;

	@FXML
	protected Button buscar;

	@FXML
	protected Label totalItems;

	@FXML
	protected Label totalPages;

	@FXML
	protected HBox pagination;

	// =============================================================================================

	abstract protected boolean isArgsEqualsArgsOld();

	abstract protected void toAssignArgsToArgsOld();

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
			window.setTitle(table.getSelectionModel().getSelectedItem().getCodigo());
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
			window.setTitle(table.getSelectionModel().getSelectedItem().getCodigo());
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

	protected void onBuscarStart() {

		if (isArgsEqualsArgsOld()) {
			return;
		}

		onBuscarFirst();

		toAssignArgsToArgsOld();
	}

	protected void onBuscarNext() {
		paginArgs.setPageRequestNext();
		onBuscar("Buscando siguiente página...");
	}

	protected void onBuscarBack() {
		paginArgs.setPageRequestBack();
		onBuscar("Buscando página anterior...");
	}

	protected void onBuscarFirst() {
		paginArgs.setPageRequestFirst();
		paginArgs.setLastIndexOld(0);
		onBuscar("Buscando la primer página...");
//		argsOld = args.clone();
	}

	protected void onBuscarLast() {
		paginArgs.setPageRequestLast();
		paginArgs.setLastIndexOld(0);
		onBuscar("Buscando última página...");
	}

	abstract protected String getSelectedId();

	abstract protected boolean selectId(String id);

	@SuppressWarnings("unchecked")
	protected void onBuscar(String msg) {

		status.setText(msg);

		String lastId = getSelectedId();

		table.getItems().clear();
		table.getItems().addAll(findAll());
		if (table.getItems().size() > 0) {
//			table.getSelectionModel().select(0);
			table.getSelectionModel().selectFirst();
			selectId(lastId);
		}
		table.requestFocus();

		status.setText("");
	}

	abstract protected void findAllBefore();

	abstract protected String getFindAllUrl();

	abstract protected ServiceArgs getArgs();

	@SuppressWarnings("rawtypes")
	protected List findAll() {

		findAllBefore();

		totalItems.setText("");
		totalPages.setText("");

		List items = new ArrayList();

		try {

			String urlString = getFindAllUrl();

			pagin = Service.GETPagin(urlString, paginArgs, getArgs());
			paginArgs.setLastIndexOld(pagin.getLastIndex());

			if (pagin.getThisPageItems() == null || pagin.getThisPageItems().length == 0) {
				return items;
			}

			totalItems.setText(pagin.getCantRows().toString() + " ítems");
			totalPages.setText("Página " + (pagin.getThisPage().getPageNumber() + 1) + " de " + (pagin.getCantPages()));

			Object[][] t = pagin.getThisPageItems();

			mapper(items, t);

			return items;

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
		return items;

	}

	@SuppressWarnings("rawtypes")
	abstract protected void mapper(List items, Object[][] t);

	public void initialize(URL url, ResourceBundle rb) {
		agregar.setTooltip(new Tooltip("Agregar (ALT+A)"));
		cambiar.setTooltip(new Tooltip("Cambiar (ALT+C)"));
		eliminar.setTooltip(new Tooltip("Eliminar (ALT+E)"));
		copiar.setTooltip(new Tooltip("Copiar (ALT+I)"));
		seleccionar.setTooltip(new Tooltip("Seleccionar (ALT+S)"));
		buscar.setTooltip(new Tooltip("Buscar (ALT+B)"));
		table.setTooltip(new Tooltip("Buscar (CTRL+ENTER)"));

		// --------------------------------------------------------------------------

		seleccionar.visibleProperty().bindBidirectional(modoSeleccionarProperty);

		// --------------------------------------------------------------------------

		table.setItems(FXCollections.observableArrayList());

		// --------------------------------------------------------------------------

		cambiar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		eliminar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		copiar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		seleccionar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		pagination.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));

	}

	// |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// STATIC ZONE
	// |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

	@SuppressWarnings("rawtypes")
	protected static Controller load(Class c, String title, Stage stage, Node owner, boolean modoSeleccionar)
			throws IOException {

		FXMLLoader loader = new FXMLLoader(c.getResource(c.getSimpleName() + ".fxml"));
		loader.load();
		Controller controller = loader.getController();

		controller.modoSeleccionarProperty.set(modoSeleccionar);
		controller.stage = stage;

		Scene scene = new Scene(controller.view);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					stage.close();
				}
			}
		});

		stage.setScene(scene);
		stage.setTitle(title);
		stage.setAlwaysOnTop(false);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.initOwner(owner.getScene().getWindow());

//		stage.initStyle(StageStyle.UTILITY);
//		stage.initStyle(StageStyle.DECORATED);
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.initStyle(StageStyle.TRANSPARENT);

		controller.table.requestFocus();

		return controller;

	}

}
