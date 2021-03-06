package com.ms.front.commons.views;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import javafx.stage.Modality;

public class JavaFXUtil {

	public static Alert buildAlertConfirmDeleteItem(String contentText) {

		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("¿ Esta seguro?");
		alert.setHeaderText("¿ Esta seguro de borrar el ítem ?");
		alert.setContentText(contentText);

		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

		// Deactivate Defaultbehavior for yes-Button:
		Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
		yesButton.setDefaultButton(false);

		// Activate Defaultbehavior for no-Button:
		Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
		noButton.setDefaultButton(true);

		return alert;

	}

	// https://code.makery.ch/blog/javafx-dialogs-official/
	public static void buildAlertService500() {

		Alert alert = new Alert(AlertType.ERROR);

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("ERROR");
		alert.setHeaderText("Ups ha ocurrido un error!!!");
		alert.setContentText("Lo sentimos, ha ocurrido un error interno en el servidor.");

		alert.show();

	}
	
	public static void buildAlertService400() {

		Alert alert = new Alert(AlertType.ERROR);

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("ERROR");
		alert.setHeaderText("Ups ha ocurrido un error!!!");
		alert.setContentText("Lo sentimos, ha ocurrido un error interno en la app.");

		alert.show();

	}

	public static void buildAlertException(Exception e) {

		e.printStackTrace();

		Alert alert = new Alert(AlertType.ERROR);

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setTitle("ERROR");
		alert.setHeaderText("Ups ha ocurrido un error!!!");
		alert.setContentText("Lo sentimos, ha ocurrido un error interno en la App. " + e);

		alert.show();

	}

//	JavaFXUtil.addEvenTtableviewScrolling(table);

//	table.addEventHandler(ScrollEvent.ANY, event -> {
//		if (event.getEventType().equals(ScrollEvent.TOP_REACHED)) {
//			onBuscarBack();
//		} else if (event.getEventType().equals(ScrollEvent.BOTTOM_REACHED)) {
//			onBuscarNext();
//		}
//
//	});

	public static ScrollBar getVerticalScrollbar(final TableView<?> table) {
		ScrollBar result = null;
		for (final Node n : table.lookupAll(".scroll-bar")) {
			if (n instanceof ScrollBar) {
				final ScrollBar bar = (ScrollBar) n;
				if (bar.getOrientation().equals(Orientation.VERTICAL)) {
					result = bar;
				}
			}
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addEvenTtableviewScrolling(TableView table) {

		int TOP_REACHED = 0;
		int BOTTOM_REACHED = 1;

		// Now, let's add a listener to the TableView's scrollbar. We can only access
		// the ScrollBar after the Scene is
		// rendered, so we need to do schedule this to run later.
		Platform.runLater(() -> {

			ScrollBar scrollBar = (ScrollBar) table.lookup(".scroll-bar:vertical");

			System.out.println(scrollBar.computeAreaInScreen());

			scrollBar.setOnScroll(e -> System.out.println("xxxxxxxxxxxxx0"));

			scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {

//				double d = (Double) newValue;

//				int index = table.getSelectionModel().getSelectedIndex();
//
//				int r = -1;

//				System.out.println(d);

//				if (d == 0.0 || d <= 0.0000000000000009) {
//					r = TOP_REACHED;
//				} else if (d == 1.0 || d == 0.9999999999999999) {
//					r = BOTTOM_REACHED;
//				}

			});

		});

		// ==================================================================================

		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			int index = table.getSelectionModel().getSelectedIndex();
			int size = table.getItems().size();
			int lastIndex = size - 1;
			int result = -1;

			if (index > -1 && size > 0) {

				if (index == 0) {
					result = TOP_REACHED;
				} else if (index == lastIndex) {
					result = BOTTOM_REACHED;
				}

				if (result == TOP_REACHED) {
					System.out.printf("%s%n", "BOTTOM_REACHED TTT");
//					table.fireEvent(new ScrollEvent(ScrollEvent.TOP_REACHED));					
				} else if (result == BOTTOM_REACHED) {
					System.out.printf("%s%n", "TOP_REACHED TTT");
//					table.fireEvent(new ScrollEvent(ScrollEvent.BOTTOM_REACHED));					
				}
			}

		});

	}
}
