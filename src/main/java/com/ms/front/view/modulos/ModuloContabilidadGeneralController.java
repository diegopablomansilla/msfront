package com.ms.front.view.modulos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ms.front.Session;
import com.ms.front.commons.views.JavaFXUtil;
import com.ms.front.services.EjercicioContableFindOneByText;
import com.ms.front.view.AsientoModeloTable;
import com.ms.front.view.CentroCostoContableTable;
import com.ms.front.view.CuentaContableTable;
import com.ms.front.view.EjercicioContableTable;
import com.ms.front.view.PuntoEquilibrioTable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import x.com.ms.front.model.IdDesc;
import x.com.ms.front.model.TableItem7;

public class ModuloContabilidadGeneralController implements Initializable {

	private static final String TITLE = "Contabilidad general";

	private final SimpleStringProperty ejercicioContableId = new SimpleStringProperty();

	// =============================================================================================

	@FXML
	public VBox view;

	@FXML
	private MenuItem mOpenCuentaContableList;

	@FXML
	private MenuItem mOpenAsientoModeloList;

	@FXML
	private MenuItem mOpenCentroDeCostoContableList;

	@FXML
	private MenuItem mOpenPuntoEquilibrioList;

	@FXML
	private AnchorPane filterEjercicioContable;

	@FXML
	private Label ejercicioContableLabel;

	@FXML
	private TextField ejercicioContableSearch;

	@FXML
	private Button openEjercicioContableTable;

	// =============================================================================================

	private String textValueTmpEjercicioContable;

	@FXML
	void onActionOpenEjercicioContableTable(ActionEvent event) {
		try {
			openEjercicioContableTableItem();
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	@FXML
	void onKeyTypedSearchEjercicioContable(KeyEvent event) {
		int key = (int) event.getCharacter().charAt(0);
		if (key == 13) {
			buscarEjercicioContable();
		}
	}

	private void onFocusedEjercicioContableSearch(Boolean oldVal, Boolean newVal) {
		if (oldVal == false && newVal == true) { // entra
			textValueTmpEjercicioContable = ejercicioContableSearch.getText();
			ejercicioContableSearch.setText("");
			ejercicioContableSearch.setFont(Font.font("System", FontPosture.ITALIC, 12));
			ejercicioContableSearch.setStyle("-fx-background-color: #607d8b; -fx-text-fill: #FFFFFF;"); // blueGrey 500
		} else if (oldVal == true && newVal == false) { // sale
			ejercicioContableSearch.setText(textValueTmpEjercicioContable);
			ejercicioContableSearch.setFont(Font.font("System", FontPosture.REGULAR, 12));
			ejercicioContableSearch.setStyle("");
		}
	}

	private void buscarEjercicioContable() {

		try {

			if (ejercicioContableSearch.getText().trim().length() == 0) {

				textValueTmpEjercicioContable = "";
				ejercicioContableSearch.setText(textValueTmpEjercicioContable);

				ejercicioContableId.set(null);
//				args.setFiltro(null);
//				this.onPorEjercicioContable(null); // buscar findAll

				return;
			}

//			IdDesc idDesc = this.ejercicioContableFindOneByText(ejercicioContableSearch.getText());
			EjercicioContableFindOneByText service = new EjercicioContableFindOneByText();
			IdDesc idDesc = service.find(Session.DB, ejercicioContableSearch.getText());

			if (idDesc != null && idDesc.getId() != null) {
				textValueTmpEjercicioContable = idDesc.getDesc();
				ejercicioContableSearch.setText(textValueTmpEjercicioContable);
				openEjercicioContableTable.requestFocus();

				ejercicioContableId.set(idDesc.getId());
//				args.setFiltro(idDesc.getId());
//				this.onPorEjercicioContable(null); // buscar findAll
			} else {
				openEjercicioContableTableItem();
			}

		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	private void openEjercicioContableTableItem() throws IOException {
		TableItem7 item = (TableItem7) EjercicioContableTable.showAndWait(new Stage(), view);
		if (item != null) {
			textValueTmpEjercicioContable = item.getAtt2();
			ejercicioContableSearch.setText(textValueTmpEjercicioContable);
			openEjercicioContableTable.requestFocus();
			ejercicioContableId.set(item.getAtt1());
//			args.setFiltro(item.getId());
		} else {
			textValueTmpEjercicioContable = "";
			ejercicioContableSearch.setText(textValueTmpEjercicioContable);
			ejercicioContableId.set(null);
//			args.setFiltro(null);
		}
//		this.onPorEjercicioContable(null); // buscar findAll

	}

	// =============================================================================================

	public void initialize(URL url, ResourceBundle rb) {

		mOpenCuentaContableList.disableProperty().bind(Bindings.isEmpty(ejercicioContableId));
		mOpenAsientoModeloList.disableProperty().bind(Bindings.isEmpty(ejercicioContableId));
		mOpenCentroDeCostoContableList.disableProperty().bind(Bindings.isEmpty(ejercicioContableId));
		mOpenPuntoEquilibrioList.disableProperty().bind(Bindings.isEmpty(ejercicioContableId));
		// --------------------------------------------------------------------------

		ejercicioContableSearch.focusedProperty()
				.addListener((obs, oldVal, newVal) -> onFocusedEjercicioContableSearch(oldVal, newVal));
	}

	public static ModuloContabilidadGeneralController loadView() throws IOException {

		FXMLLoader loader = new FXMLLoader(
				ModuloContabilidadGeneralController.class.getResource("ModuloContabilidadGeneral.fxml"));

		loader.load();

		ModuloContabilidadGeneralController viewController = loader.getController();

		return viewController;
	}

	public static void show(Stage stage) throws IOException {

		ModuloContabilidadGeneralController viewController = loadView();

		stage.setScene(new Scene(viewController.view));
		stage.setTitle(TITLE);
		stage.setAlwaysOnTop(false);
		stage.setResizable(false);
		stage.centerOnScreen();

//		stage.initStyle(StageStyle.UTILITY);
//		stage.initStyle(StageStyle.DECORATED);
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.initStyle(StageStyle.TRANSPARENT);

		stage.show();

	}

	// ----------------------------------------------------------------------

	@FXML
	void onOpenCuentaContableList(ActionEvent event) {
		try {
			CuentaContableTable.show(view, ejercicioContableId.get(), CuentaContableTable.POR_CUENTA_CONTABLE);
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}

	}

	@FXML
	void onOpenPuntoEquilibrioList(ActionEvent event) {
		try {
			PuntoEquilibrioTable.show(view, ejercicioContableId.get());
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	@FXML
	void onOpenCentroDeCostoContableList(ActionEvent event) {

		try {
			CentroCostoContableTable.show(view, ejercicioContableId.get(),
					CentroCostoContableTable.POR_CENTRO_DE_COSTO);
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}

	}

	@FXML
	void onOpenEjercicioContableList(ActionEvent event) {
		try {
			EjercicioContableTable.show(new Stage(), view);
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

	@FXML
	void onOpenAsientoModeloList(ActionEvent event) {
		try {
			AsientoModeloTable.show(view, ejercicioContableId.get());
		} catch (Exception e) {
			JavaFXUtil.buildAlertException(e);
		}
	}

}
