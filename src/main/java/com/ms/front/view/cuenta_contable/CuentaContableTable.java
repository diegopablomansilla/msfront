package com.ms.front.view.cuenta_contable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.ms.front.services.ServiceArgs;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CuentaContableTable extends Controller {

	private static final String TITLE = "Plan de cuentas";

	private CuentaContablePaginArgs args;
	private CuentaContablePaginArgs argsOld;

	// =============================================================================================

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
	private ChoiceBox<String> operator;

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
	private void onPorCuentaContable(ActionEvent event) {
		args.setPorCuentaContable();
		filtro.setPromptText("Buscar por cuenta contable");
		onBuscarStart();
	}

	@FXML
	private void onPorNombre(ActionEvent event) {
		args.setPorNombre();
		filtro.setPromptText("Buscar por nombre");
		onBuscarStart();
	}

	@FXML
	private void onPorCuentaAgrupadora(ActionEvent event) {
		args.setPorCuentaAgrupadora();
		filtro.setPromptText("Buscar por cuenta agrupadora");
		onBuscarStart();
	}

	@FXML
	private void onPorCentroDeCosto(ActionEvent event) {
		args.setPorCentroDeCosto();
		filtro.setPromptText("Buscar por centro de costo");
		onBuscarStart();
	}

	@FXML
	private void onPorPuntoDeEquilibrio(ActionEvent event) {
		args.setPorPuntoDeEquilibrio();
		filtro.setPromptText("Buscar por punto de equilibrio");
		onBuscarStart();
	}

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

	// =============================================================================================
	// =============================================================================================
	// =============================================================================================

	protected ServiceArgs getArgs() {
		return args;
	}
//	
//	protected void setArgs(ServiceArgs args) {
//		this.args = (CuentaContablePaginArgs) args; 
//	}
//	
//	protected ServiceArgs getArgsOld() {
//		return argsOld;
//	}
//	
//	protected void setArgsOld(ServiceArgs argsOld) {
//		this.argsOld = (CuentaContablePaginArgs) argsOld;
//	} 

	protected boolean isArgsEqualsArgsOld() {
		return this.argsOld != null && argsOld.equals(this.args);
	}

	protected void toAssignArgsToArgsOld() {
		argsOld = this.args.clone();
	}

	protected String getSelectedId() {
		if (table.getSelectionModel().getSelectedIndex() > -1) {
			return table.getSelectionModel().getSelectedItem().getId();
		}

		return null;
	}

	protected boolean selectId(String id) {
		for (int i = 0; i < table.getItems().size(); i++) {
			if (table.getItems().get(i).getId().equals(id)) {
				table.getSelectionModel().select(i);
				return true;
			}
		}

		return false;
	}

	protected void findAllBefore() {
		if (args.getOperador() == null) {
			args.setOperador(ServiceArgs.OP_SW_ICT_O);
		}
	}

	protected String getFindAllUrl() {
		return "CuentaContable/findAll";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void mapper(List items, Object[][] t) {

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

	}

	// ================================================================================================

	private void initializeAfter(CuentaContablePaginArgs args) {
		this.args = args;
		filtro.textProperty().bindBidirectional(args.filtroProperty());
		onPorCuentaContable(null);
	}

	private static void initializeBefore(CuentaContablePaginArgs args) {
		if (args.getEjercicioContable() == null) {
			throw new IllegalArgumentException("args.getEjercicioContable() is null");
		}
	}

	public static void show(Stage stage, Node owner, CuentaContablePaginArgs args) throws IOException {

		initializeBefore(args);

		CuentaContableTable controller = (CuentaContableTable) load(CuentaContableTable.class, TITLE, stage, owner,
				MODE_NORMAL);

		controller.initializeAfter(args);

		stage.show();
	}

	public static CuentaContableTableItem showAndWait(Stage stage, Node owner, CuentaContablePaginArgs args)
			throws IOException {

		initializeBefore(args);

		CuentaContableTable controller = (CuentaContableTable) load(CuentaContableTable.class, TITLE, stage, owner,
				MODE_SELECCIONAR);

		controller.initializeAfter(args);

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		if (controller.table.getSelectionModel().getSelectedIndex() > -1) {
			return controller.table.getSelectionModel().getSelectedItem();
		}

		return null;
	}

	// ================================================================================================

	public void initialize(URL url, ResourceBundle rb) {

		super.initialize(url, rb);

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

	}

}
