package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class InterfaceController {

    @FXML
    private Button B_Anamnese;
    @FXML
    private TextField t_fname,t_lname,t_wight;

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

}
