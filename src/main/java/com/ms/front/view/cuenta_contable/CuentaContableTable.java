package com.ms.front.view.cuenta_contable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ms.front.commons.services.Service;
import com.ms.front.model.IdDesc;
import com.ms.front.model.IdDescArgs;
import com.ms.front.model.Pagin;
import com.ms.front.model.PaginArgs;
import com.ms.front.model.ServiceArgs;
import com.ms.front.view.JavaFXUtil;
import com.ms.front.view.centro_costo_contable.CentroCostoContablePaginArgs;
import com.ms.front.view.centro_costo_contable.CentroCostoContableTable;
import com.ms.front.view.centro_costo_contable.CentroCostoContableTableItem;
import com.ms.front.view.punto_equilibrio.PuntoEquilibrioIdDescArgs;
import com.ms.front.view.punto_equilibrio.PuntoEquilibrioPaginArgs;
import com.ms.front.view.punto_equilibrio.PuntoEquilibrioTable;
import com.ms.front.view.punto_equilibrio.PuntoEquilibrioTableItem;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CuentaContableTable implements Initializable {

	private static boolean MODE_SELECCIONAR = true;
	private static boolean MODE_NORMAL = false;

	private static final String TITLE = "Plan de cuentas";

	private Stage stage;
	private SimpleBooleanProperty modoSeleccionarProperty = new SimpleBooleanProperty();
	private PaginArgs paginArgs = new PaginArgs();
	private CuentaContablePaginArgs args;
	private CuentaContablePaginArgs argsOld;
//	private Pagin pagin;

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

//	@FXML
//	private Label status;

	@FXML
	private ProgressIndicator progress;

	@FXML
	private Button buscar;

	@FXML
	private Label totalItems;

	@FXML
	private Label totalPages;

	@FXML
	private HBox pagination;

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
	private TextField filtro;

	@FXML
	private ChoiceBox<String> operator;

	@FXML
	private Label puntoEquilibrioLabel;

	@FXML
	private Button openPuntoEquilibrioTable;

	@FXML
	private TextField puntoEquilibrioSearch;

	@FXML
	private AnchorPane filterPuntoEqulibrio;

	@FXML
	private AnchorPane filterVarios;

	@FXML
	private AnchorPane filterCentroCosto;

	@FXML
	private Label centroCostoLabel;

	@FXML
	private TextField centroCostoSearch;

	@FXML
	private Button openCentroCostoTable;

	// =============================================================================================

	private String textValueTmpCentroCosto;

	@FXML
	void onActionOpenCentoCostoTable(ActionEvent event) {
		try {
			openCentroCostoTableItem();
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	@FXML
	void onKeyTypedSearchCentroCosto(KeyEvent event) {
		int key = (int) event.getCharacter().charAt(0);
		if (key == 13) {
			buscarCentroCosto();
		}
	}

	private void onFocusedCentroCostoSearch(Boolean oldVal, Boolean newVal) {
		if (oldVal == false && newVal == true) { // entra
			textValueTmpCentroCosto = centroCostoSearch.getText();
			centroCostoSearch.setText("");
			centroCostoSearch.setFont(Font.font("System", FontPosture.ITALIC, 12));
			centroCostoSearch.setStyle("-fx-background-color: #607d8b; -fx-text-fill: #FFFFFF;"); // blueGrey 500
		} else if (oldVal == true && newVal == false) { // sale
			centroCostoSearch.setText(textValueTmpCentroCosto);
			centroCostoSearch.setFont(Font.font("System", FontPosture.REGULAR, 12));
			centroCostoSearch.setStyle("");
		}
	}

	private void buscarCentroCosto() {

		try {

			if (centroCostoSearch.getText().trim().length() == 0) {

				textValueTmpCentroCosto = "";
				centroCostoSearch.setText(textValueTmpCentroCosto);
				args.setFiltro(null);

				this.onPorCentroDeCosto(null);

				return;
			}

			IdDesc idDesc = this.centroCostoFindOneByText(centroCostoSearch.getText());

			if (idDesc != null && idDesc.getId() != null) {
				textValueTmpCentroCosto = idDesc.getDesc();
				centroCostoSearch.setText(textValueTmpCentroCosto);
				openCentroCostoTable.requestFocus();
				args.setFiltro(idDesc.getId());
				this.onPorCentroDeCosto(null);
			} else {
				openCentroCostoTableItem();
			}

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	private void openCentroCostoTableItem() throws IOException {
		CentroCostoContablePaginArgs filter = new CentroCostoContablePaginArgs();
		filter.setEjercicioContable(args.getEjercicioContable());
		CentroCostoContableTableItem item = CentroCostoContableTable.showAndWait(new Stage(), view, filter);
		if (item != null) {
			textValueTmpCentroCosto = item.getNumero() + " - " + item.getNombre();
			centroCostoSearch.setText(textValueTmpCentroCosto);
			openCentroCostoTable.requestFocus();
			args.setFiltro(item.getId());
		} else {
			textValueTmpCentroCosto = "";
			centroCostoSearch.setText(textValueTmpCentroCosto);
			args.setFiltro(null);
		}

		this.onPorCentroDeCosto(null);

	}

	// =============================================================================================

	private String textValueTmpPuntoEquilibrio;

	@FXML
	void onActionOpenPuntoEquilibrioTable(ActionEvent event) {
		try {
			openPuntoEquilibrioTableItem();
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	@FXML
	void onKeyTypedSearchPuntoEquilibrio(KeyEvent event) {
		int key = (int) event.getCharacter().charAt(0);
		if (key == 13) {
			buscarPuntoEquilibrio();
		}
	}

	private void onFocusedPuntoEquilibrioSearch(Boolean oldVal, Boolean newVal) {
		if (oldVal == false && newVal == true) { // entra
			textValueTmpPuntoEquilibrio = puntoEquilibrioSearch.getText();
			puntoEquilibrioSearch.setText("");
			puntoEquilibrioSearch.setFont(Font.font("System", FontPosture.ITALIC, 12));
			puntoEquilibrioSearch.setStyle("-fx-background-color: #607d8b; -fx-text-fill: #FFFFFF;"); // blueGrey 500
		} else if (oldVal == true && newVal == false) { // sale
			puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
			puntoEquilibrioSearch.setFont(Font.font("System", FontPosture.REGULAR, 12));
			puntoEquilibrioSearch.setStyle("");
		}
	}

	private void buscarPuntoEquilibrio() {

		try {

			if (puntoEquilibrioSearch.getText().trim().length() == 0) {

				textValueTmpPuntoEquilibrio = "";
				puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
				args.setFiltro(null);

				this.onPorPuntoDeEquilibrio(null);

				return;
			}

			IdDesc idDesc = this.puntoEquilibrioFindOneByText(puntoEquilibrioSearch.getText());

			if (idDesc != null && idDesc.getId() != null) {
				textValueTmpPuntoEquilibrio = idDesc.getDesc();
				puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
				openPuntoEquilibrioTable.requestFocus();
				args.setFiltro(idDesc.getId());
				this.onPorPuntoDeEquilibrio(null);
			} else {
				openPuntoEquilibrioTableItem();
			}

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	private void openPuntoEquilibrioTableItem() throws IOException {
		PuntoEquilibrioPaginArgs filter = new PuntoEquilibrioPaginArgs();
		filter.setEjercicioContable(args.getEjercicioContable());
		PuntoEquilibrioTableItem item = PuntoEquilibrioTable.showAndWait(new Stage(), view, filter);
		if (item != null) {
			textValueTmpPuntoEquilibrio = item.getNumero() + " - " + item.getNombre();
			puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
			openPuntoEquilibrioTable.requestFocus();
			args.setFiltro(item.getId());
		} else {
			textValueTmpPuntoEquilibrio = "";
			puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
			args.setFiltro(null);
		}

		this.onPorPuntoDeEquilibrio(null);

	}

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
		} else if (event.getCode().equals(KeyCode.DIGIT1) && event.isControlDown()) {
			if (operator.getSelectionModel().getSelectedIndex() != 0) {
				operator.getSelectionModel().select(0);
			}
		} else if (event.getCode().equals(KeyCode.DIGIT2) && event.isControlDown()) {
			if (operator.getSelectionModel().getSelectedIndex() != 1) {
				operator.getSelectionModel().select(1);
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
	private void onPorCuentaContable(ActionEvent event) {

		if (porCuentaContable.isSelected()) {
			filterVarios.setVisible(true);
			filterCentroCosto.setVisible(false);
			filterPuntoEqulibrio.setVisible(false);

			args.setFiltro(filtro.getText());
			args.setPorCuentaContable();
			filtro.setPromptText("Buscar por cuenta contable");
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porNombre);
			onPorNombre(null);
		}

	}

	@FXML
	private void onPorNombre(ActionEvent event) {
		if (porNombre.isSelected()) {
			filterVarios.setVisible(true);
			filterCentroCosto.setVisible(false);
			filterPuntoEqulibrio.setVisible(false);

			args.setFiltro(filtro.getText());
			args.setPorNombre();
			filtro.setPromptText("Buscar por nombre");
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porCuentaAgrupadora);
			onPorCuentaAgrupadora(null);
		}

	}

	@FXML
	private void onPorCuentaAgrupadora(ActionEvent event) {
		if (porCuentaAgrupadora.isSelected()) {
			filterVarios.setVisible(true);
			filterCentroCosto.setVisible(false);
			filterPuntoEqulibrio.setVisible(false);

			args.setFiltro(filtro.getText());
			args.setPorCuentaAgrupadora();
			filtro.setPromptText("Buscar por cuenta agrupadora");
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porCentroDeCosto);
			onPorCentroDeCosto(null);
		}

	}

	@FXML
	private void onPorCentroDeCosto(ActionEvent event) {
		if (porCentroDeCosto.isSelected()) {
			filterVarios.setVisible(false);
			filterCentroCosto.setVisible(true);
			filterPuntoEqulibrio.setVisible(false);

			args.setPorCentroDeCosto();
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porPuntoDeEquilibrio);
			onPorPuntoDeEquilibrio(null);
		}

	}

	@FXML
	private void onPorPuntoDeEquilibrio(ActionEvent event) {
		if (porPuntoDeEquilibrio.isSelected()) {
			filterVarios.setVisible(false);
			filterCentroCosto.setVisible(false);
			filterPuntoEqulibrio.setVisible(true);

			args.setPorPuntoDeEquilibrio();
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porCuentaContable);
			onPorCuentaContable(null);
		}

	}

	// ================================================================================================

	private void onBuscarChangeOperadorFiltro(int index) {

		if (index == 0) {
			args.setOperador(ServiceArgs.OP_SW_ICT_O);
			operator.setTooltip(new Tooltip("Buscar (CTRL+#) - " + ServiceArgs.OP_SW_ICT_O_TXT));
		} else if (index == 1) {
			args.setOperador(ServiceArgs.OP_C_ICT_A);
			operator.setTooltip(new Tooltip("Buscar (CTRL+#) - " + ServiceArgs.OP_C_ICT_A_TXT));
		}
		onBuscarStart();
	}

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

//		status.setText(msg);

		progress.setVisible(true);

		String lastId = null;
		if (table.getSelectionModel().getSelectedIndex() > -1) {
			lastId = table.getSelectionModel().getSelectedItem().getId();
		}

		List<CuentaContableTableItem> items = findAll();

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

//		progress.setVisible(false);

//		status.setText("");
	}

	// ==========================================================================

	private IdDesc centroCostoFindOneByText(String text) throws IOException, URISyntaxException {

		IdDescArgs idDescArgs = new IdDescArgs();
		idDescArgs.setText(text);

		PuntoEquilibrioIdDescArgs argsPuntoEquilibrio = new PuntoEquilibrioIdDescArgs();
		argsPuntoEquilibrio.setEjercicioContable(args.getEjercicioContable());

		String urlString = "CentroCostoContable/findOneByText";

		return Service.GETIdDesc(urlString, idDescArgs, argsPuntoEquilibrio);

	}

	private IdDesc puntoEquilibrioFindOneByText(String text) throws IOException, URISyntaxException {

		IdDescArgs idDescArgs = new IdDescArgs();
		idDescArgs.setText(text);

		PuntoEquilibrioIdDescArgs argsPuntoEquilibrio = new PuntoEquilibrioIdDescArgs();
		argsPuntoEquilibrio.setEjercicioContable(args.getEjercicioContable());

		String urlString = "PuntoEquilibrio/findOneByText";

		return Service.GETIdDesc(urlString, idDescArgs, argsPuntoEquilibrio);

	}

	private List<CuentaContableTableItem> findAll() {

		if (args.getOperador() == null) {
			args.setOperador(ServiceArgs.OP_SW_ICT_O);
		}

		totalItems.setText("");
		totalPages.setText("");

		List<CuentaContableTableItem> items = new ArrayList<CuentaContableTableItem>();

		try {

			String urlString = "CuentaContable/findAllPagin";

			Pagin pagin = Service.GETPagin(urlString, paginArgs, args);
			paginArgs.setLastIndexOld(pagin.getLastIndex());

			if (pagin.getThisPageItems() == null || pagin.getThisPageItems().length == 0) {
				return items;
			}

			totalItems.setText(pagin.getCantRows().toString() + " ítems");
			totalPages.setText("Página " + (pagin.getThisPage().getPageNumber() + 1) + " de " + (pagin.getCantPages()));

			Object[][] t = pagin.getThisPageItems();

			for (int i = 0; i < t.length; i++) {

				CuentaContableTableItem item = new CuentaContableTableItem();

				int j = 0;
				if (t[i][j] != null) {
					item.setId(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setCodigo(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setNombre(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setCentroCostoContable(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setCuentaAgrupadora(t[i][j].toString());
				}

				j++;
				if (t[i][j] != null) {
					item.setPorcentaje(t[i][j].toString());
				}

				items.add(item);
			}

			return items;

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
		return items;

	}

	// ==========================================================================

	// ================================================================================================

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
		copiar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		seleccionar.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));
		pagination.disableProperty().bind(Bindings.size(table.getItems()).isEqualTo(0));

		// --------------------------------------------------------------------------

		codigo.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("codigo"));
		nombre.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("nombre"));
		centroCostoContable
				.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("centroCostoContable"));
		cuentaAgrupadora
				.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("cuentaAgrupadora"));
		porcentaje.setCellValueFactory(new PropertyValueFactory<CuentaContableTableItem, String>("porcentaje"));

		// --------------------------------------------------------------------------

		operator.setItems(FXCollections.observableArrayList("(1) comienza con", "(2) contiene"));
		operator.setTooltip(new Tooltip("Buscar (CTRL+#) - " + ServiceArgs.OP_SW_ICT_O_TXT));
		operator.getSelectionModel().selectFirst();

		operator.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldV, newV) -> onBuscarChangeOperadorFiltro((int) newV));

		// --------------------------------------------------------------------------

		centroCostoSearch.focusedProperty()
				.addListener((obs, oldVal, newVal) -> onFocusedCentroCostoSearch(oldVal, newVal));

		puntoEquilibrioSearch.focusedProperty()
				.addListener((obs, oldVal, newVal) -> onFocusedPuntoEquilibrioSearch(oldVal, newVal));

	}

	// ================================================================================================

	private static CuentaContableTable show(Stage stage, Node owner, boolean modoSeleccionar,
			CuentaContablePaginArgs filter) throws IOException {

		CuentaContableTable viewController = loadView(modoSeleccionar, filter);
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

	public static CuentaContableTableItem showAndWait(Stage stage, Node owner, CuentaContablePaginArgs filter)
			throws IOException {

		CuentaContableTable viewController = show(stage, owner, MODE_SELECCIONAR, filter);

		if (viewController.table.getSelectionModel().getSelectedIndex() > -1) {
			return viewController.table.getSelectionModel().getSelectedItem();
		}

		return null;
	}

	public static void show(Stage stage, Node owner, CuentaContablePaginArgs filter) throws IOException {
		show(stage, owner, MODE_NORMAL, filter);
	}

	private static CuentaContableTable loadView(boolean modoSeleccionar, CuentaContablePaginArgs filter)
			throws IOException {

		if (filter.getEjercicioContable() == null) {
			throw new IllegalArgumentException("filter.getEjercicioContable() is null");
		}

		FXMLLoader loader = new FXMLLoader(CuentaContableTable.class.getResource("CuentaContableTable.fxml"));

		loader.load();

		CuentaContableTable viewController = loader.getController();
		viewController.modoSeleccionarProperty.set(modoSeleccionar);
		viewController.args = filter;
//		viewController.filtro.textProperty().bindBidirectional(filter.filtroProperty());
		viewController.onPorCuentaContable(null);

		return viewController;
	}

}
