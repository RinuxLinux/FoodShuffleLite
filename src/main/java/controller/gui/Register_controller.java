/**
 * 
 */
package com.s2rltx.projet.foodshuflle.application.controller;


import java.io.IOException;

import com.s2rltx.projet.foodshuflle.managerdatabase.DbManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author Apprenant
 *
 */
public class Register_controller {

	@FXML
	private TextField login;
	
	@FXML
	private TextField password;
	
	@FXML
	private TextField confirmPassword;
	
	@FXML
	private TextField email;
	
	@FXML
	private Button create;
	
	@FXML
	public void userCreated() {
		DbManager manager = new DbManager();
		
		if(userCreatedError()==false) {
			manager.creationRequest("INSERT INTO Utilisateur (U_pseudo, U_mdp, U_email) VALUES ('" + login.getText() + "','" + password.getText() + "','" + email.getText() + "')");
			goPage1();
		}
	}
	
	@FXML
	public boolean userCreatedError() {
		boolean result=false;
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(null);
		

		
		if (!password.getText().equals(confirmPassword.getText())) {
			result = true;
			alert.setContentText("Ooops, la confirmation du password est erronée");
			alert.showAndWait();
		}
		
		if ((login.getText().equals("")) || (password.getText().equals("")) || (email.getText().equals("")) ) {
			result = true;
			alert.setContentText("Ooops, veuillez remplir tous les champs");
			alert.showAndWait();
		}
		return result;
	}
	
	@FXML
	public void goPage1() {
		try {
			Stage stage = (Stage) this.create.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("../ProjetFspPage1.fxml"));

			Scene scene = this.create.getScene();
			scene = new Scene(root, 800, 600);

			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
