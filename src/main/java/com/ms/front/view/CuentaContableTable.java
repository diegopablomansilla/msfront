package com.ms.front.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ms.front.Session;
import com.ms.front.commons.views.JavaFXUtil;
import com.ms.front.commons.views.Table;
import com.ms.front.model.IdDesc;
import com.ms.front.model.Pagin;
import com.ms.front.model.TableItem4;
import com.ms.front.model.TableItem6;
import com.ms.front.services.CentroCostoContableFindOneByText;
import com.ms.front.services.CuentaContableFindAllPagin;
import com.ms.front.services.PuntoEquilibrioFindOneByText;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CuentaContableTable extends Table<TableItem6> {

	public static final String POR_CUENTA_CONTABLE = "CUENTA_CONTABLE";
	public static final String POR_NOMBRE = "NOMBRE";
	public static final String POR_CUENTA_AGRUPADORA = "CUENTA_AGRUPADORA";
	public static final String POR_CENTRO_DE_COSTO = "CENTRO_DE_COSTO";
	public static final String POR_PUNTO_DE_EQUILIBRIO = "PUNTO_DE_EQUILIBRIO";

	public static final String OP_SW_ICT_O = "SW_ICT_O";
	public static final String OP_C_ICT_A = "C_ICT_A";
	public static final String OP_C_ICT_A_TXT = "Contiene todas las palabras por búsqueda completa (ignorando mayúsculas y acentos, tildes, etc.)";
	public static final String OP_SW_ICT_O_TXT = "Comienza con alguna de las palabras por búsqueda completa (ignorando mayúsculas y acentos, tildes, etc.)";

	private String ejercicioContableId;
	private String por;
	private String operador;
	private String filtroValue;

	private String textValueTmpCentroCosto;
	private String textValueTmpPuntoEquilibrio;

	// =============================================================================================

	@FXML
	private Button copiar;

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

	// ---------------------------------------------------------------------------------------------

	@FXML
	private TextField filtro;

	@FXML
	private ChoiceBox<String> operator;

	@FXML
	private Button openPuntoEquilibrioTable;

	@FXML
	private TextField puntoEquilibrioSearch;

	@FXML
	private TextField centroCostoSearch;

	@FXML
	private AnchorPane filterPuntoEqulibrio;

	@FXML
	private AnchorPane filterVarios;

	@FXML
	private AnchorPane filterCentroCosto;

	@FXML
	private Button openCentroCostoTable;

	// =============================================================================================

	@FXML
	private TableColumn<TableItem6, String> att2;

	@FXML
	private TableColumn<TableItem6, String> att3;

	@FXML
	private TableColumn<TableItem6, String> att4;

	@FXML
	private TableColumn<TableItem6, String> att5;

	@FXML
	private TableColumn<TableItem6, String> att6;

	// ================================================================================================

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

	@Override
	protected List<TableItem6> toList(Object[][] t) {

		List<TableItem6> items = new ArrayList<TableItem6>();

		for (int i = 0; i < t.length; i++) {
			items.add(new TableItem6(t[i]));
		}

		return items;
	}

	protected Pagin findPage() throws Exception {

		CuentaContableFindAllPagin service = new CuentaContableFindAllPagin();

		System.out.println(ejercicioContableId);
		System.out.println(por);
		System.out.println(operador);
		System.out.println(filtroValue);

		if (por.equals(POR_CUENTA_CONTABLE)) {
			filtroValue = filtro.getText();
		} else if (por.equals(POR_NOMBRE)) {
			filtroValue = filtro.getText();
		} else if (por.equals(POR_CUENTA_AGRUPADORA)) {
			filtroValue = filtro.getText();
		} else if (por.equals(POR_CENTRO_DE_COSTO)) {
//			filtroValue = null;
		} else if (por.equals(POR_PUNTO_DE_EQUILIBRIO)) {
//			filtroValue = null;
		}

		if (this.pageRequest.equals(PAGE_REQUEST_FIRST)) {
			return service.findFirst(Session.DB, ejercicioContableId, por, operador, filtroValue);
		} else if (this.pageRequest.equals(PAGE_REQUEST_NEXT)) {
			return service.findNext(Session.DB, this.lastIndexOld, ejercicioContableId, por, operador, filtroValue);
		} else if (this.pageRequest.equals(PAGE_REQUEST_BACK)) {
			return service.findBack(Session.DB, this.lastIndexOld, ejercicioContableId, por, operador, filtroValue);
		} else if (this.pageRequest.equals(PAGE_REQUEST_LAST)) {
			return service.findLast(Session.DB, ejercicioContableId, por, operador, filtroValue);
		}

		throw new IllegalStateException("pageRequest not found. " + pageRequest);
	}

	// ================================================================================================

	@FXML
	private void onBuscarStartPor() {

		if (operador == null) {
			operador = OP_SW_ICT_O;
		}

		if (por == null) {
			por = POR_CUENTA_CONTABLE;
		}

		if (por.equals(POR_CUENTA_CONTABLE)) {
			porToogleGroup.selectToggle(this.porCuentaContable);
			onPorCuentaContable(null);
		} else if (por.equals(POR_NOMBRE)) {
			porToogleGroup.selectToggle(this.porNombre);
			onPorNombre(null);
		} else if (por.equals(POR_CUENTA_AGRUPADORA)) {
			porToogleGroup.selectToggle(this.porCuentaAgrupadora);
			onPorCuentaAgrupadora(null);
		} else if (por.equals(POR_CENTRO_DE_COSTO)) {
			porToogleGroup.selectToggle(porCentroDeCosto);
			onPorCentroDeCosto(null);
		} else if (por.equals(POR_PUNTO_DE_EQUILIBRIO)) {
			porToogleGroup.selectToggle(this.porPuntoDeEquilibrio);
			onPorPuntoDeEquilibrio(null);
		}

	}

	@FXML
	private void onPorCuentaContable(ActionEvent event) {

		if (porCuentaContable.isSelected()) {
			filterVarios.setVisible(true);
			filterCentroCosto.setVisible(false);
			filterPuntoEqulibrio.setVisible(false);

			filtroValue = filtro.getText();
			por = POR_CUENTA_CONTABLE;
//			args.setFiltro(filtro.getText());
//			args.setPorCuentaContable();
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

			filtroValue = filtro.getText();
			por = POR_NOMBRE;
//			args.setFiltro(filtro.getText());
//			args.setPorNombre();
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

			filtroValue = filtro.getText();
			por = POR_CUENTA_AGRUPADORA;
//			args.setFiltro(filtro.getText());
//			args.setPorCuentaAgrupadora();
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

			por = POR_CENTRO_DE_COSTO;
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

			por = POR_PUNTO_DE_EQUILIBRIO;
			onBuscarStart();
		} else {
			porToogleGroup.selectToggle(porCuentaContable);
			onPorCuentaContable(null);
		}

	}

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
	private void onKeyTypedFiltro(KeyEvent event) {

		int key = (int) event.getCharacter().charAt(0);

//		if (event.isControlDown() && key == 13) {
		if (event.isControlDown() && key == 10) {
			onBuscarStart();
		}

	}

	private void onBuscarChangeOperadorFiltro(int index) {

		if (index == 0) {
			operador = OP_SW_ICT_O;
			filtroValue = this.filtro.getText();
//			args.setOperador(ServiceArgs.OP_SW_ICT_O);
			operator.setTooltip(new Tooltip("Buscar (CTRL+#) - " + OP_SW_ICT_O_TXT));
			onBuscarStart();
		} else if (index == 1) {
			operador = OP_C_ICT_A;
			filtroValue = this.filtro.getText();
//			args.setOperador(ServiceArgs.OP_C_ICT_A);
			operator.setTooltip(new Tooltip("Buscar (CTRL+#) - " + OP_C_ICT_A_TXT));
			onBuscarStart();
		}

	}

	// ----------------------------------------------------------------------------------

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

	public void initialize(URL url, ResourceBundle rb) {

		super.initialize(url, rb);

		// --------------------------------------------------------------------------

		att2.setCellValueFactory(new PropertyValueFactory<TableItem6, String>("att2"));
		att3.setCellValueFactory(new PropertyValueFactory<TableItem6, String>("att3"));
		att4.setCellValueFactory(new PropertyValueFactory<TableItem6, String>("att4"));
		att5.setCellValueFactory(new PropertyValueFactory<TableItem6, String>("att5"));
		att6.setCellValueFactory(new PropertyValueFactory<TableItem6, String>("att6"));

		// --------------------------------------------------------------------------

		// --------------------------------------------------------------------------

		operator.setItems(FXCollections.observableArrayList("(1) comienza con", "(2) contiene"));
		operator.setTooltip(new Tooltip("Buscar (CTRL+#) - " + OP_SW_ICT_O_TXT));
		operator.getSelectionModel().selectFirst();

		operator.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldV, newV) -> onBuscarChangeOperadorFiltro((int) newV));

		// --------------------------------------------------------------------------

		centroCostoSearch.focusedProperty()
				.addListener((obs, oldVal, newVal) -> onFocusedCentroCostoSearch(oldVal, newVal));

		puntoEquilibrioSearch.focusedProperty()
				.addListener((obs, oldVal, newVal) -> onFocusedPuntoEquilibrioSearch(oldVal, newVal));

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

		CuentaContableTable viewController = (CuentaContableTable) execShow(CuentaContableTable.class,
				"Plan de cuentas", stage, owner, MODE_SELECCIONAR);

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

		CuentaContableTable viewController = (CuentaContableTable) execShow(CuentaContableTable.class,
				"Plan de cuentas", stage, owner, MODE_NORMAL);

		// ---------------------------------------------------------------------------

		viewController.ejercicioContableId = ejercicioContableId;
		viewController.por = por;

		viewController.onBuscarStartPor();
		viewController.table.requestFocus();

		// ---------------------------------------------------------------------------

		stage.show();

		// ---------------------------------------------------------------------------
	}

	// =============================================================================================
	// BOX CentroCostoContable
	// =============================================================================================

	@FXML
	void onActionOpenCentoCostoTable(ActionEvent event) {
		try {
			openCentroCostoTableItem();
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	private void openCentroCostoTableItem() throws IOException {
		TableItem4 item = CentroCostoContableTable.showAndWait(view, this.ejercicioContableId,
				CentroCostoContableTable.POR_CENTRO_DE_COSTO);
		if (item != null) {
			textValueTmpCentroCosto = item.getAtt2() + " - " + item.getAtt3();
			centroCostoSearch.setText(textValueTmpCentroCosto);
			openCentroCostoTable.requestFocus();
			this.filtroValue = item.getAtt1();
		} else {
			this.filtroValue = null;
			textValueTmpCentroCosto = "";
			centroCostoSearch.setText(textValueTmpCentroCosto);
			this.filtroValue = null;
		}

		this.onPorCentroDeCosto(null);

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
				this.filtroValue = null; // args.setFiltro(null);

				this.onPorCentroDeCosto(null);

				return;
			}

			CentroCostoContableFindOneByText service = new CentroCostoContableFindOneByText();
			IdDesc idDesc = service.find(Session.DB, centroCostoSearch.getText(), ejercicioContableId);

			if (idDesc != null && idDesc.getId() != null) {
				textValueTmpCentroCosto = idDesc.getDesc();
				centroCostoSearch.setText(textValueTmpCentroCosto);
				openCentroCostoTable.requestFocus();
				this.filtroValue = idDesc.getId(); // args.setFiltro(idDesc.getId());
				this.onPorCentroDeCosto(null);
			} else {
				openCentroCostoTableItem();
			}

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	// =============================================================================================
	// BOX PuntoEquilibrio
	// =============================================================================================

	@FXML
	void onActionOpenPuntoEquilibrioTable(ActionEvent event) {
		try {
			openPuntoEquilibrioTableItem();
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	private void openPuntoEquilibrioTableItem() throws IOException {

		TableItem4 item = (TableItem4) PuntoEquilibrioTable.showAndWait(view, this.ejercicioContableId);
		if (item != null) {
			textValueTmpPuntoEquilibrio = item.getAtt2() + " - " + item.getAtt3();
			puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
			openPuntoEquilibrioTable.requestFocus();
			filtroValue = item.getAtt1();
		} else {
			textValueTmpPuntoEquilibrio = "";
			puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
			filtroValue = null;
		}

		this.onPorPuntoDeEquilibrio(null);

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
				filtroValue = null; // args.setFiltro(null);

				this.onPorPuntoDeEquilibrio(null);

				return;
			}

			PuntoEquilibrioFindOneByText service = new PuntoEquilibrioFindOneByText();
			IdDesc idDesc = service.find(Session.DB, puntoEquilibrioSearch.getText(), ejercicioContableId);

			if (idDesc != null && idDesc.getId() != null) {
				textValueTmpPuntoEquilibrio = idDesc.getDesc();
				puntoEquilibrioSearch.setText(textValueTmpPuntoEquilibrio);
				openPuntoEquilibrioTable.requestFocus();
				filtroValue = idDesc.getId(); // args.setFiltro(idDesc.getId());
				this.onPorPuntoDeEquilibrio(null);
			} else {
				openPuntoEquilibrioTableItem();
			}

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

} // END CLASS *****************************************************
