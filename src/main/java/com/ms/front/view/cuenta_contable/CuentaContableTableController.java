package com.ms.front.view.cuenta_contable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.json.JsonArray;

import com.ms.EnvVars;
import com.ms.front.services.ResponseJsonObject;
import com.ms.front.services.Service;
import com.ms.front.view.JavaFXUtil;
import com.ms.front.view.TableFilter;

import javafx.beans.InvalidationListener;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CuentaContableTableController implements Initializable {

	private static boolean MODE_SELECCIONAR = true;
	private static boolean MODE_NORMAL = false;

	private static final String TITLE = "Plan de cuentas";

	private Stage stage;
	private SimpleBooleanProperty modoSeleccionarProperty = new SimpleBooleanProperty();
	private TableFilter filterPagin = new TableFilter();
	private CuentaContableTableFilter filter;
	private CuentaContableTableFilter filterOld;

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
	private ToggleButton porCuentaContable;

	@FXML
	private ToggleButton porNombre;

	@FXML
	private ToggleButton porCuentaAgrupadora;

	@FXML
	private ToggleButton porCentroDeCosto;

	@FXML
	private ToggleButton porPuntoDeEquilibrio;

	@FXML
	private TableView<CuentaContableTableItem> table;

	@FXML
	private TableColumn<CuentaContableTableItem, String> codigo;

	@FXML
	private TableColumn<CuentaContableTableItem, String> nombre;

	@FXML
	private TableColumn<CuentaContableTableItem, String> centroCostoContable;

	@FXML
	private TableColumn<CuentaContableTableItem, String> cuentaAgrupadora;

	@FXML
	private TableColumn<CuentaContableTableItem, String> porcentaje;

	@FXML
	private TextField filtro;

	@FXML
	private Label status;

	@FXML
	private Button buscar;

	// =============================================================================================

	@FXML
	private void onKeyReleased(KeyEvent event) {

		if (event.getCode().equals(KeyCode.TAB) && event.isControlDown() && event.isShiftDown()) {
			if (porCuentaContable.isSelected()) {
				porToogleGroup.selectToggle(porPuntoDeEquilibrio);
				onPorPuntoDeEquilibrio(null);
			} else if (porNombre.isSelected()) {
				porToogleGroup.selectToggle(porCuentaContable);
				onPorCuentaContable(null);
			} else if (porCuentaAgrupadora.isSelected()) {
				porToogleGroup.selectToggle(porNombre);
				onPorNombre(null);
			} else if (porCentroDeCosto.isSelected()) {
				porToogleGroup.selectToggle(porCuentaAgrupadora);
				onPorCuentaAgrupadora(null);
			} else if (porPuntoDeEquilibrio.isSelected()) {
				porToogleGroup.selectToggle(porCentroDeCosto);
				onPorCentroDeCosto(null);
			}
		} else if (event.getCode().equals(KeyCode.TAB) && event.isControlDown()) {
			if (porCuentaContable.isSelected()) {
				porToogleGroup.selectToggle(porNombre);
				onPorNombre(null);
			} else if (porNombre.isSelected()) {
				porToogleGroup.selectToggle(porCuentaAgrupadora);
				onPorCuentaAgrupadora(null);
			} else if (porCuentaAgrupadora.isSelected()) {
				porToogleGroup.selectToggle(porCentroDeCosto);
				onPorCentroDeCosto(null);
			} else if (porCentroDeCosto.isSelected()) {
				porToogleGroup.selectToggle(porPuntoDeEquilibrio);
				onPorPuntoDeEquilibrio(null);
			} else if (porPuntoDeEquilibrio.isSelected()) {
				porToogleGroup.selectToggle(porCuentaContable);
				onPorCuentaContable(null);
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
			onBuscarStart();
		} else if (event.getCode().equals(KeyCode.END)) {
			onBuscarEnd();
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
		} else if (key == 8) {
			if (filtro.getText().length() > 0) {
				filtro.setText(filtro.getText().substring(0, filtro.getText().length() - 1));
			} else if (filtro.getText().length() == 0) {
				filtro.setText("");
			}
		} else {
			if (filtro.getText() != null) {
				filtro.setText(filtro.getText() + event.getCharacter());
			} else {
				filtro.setText(event.getCharacter());
			}

		}

	}

	@FXML
	private void onKeyTypedFiltro(KeyEvent event) {

		int key = (int) event.getCharacter().charAt(0);
		if (key == 13) {
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
			window.setTitle(table.getSelectionModel().getSelectedItem().getCodigo());
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
			window.setTitle(table.getSelectionModel().getSelectedItem().getCodigo());
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
	private void onPorCuentaContable(ActionEvent event) {
		filter.setPor("CUENTA_CONTABLE");
		filtro.setPromptText("Buscar por cuenta contable");
		onBuscarStart();
	}

	@FXML
	private void onPorNombre(ActionEvent event) {
		filter.setPor("NOMBRE");
		filtro.setPromptText("Buscar por nombre");
		onBuscarStart();
	}

	@FXML
	private void onPorCuentaAgrupadora(ActionEvent event) {
		filter.setPor("CUENTA_AGRUPADORA");
		filtro.setPromptText("Buscar por cuenta agrupadora");
		onBuscarStart();
	}

	@FXML
	private void onPorCentroDeCosto(ActionEvent event) {
		filter.setPor("CENTRO_DE_COSTO");
		filtro.setPromptText("Buscar por centro de costo");
		onBuscarStart();
	}

	@FXML
	private void onPorPuntoDeEquilibrio(ActionEvent event) {
		filter.setPor("PUNTO_DE_EQUILIBRIO");
		filtro.setPromptText("Buscar por punto de equilibrio");
		onBuscarStart();
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

		table.getItems().addListener((InvalidationListener) invalidationChange -> {
			boolean disable = table.getItems().size() == 0;
			cambiar.setDisable(disable);
			eliminar.setDisable(disable);
			copiar.setDisable(disable);
			seleccionar.setDisable(disable);
		});

		// --------------------------------------------------------------------------

		codigo.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("codigo"));
		nombre.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("nombre"));
		centroCostoContable
				.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("centroCostoContable"));
		cuentaAgrupadora
				.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("cuentaAgrupadora"));
		porcentaje.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("porcentaje"));

		// --------------------------------------------------------------------------

	}

	// ================================================================================================

	private static CuentaContableTableController loadView(boolean modoSeleccionar, CuentaContableTableFilter filter)
			throws IOException {

		if (filter.getEjercicioContable() == null) {
			throw new IllegalArgumentException("filter.getEjercicioContable() is null");
		}

		FXMLLoader loader = new FXMLLoader(CuentaContableTableController.class.getResource("CuentaContableTable.fxml"));

		loader.load();

		CuentaContableTableController viewController = loader.getController();
		viewController.modoSeleccionarProperty.set(modoSeleccionar);
		viewController.filter = filter;
		viewController.filtro.textProperty().bindBidirectional(filter.filtroProperty());
		viewController.onPorCuentaContable(null);

		return viewController;
	}

	private static CuentaContableTableController show(Stage stage, Node owner, boolean modoSeleccionar,
			CuentaContableTableFilter filter) throws IOException {

		CuentaContableTableController viewController = loadView(modoSeleccionar, filter);
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

//		stage.initStyle(StageStyle.UTILITY);
//		stage.initStyle(StageStyle.DECORATED);
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.initStyle(StageStyle.TRANSPARENT);

		viewController.table.requestFocus();

		if (modoSeleccionar) {
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} else {
			stage.show();
//			viewController.confScroll();
		}

		return viewController;

	}

	public static CuentaContableTableItem showAndWait(Stage stage, Node owner, CuentaContableTableFilter filter)
			throws IOException {

		CuentaContableTableController viewController = show(stage, owner, MODE_SELECCIONAR, filter);

		if (viewController.table.getSelectionModel().getSelectedIndex() > -1) {
			return viewController.table.getSelectionModel().getSelectedItem();
		}

		return null;
	}

	public static void show(Stage stage, Node owner, CuentaContableTableFilter filter) throws IOException {
		show(stage, owner, MODE_NORMAL, filter);
	}

	// ================================================================================================

	private void onBuscarStart() {

		if (filterOld != null && filterOld.equals(filter)) {
			return;
		}

		status.setText("Buscando la primer página...");

		table.getItems().clear();
		filterPagin.setOffset(0);
		table.getItems().addAll(findAll(filterPagin, filter));
		if (table.getItems().size() > 0) {
			table.getSelectionModel().select(0);
		}
		table.requestFocus();

		status.setText("");

		filterOld = filter.clone();
	}

	private void onBuscarNext() {

		status.setText("Buscando siguiente página...");

		filterPagin.setOffset(filterPagin.getOffset() + EnvVars.getPaginLimit());
		List<CuentaContableTableItem> items = findAll(filterPagin, filter);
		if (items.size() > 0) {
			table.getItems().clear();
			table.getItems().addAll(items);
			table.getSelectionModel().select(0);
			table.requestFocus();
		} else {
			this.filterPagin.setOffset(this.filterPagin.getOffset() - EnvVars.getPaginLimit());
		}

		status.setText("");
	}

	private void onBuscarBack() {

		status.setText("Buscando página anterior...");

		filterPagin.setOffset(filterPagin.getOffset() - EnvVars.getPaginLimit());
		if (this.filterPagin.getOffset() >= 0) {
			List<CuentaContableTableItem> items = findAll(filterPagin, filter);
			if (items.size() > 0) {
				table.getItems().clear();
				table.getItems().addAll(items);
				table.getSelectionModel().select(0);
				table.requestFocus();
			}
		} else {
			this.filterPagin.setOffset(0);
		}

		status.setText("");
	}

	private void onBuscarEnd() {

		status.setText("Buscando última página...");

		int offset = findLastOffset(filterPagin, filter);
		filterPagin.setOffset(offset);
		List<CuentaContableTableItem> items = findAll(filterPagin, filter);
		if (items.size() > 0) {
			table.getItems().clear();
			table.getItems().addAll(items);
			table.getSelectionModel().select(table.getItems().size() - 1);
			table.requestFocus();
		} else {
			this.filterPagin.setOffset(this.filterPagin.getOffset() - EnvVars.getPaginLimit());
		}

		status.setText("");
	}

	// ==========================================================================

	private int findLastOffset(TableFilter filterPagin, CuentaContableTableFilter filter) {
		int offset = CuentaContableService.items.size() - 1 - EnvVars.getPaginLimit();
		return offset;
	}

	private List<CuentaContableTableItem> findAll(TableFilter filterPagin, CuentaContableTableFilter filter) {

		List<CuentaContableTableItem> items = new ArrayList<CuentaContableTableItem>();

		try {

			String urlString = "CuentaContable/findAll";

			ResponseJsonObject r = Service.GET(Service.TYPE_RPC, urlString, "db1", "offset",
					filterPagin.getOffset().toString(), "ejercicioContable", filter.getEjercicioContable(), "por",
					filter.getPor(), "filtro", filter.getFiltro());

			if (r.getStatus() == 500) {
				JavaFXUtil.buildAlertService500();
			} else if (r.getStatus() == 204) {
				return items;
			} else if (r.getStatus() == 200) {
				JsonArray rows = Service.toJsonArray(r.getPayload());
				for (int i = 0; i < rows.size(); i++) {
					JsonArray columns = rows.getJsonArray(i);
					CuentaContableTableItem item = new CuentaContableTableItem();

					int c = 0;
					if (columns.isNull(c) == false) {
						item.setId(columns.getString(c));
					}
					c++;

					if (columns.isNull(c) == false) {
						item.setCodigo(columns.getString(c));
					}
					c++;

					if (columns.isNull(c) == false) {
						item.setNombre(columns.getString(c));
					}
					c++;

					if (columns.isNull(c) == false) {
						item.setCentroCostoContable(columns.getString(c));
					}
					c++;

					if (columns.isNull(c) == false) {
						item.setCuentaAgrupadora(columns.getString(c));
					}
					c++;

					if (columns.isNull(c) == false) {
						item.setPorcentaje(columns.getString(c));
					}

					items.add(item);
				}

				return items;
			} else {
				throw new IllegalStateException("Illegal state response, code " + r.getStatus());
			}

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
		return items;

	}

//	private List<CuentaContableTableItem> findAll(TableFilter filterPagin, CuentaContableTableFilter filter) {
//		
//		try {
//			String urlString = "/rpc/v1/CuentaContable/findAll?ejercicioContable=2002";
//			
//			ResponseJsonObject  r = Service.GET("rpc", urlString, "db1"); 
//			
//			if(r.getStatus() == 200) {
//				
//			}
//
//			System.out.println(filter);
//
//			// ---------------------------------------
//
//			int fromIndex = filterPagin.getOffset();
//			int toIndex = filterPagin.getOffset() + filterPagin.getLimit();
//
//			if (fromIndex > CuentaContableService.items.size() - 1) {
//				return new ArrayList<CuentaContableTableItem>();
//			}
//
//			if (toIndex > CuentaContableService.items.size() - 1) {
//				return new ArrayList<CuentaContableTableItem>();
//			}
//
//			return CuentaContableService.items.subList(fromIndex, toIndex);
//		}catch(Exception e) {
//			
//		}	
//
//	}

}
