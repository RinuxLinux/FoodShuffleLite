package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            String pathView = "main/view";
            Parent root = FXMLLoader.load(getClass().getResource(pathView + "/fxml/page1.fxml"));
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource(pathView + "/styles/Styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run once to check if db exists and/or is up-to-date.
     * Action taken is create and populate or nothing.
     * 
     */
    public void setupDB(){
        
        
        // TODO:
        // check si db file exists
        // si oui : 
        // // check version
        // // // // si inf a reference (?) alors update
        // // // // sinon rien
        // si non :
        // // creer la db
        // // insert data from sql script
    }
    
    public static void main(String[] args) {
        launch(args);

    }
}
