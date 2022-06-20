package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class InterfaceController {

    @FXML
    private Button B_Anamnese;
    @FXML
    private TextField t_fname,t_lname,t_wight;

    private Stage stage;
    private Scene scene;



    FileChooser fileChooser = new FileChooser();

    public void fileJason(ActionEvent event) {
        Stage choserStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(choserStage);
    }

    public void readEcard(ActionEvent event){
        String[] ECard = ReadEcard.readCard();

       t_fname.setText(ECard[1]);
       t_lname.setText(ECard[2]);

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

}
