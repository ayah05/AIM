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

public class InterfaceController implements Initializable {

    @FXML
    private Button B_Anamnese;
    @FXML
    private TextField t_fname,t_lname,t_wight;

    @FXML
    private DatePicker DP;
    @FXML
    private ChoiceBox ChoiceB_Condition;
    @FXML
    private ListView<String> conditionListView, patMedListView;



    private Stage stage;
    private Scene scene;
    private String birthdate;
    private List <String> allConditions = new ArrayList<>();
    private List <String> curentConditions = new ArrayList<>();


//Patient Data

        DB_Patient Patient = new DB_Patient();




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
        List<String> testConditionsAll = Arrays.asList( "O90", "Z39.1", "K27.9","K29");
        List<String> testConditionsPAtient = Arrays.asList( "J46", "G71", "J96","I95");
            //Patien medication
        List<String> testPatientMed = Arrays.asList( "Alkohol", "Heroin", "Crack","Methamphetamin","Weed");


        patMedListView.getItems().addAll(testPatientMed);

        allConditions.addAll(testConditionsAll) ;
        curentConditions.addAll(testConditionsPAtient);

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
