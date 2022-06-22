package com.company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
      ////////////
      //  Wenn die GUI nervt einfach mit // (oder /* */ xd) launch(); auskommentieren :D
        // glaub am besten, wenn jeder seinen bereich hat dann müsste git vlt funktionieren ^^
      ///////////

        /* flo test */
        launch();


        /* mo test
        FHIR_IPS_parser parse = new FHIR_IPS_parser();
        System.out.println(parse.readIPS("./src/main/IPS-example-Bundle-with-renal-disease-et-al.json"));

        System.out.println(new DB_Patient(true)); // for testing the test DB_Patient: works just as well as readIPS on our example IPS :D

        // shows all terminals:
        System.out.println("All terminals: "+ ReadEcardGeneric.getAllTerminals());
        // shows all terminals with card present (maybe easier just to make the user coose from these)
        System.out.println("Terminals with card present: " + ReadEcardGeneric.getAllTerminalsWithCardPresent());
        // reads the ecard in terminal 0, true just puts out debug-info
        System.out.println(ReadEcardGeneric.readCard(0,true));
        */



        /* ayah test
            SQLtoJava test = new SQLtoJava();
            Connection connection = test.setConnection();
            test.listingAllPatients(connection);
         */
    }




    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(InterfaceController.class.getResource("/InterfaceStage1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Color.WHITE);
        stage.setTitle("AIM");

        File fileIcon = new File("src/main/resources/Images/ICON.png");
        Image applicationIcon = new Image(fileIcon.toURI().toString());
        stage.getIcons().add(applicationIcon);
        stage.setScene(scene);
        stage.show();
    }
}
