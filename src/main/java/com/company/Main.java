package com.company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    public static void main(String[] args) {
         launch();
        // FHIR_IPS_parser parse = new FHIR_IPS_parser();
        // System.out.println(parse.readIPS("./src/main/IPS-example-Bundle-with-renal-disease-et-al.json"));

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(InterfaceController.class.getResource("/InterfaceStage1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("AIM");
        File fileIcon = new File("src/main/resources/Images/ICON.png");
        Image applicationIcon = new Image(fileIcon.toURI().toString());
        stage.getIcons().add(applicationIcon);
        stage.setScene(scene);
        stage.show();
    }
}
