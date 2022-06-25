package com.company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.sql.*;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        ////////////
        //  Wenn die GUI nervt einfach mit // (oder /* */ xd) launch(); auskommentieren :D
        // glaub am besten, wenn jeder seinen bereich hat dann müsste git vlt funktionieren ^^
        ///////////

        /* flo test

        */
        launch();

        /* mo test
        String query_tmp = String.format("SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" = '%s') AND \"Interaction\".\"Drug2orCond\" = '%cs');", "N05AA5", 0x25);
        System.out.println(String.format(query_tmp, "drug2"));

        FHIR_IPS_parser parse = new FHIR_IPS_parser();
        DB_Patient martha = parse.readIPS("./src/main/IPS-example-Bundle-with-renal-disease-et-al.json");
        //System.out.println(martha);
        List<String> hintsForMarta = SQLtoJava.getInteractionsForPatient(SQLtoJava.setConnection(), martha);
        System.out.println("Yay hints:");
        for (String hint : hintsForMarta){
            System.out.println(hint);
        }*/
        /* System.out.println(new DB_Patient(true)); // for testing the test DB_Patient: works just as well as readIPS on our example IPS :D
        // shows all terminals:
        System.out.println("All terminals: "+ ReadEcardGeneric.getAllTerminals());
        // shows all terminals with card present (maybe easier just to make the user coose from these)
        System.out.println("Terminals with card present: " + ReadEcardGeneric.getAllTerminalsWithCardPresent());
        // reads the ecard in terminal 0, true just puts out debug-info
        System.out.println("Created DB_Patient:\n"+ReadEcardGeneric.readCard(0,true));
        */

        /* ayah test
            //deprecated, class is now satic.

            SQLtoJava.addPatient(connection,"K71","Maurice Leon",55,67.8);
            SQLtoJava.addPatient(connection,"C01BD04","I48","Lara Mueller",70,60.1);
            SQLtoJava.addPatient(connection,"Jasmin Shawki",20,66.3);
            SQLtoJava.addPatient(connection,"Sam Lewis",33,"J01FA09",60.2);
            Connection connection = SQLtoJava.setConnection();
            SQLtoJava.addInteraction(connection,"tDrug1","tDrug2","someHint");
            SQLtoJava.addInteraction(connection,"M03AB01","G72","smth");*/


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
