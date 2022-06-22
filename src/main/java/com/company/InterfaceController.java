package com.company;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InterfaceController implements Initializable {

    @FXML
    private Button B_Anamnese;
    @FXML
    private TextField t_fname,t_lname,t_wight;

    @FXML
    private DatePicker DP;
    @FXML
    private ChoiceBox<Object> ChoiceB_Condition = new ChoiceBox<>();
    @FXML
    private ListView<String> conditionListView = new ListView<>(), patMedListView = new ListView<>();



    private Stage stage;
    private Scene scene;
    private String birthdate;
    private List <String> allConditions = new ArrayList<>();
    private List <String> curentConditions = new ArrayList<>();


//Patient Data

        DB_Patient Patient = new DB_Patient(true);




    FileChooser fileChooser = new FileChooser();

    public void fileJason(ActionEvent event) {
        Stage choserStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(choserStage);
    }

    public void readEcard(ActionEvent event){
        String[] ECard = ReadEcard.readCard();

       t_fname.setText(ECard[1]);
       t_lname.setText(ECard[2]);
       //t_wight.setText(ECard[]); // Falls das gewicht im der ECard hinterlegt ist?

    }
    public void switchToInterface2 (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InterfaceStage2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToInterface1 (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InterfaceStage1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void getDate(ActionEvent event){
        LocalDate myDate =  DP.getValue();
       birthdate = String.valueOf(myDate);
        System.out.println(birthdate);
    }

    // needet for choiceBoxes and ListView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Test Data
        /////////////////////////////////////////////////////
            //Contition
        ConcurrentHashMap<String, String> testConditionsAll = new ConcurrentHashMap<>(){{ // _concurrent_hashmap for thread safety -- dunno if important
            put("O90","Wochenbettkomplikationen"); put("Z39.1","Betreuung und Untersuchung der stillenden Mutter");
            put("K27.9","Ulcus pepticum, Lokalisation nicht näher bezeichnet : Weder als akut noch als chronisch bezeichnet, ohne Blutung oder Perforation");
            put("K29.0","Akute hämorrhagische Gastritis"); put("J46","Status asthmaticus"); put("G71.0","Muskeldystrophie");
            put("J96.0","Akute respiratorische Insuffizienz, anderenorts nicht klassifiziert");
            put("I95","Hypotonie"); put("N18.9","Chronische Nierenkrankheit, nicht näher bezeichnet"); put("N95.1","Zustände im Zusammenhang mit der Menopause und dem Klimakterium");
            put("Z88.0","Allergie gegenüber Penicillin");  put("Z88.1","Allergie gegenüber anderen Antibiotika");  put("Z88.4","Allergie gegenüber Anästhetikum"); put("Z88.5","Allergie gegenüber Betäubungsmittel");  put("Z88.6","Allergie gegenüber Analgetikum");
            put("K70.4","Alkoholisches Leverversagen");
        }} ;
        // has to be a subset of testConditionsAll (only conditions allowed in patient, which are part of all conditions)
        ConcurrentHashMap<String, String> testConditionsPatient = new ConcurrentHashMap<>();
        for (Map.Entry<String, String> entry: testConditionsAll.entrySet()){
            if(entry.getKey().equals("J46") || entry.getKey().equals("G71.0") || entry.getKey().equals("N18.9") || entry.getKey().equals("I95")){ // specify which conditions you want the parient to have
                testConditionsPatient.put(entry.getKey(),entry.getValue());
            }
        }

            //Patien medication
        ConcurrentHashMap<String, String> testPatientMed = new ConcurrentHashMap<>(){{
            put("V03AZ01","Ethanol"); put("N07BC06", "Diamorphin"); put("N06BA03","Methamphetamin"); put("N01BC01","Kokain"); put("N05CD03","Flunitrazepam"); put("N05CA19", "Thiopental"); put("N02BG10","Cannabinoide"); put("A04AD10","Dronabinol (THC)"); put("N05CM01","Methaqualon"); put("N01AX11","Natriumoxybat"); put("N02AA05", "Oxycodon"); put("N06BA10","Fenetyllin");
        }};


        patMedListView.getItems().addAll(testPatientMed.values());

        allConditions.addAll(testConditionsAll.values());
        curentConditions.addAll(testConditionsPatient.values());

        ChoiceB_Condition.getItems().addAll(allConditions);
        ChoiceB_Condition.setOnAction(this::setCondition);
        conditionListView.getItems().addAll(curentConditions);


        conditionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

               curentConditions.remove(conditionListView.getSelectionModel().getSelectedItem());
               Platform.runLater(() -> conditionListView.getItems().setAll(curentConditions));

           }
       });
    }



    private void setCondition(Event event) {

        curentConditions.add(String.valueOf(ChoiceB_Condition.getValue()));
        conditionListView.getItems().setAll(curentConditions);
      // a check for duplicates are needed

        }

}
