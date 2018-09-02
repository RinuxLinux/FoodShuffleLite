package com.s2rltx.projet.foodshuflle.application.controller;

import java.awt.TextArea;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class Page3_controller {

	@FXML
	private TextArea liste;
	@FXML
	private ImageView imprimer;

	@FXML
	public void imprimerClicked() {
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);

        alert.setContentText("Fonctionnalité en cours de construction");
        alert.showAndWait();
		
	}
}
