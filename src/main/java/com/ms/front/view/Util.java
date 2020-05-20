package com.ms.front.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

public class Util {
	
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

}
