package com.ms.front.view.centro_costo_contable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ms.front.model.Pagin;
import com.ms.front.model.PaginArgs;
import com.ms.front.services.Service;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CentroCostoContableTable implements Initializable {

	private static boolean MODE_SELECCIONAR = true;
	private static boolean MODE_NORMAL = false;

	private static final String TITLE = "Centros de costos";

	private Stage stage;
	private SimpleBooleanProperty modoSeleccionarProperty = new SimpleBooleanProperty();
	private PaginArgs paginArgs = new PaginArgs();
	private CentroCostoContablePaginArgs args;
	private CentroCostoContablePaginArgs argsOld;
	private Pagin pagin;

	// =============================================================================================

	@FXML
	private AnchorPane view;

	@FXML
	private Button agregar;

	@FXML
	private Button cambiar;

	@FXML
	private Button eliminar;

	@FXML
	private Button copiar;

	@FXML
	private Button seleccionar;

	@FXML
	private ToggleGroup porToogleGroup;

	@FXML
	private ToggleButton porCentroDeCosto;

	@FXML
	private ToggleButton porNombre;

	@FXML
	private TableView<CentroCostoContableTableItem> table;

	@FXML
	private TableColumn<CentroCostoContableTableItem, String> numero;

	@FXML
	private TableColumn<CentroCostoContableTableItem, String> abreviatura;

	@FXML
	private TableColumn<CentroCostoContableTableItem, String> nombre;

	@FXML
	private Label status;

	@FXML
	private Button buscar;

	@FXML
	private Label totalItems;

	@FXML
	private Label totalPages;

	@FXML
	private HBox pagination;

	// =============================================================================================

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

	@FXML
	private void onKeyPressedTable(KeyEvent event) {

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
	private void onKeyTypedTable(KeyEvent event) {

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

	@FXML
	private void onKeyTypedFiltro(KeyEvent event) {

		int key = (int) event.getCharacter().charAt(0);
//		if (event.isControlDown() && key == 13) {
		if (event.isControlDown() && key == 10) {
			onBuscarStart();
		}

	}

	// =============================================================================================

	@FXML
	private void onAgregar(ActionEvent event) {
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
	private void onCambiar(ActionEvent event) {
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
	private void onCopiar(ActionEvent event) {
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
	private void onEliminar(ActionEvent event) {
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
	private void onSeleccionar(ActionEvent event) {
		stage.close();
	}

	@FXML
	private void onBuscar(ActionEvent event) {
		onBuscarStart();
	}

	@FXML
	void onActionBack(ActionEvent event) {
		onBuscarBack();
	}

	@FXML
	void onActionFirst(ActionEvent event) {
		onBuscarFirst();
	}

	@FXML
	void onActionLast(ActionEvent event) {
		onBuscarLast();
	}

	@FXML
	void onActionNext(ActionEvent event) {
		onBuscarNext();
	}

	@FXML
	private void onPorCentroDeCosto(ActionEvent event) {
		args.setPorCentroDeCosto();
		onBuscarStart();
	}

	@FXML
	private void onPorNombre(ActionEvent event) {
		args.setPorNombre();
		onBuscarStart();
	}

	// ================================================================================================

	private void onBuscarStart() {

		if (argsOld != null && argsOld.equals(args)) {
			return;
		}

		onBuscarFirst();

		argsOld = args.clone();
	}

	private void onBuscarNext() {
		paginArgs.setPageRequestNext();
		onBuscar("Buscando siguiente página...");
	}

	private void onBuscarBack() {
		paginArgs.setPageRequestBack();
		onBuscar("Buscando página anterior...");
	}

	private void onBuscarFirst() {
		paginArgs.setPageRequestFirst();
		paginArgs.setLastIndexOld(0);
		onBuscar("Buscando la primer página...");
//		argsOld = args.clone();
	}

	private void onBuscarLast() {
		paginArgs.setPageRequestLast();
		paginArgs.setLastIndexOld(0);
		onBuscar("Buscando última página...");
	}

	private void onBuscar(String msg) {

		status.setText(msg);

		String lastId = null;
		if (table.getSelectionModel().getSelectedIndex() > -1) {
			lastId = table.getSelectionModel().getSelectedItem().getId();
		}

		List<CentroCostoContableTableItem> items = findAll();

		table.getItems().clear();
		table.getItems().addAll(items);
		if (table.getItems().size() > 0) {
//			table.getSelectionModel().select(0);
			table.getSelectionModel().selectFirst();
			for (int i = 0; i < table.getItems().size(); i++) {
				if (table.getItems().get(i).getId().equals(lastId)) {
					table.getSelectionModel().select(i);
					break;
				}
			}
		}
		table.requestFocus();

		status.setText("");
	}

	// ==========================================================================

	private List<CentroCostoContableTableItem> findAll() {

		totalItems.setText("");
		totalPages.setText("");

		List<CentroCostoContableTableItem> items = new ArrayList<CentroCostoContableTableItem>();

		try {

			String urlString = "CentroCostoContable/findAll";

			pagin = Service.GETPagin(urlString, paginArgs, args);
			paginArgs.setLastIndexOld(pagin.getLastIndex());

			if (pagin.getThisPageItems() == null || pagin.getThisPageItems().length == 0) {
				return items;
			}

			totalItems.setText(pagin.getCantRows().toString() + " ítems");
			totalPages.setText("Página " + (pagin.getThisPage().getPageNumber() + 1) + " de " + (pagin.getCantPages()));

			Object[][] t = pagin.getThisPageItems();

			for (int i = 0; i < t.length; i++) {

				CentroCostoContableTableItem item = new CentroCostoContableTableItem();

				int j = 0;
				if (t[i][j] != null) {
					item.setId(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setNumero(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setAbreviatura(t[i][j].toString());
				}
				
				j++;
				if (t[i][j] != null) {
					item.setNombre(t[i][j].toString());
				}

				items.add(item);
			}

			return items;

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
		return items;

	}

	// ================================================================================================

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

		// --------------------------------------------------------------------------

		numero.setCellValueFactory(new PropertyValueFactory<CentroCostoContableTableItem, String>("numero"));
		abreviatura.setCellValueFactory(new PropertyValueFactory<CentroCostoContableTableItem, String>("abreviatura"));
		nombre.setCellValueFactory(new PropertyValueFactory<CentroCostoContableTableItem, String>("nombre"));

		// --------------------------------------------------------------------------

	}

	// ================================================================================================

	private static CentroCostoContableTable show(Stage stage, Node owner, boolean modoSeleccionar,
			CentroCostoContablePaginArgs filter) throws IOException {

		CentroCostoContableTable viewController = loadView(modoSeleccionar, filter);
		viewController.stage = stage;

		Scene scene = new Scene(viewController.view);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					stage.close();
				}
			}
		});

		stage.setScene(scene);
		stage.setTitle(TITLE);
		stage.setAlwaysOnTop(false);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.initOwner(owner.getScene().getWindow());

		stage.initStyle(StageStyle.UTILITY);
//		stage.initStyle(StageStyle.DECORATED);
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.initStyle(StageStyle.TRANSPARENT);

		viewController.table.requestFocus();

		if (modoSeleccionar) {
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} else {
			stage.show();
		}

		return viewController;

	}

	public static CentroCostoContableTableItem showAndWait(Stage stage, Node owner, CentroCostoContablePaginArgs filter)
			throws IOException {

		CentroCostoContableTable viewController = show(stage, owner, MODE_SELECCIONAR, filter);

		if (viewController.table.getSelectionModel().getSelectedIndex() > -1) {
			return viewController.table.getSelectionModel().getSelectedItem();
		}

		return null;
	}

	public static void show(Stage stage, Node owner, CentroCostoContablePaginArgs filter) throws IOException {
		show(stage, owner, MODE_NORMAL, filter);
	}

	private static CentroCostoContableTable loadView(boolean modoSeleccionar, CentroCostoContablePaginArgs filter)
			throws IOException {

		if (filter.getEjercicioContable() == null) {
			throw new IllegalArgumentException("filter.getEjercicioContable() is null");
		}

		FXMLLoader loader = new FXMLLoader(CentroCostoContableTable.class.getResource("CentroCostoContableTable.fxml"));

		loader.load();

		CentroCostoContableTable viewController = loader.getController();
		viewController.modoSeleccionarProperty.set(modoSeleccionar);
		viewController.args = filter;

		viewController.onPorCentroDeCosto(null);

		return viewController;
	}

}
