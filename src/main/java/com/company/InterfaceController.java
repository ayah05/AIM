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
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.LocalDate.now;
import static java.time.Period.between;

public class InterfaceController implements Initializable {

    @FXML
    private Button B_Anamnese,saveButton;
    @FXML
    private TextField t_fname,t_lname,t_wight;

    @FXML
    private DatePicker DP;
    @FXML
    private ChoiceBox<Object> ChoiceB_Condition = new ChoiceBox<>();
    @FXML
    private ChoiceBox<Object>ECardBox= new ChoiceBox<>(),ChoiceB_DoctorMedication= new ChoiceBox<>(),ChoiceB_PatientMedication= new ChoiceBox<>(),ChoiceB_PationLoad= new ChoiceBox<>();
    @FXML
    private ListView<String> conditionListView = new ListView<>(), patMedListView = new ListView<>(),DoctorMedicationListView = new ListView<>(),checkerListView= new ListView<>();
    @FXML
    private TextFlow interList = new TextFlow();
    @FXML
    private TextField TextF_DoctorMedication;
    private Connection connection;
    private String patientDrug;
    private  String condition;
    private  String drugDoctor,chosenPatient;

@FXML
private TextField TFCondition;

    private Stage stage;
    private Scene scene;
    private LocalDate birthdate;

    private List <String> currentDrMedication = new ArrayList<>();
    private List <String> currentConditions = new ArrayList<>();
    private List <String> allConditions = new ArrayList<>();
    private List <String> currentPatientMedication = new ArrayList<>();
    private List<DB_Patient> currentPations=new ArrayList<DB_Patient>();


//Patient Data

        DB_Patient Patient = new DB_Patient(true);
        DB_Patient testP = new DB_Patient();



    FileChooser fileChooser = new FileChooser();

    public void fileJson(ActionEvent event) {
        Stage choserStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(choserStage);//gives the chosen adress
    }


    public void readEcard(ActionEvent event, int terminal){
        DB_Patient patient = ReadEcardGeneric.readCard(terminal,false);
    }

    public void switchToInterface2 (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/InterfaceStage2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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
       birthdate = myDate;

    }

    // needet for choiceBoxes and ListView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Test Data
        /////////////////////////////////////////////////////
            //Contition
            //
           connection = SQLtoJava.setConnection();
            HashMap<String,String> conditionlist = SQLtoJava.listAllConditions(connection,false);
            /*ConcurrentHashMap<String, String> testConditionsAll = new ConcurrentHashMap<>(){{ // _concurrent_hashmap for thread safety -- dunno if important
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
        for (Map.Entry<String, String> entry: conditionlist.entrySet()){
            if(entry.getKey().equals("J46") || entry.getKey().equals("G71.0") || entry.getKey().equals("N18.9") || entry.getKey().equals("I95")){ // specify which conditions you want the parient to have
                testConditionsPatient.put(entry.getKey(),entry.getValue());
            }
        }*/
        // TODO: 25.06.2022  testP.getConditions() ist working I cant  recall the test data
        allConditions.addAll(conditionlist.values());
        ChoiceB_Condition.getItems().addAll(allConditions);
        System.out.println(testP.getConditions());
        currentConditions.addAll(testP.getConditions());
        conditionListView.getItems().addAll(currentConditions);
        ChoiceB_Condition.setOnAction(this::setCondition);
        conditionListView.getItems().addAll(currentConditions);




        //remove methode for condition ListView
        conditionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

               currentConditions.remove(conditionListView.getSelectionModel().getSelectedItem());
               Platform.runLater(() -> conditionListView.getItems().setAll(currentConditions));



           }
       });
        //remove methode for DrMedication ListView
        DoctorMedicationListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentDrMedication.remove(DoctorMedicationListView.getSelectionModel().getSelectedItem());
                Platform.runLater(() -> DoctorMedicationListView.getItems().setAll(currentDrMedication));


            }
        });

        //remove methode for Pation medikation ListView
        patMedListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentPatientMedication.remove(patMedListView.getSelectionModel().getSelectedItem());
                Platform.runLater(() -> patMedListView.getItems().setAll(currentPatientMedication));


            }
        });

        //E Card Section
        ConcurrentHashMap<String, String> eCardDevice = new ConcurrentHashMap<>(){{
            put("01","Virtual maschien"); put("02"," FH");
            put("03"," F 0.02");
        }} ;

        ECardBox.getItems().addAll(eCardDevice.values());
        ECardBox.setOnAction(this::setECardDevice);
        ECardBox.setValue("E Card Geräte");

        // TODO: 25.06.2022 integrate listAllPatients
        //////////////////////
        //Pation load Section
        //////////////////////

        //ChoiceB_PationLoad.getItems().addAll(SQLtoJava.listAllPatients(connection,false));
       ConcurrentHashMap<String, String> pationList = new ConcurrentHashMap<>(){{//Test Data
            put("01","Maria Müller"); put("02"," Klaus hansl");
            put("03"," Christian Strache");
        }} ;
        // TODO: 25.06.2022 SQLtoJAve shous be integradet but I'm too stupid for that
       // currentPations =SQLtoJava.listAllPatients(connection,false);

        ChoiceB_PationLoad.getItems().addAll(pationList.values());
        ChoiceB_PationLoad.setOnAction(this::setLoadPation);
        ChoiceB_PationLoad.setValue("Patient laden");


        //////////////////
        //Medication Doctor
        //////////////////

        //HashMap<String,String> DoctorMedicationList = SQLtoJava.listAllAnesthesieMed(connection,false);
        ConcurrentHashMap<String, String> doctorMedicationList = new ConcurrentHashMap<>(){{//Quelle: https://www.dr-gumpert.de/html/narkosemittel.html
            put("O01","Propofol");
            put("O02","Thipental");
            put("O03","Etomidat");
            put("O04","Ketamin");
            put("O05","Morphin");
            put("O06","Fentanyl");
            put("O07","Sufentanil");
            put("O08","Alfentanil");
            put("O08","Remifentanil");
            put("O08","Dipidolor");
        }} ;

        ChoiceB_DoctorMedication.getItems().addAll(doctorMedicationList.values());
        ChoiceB_DoctorMedication.setOnAction(this::set_DoctorMedication);
        DoctorMedicationListView.getItems().addAll(currentDrMedication);


        //////////////////
        //Medication Patient
        //////////////////
        HashMap<String,String> patientMedicationList=SQLtoJava.listAllDrugs(connection,false);


        ChoiceB_PatientMedication.getItems().addAll(SQLtoJava.listAllDrugs(connection,false).values());
        //currentPatientMedication=testP.getDrugs();
        currentPatientMedication.addAll(testP.getDrugs());
        patMedListView.getItems().addAll(currentPatientMedication);

        ChoiceB_PatientMedication.setOnAction(this::set_PatientMedication);
        DoctorMedicationListView.getItems().addAll(currentPatientMedication);

        //Patien medication



       patMedListView.getItems().addAll(currentPatientMedication);




    }

    private void set_PatientMedication(ActionEvent actionEvent) {

        currentPatientMedication.add(String.valueOf(ChoiceB_PatientMedication.getValue()));
        patMedListView.getItems().setAll(currentPatientMedication);
    }

    private void setLoadPation(ActionEvent actionEvent) {


        chosenPatient = String.valueOf(ChoiceB_PationLoad.getSelectionModel().getSelectedItem());
        //chosenPatient=  ChoiceB_PationLoad.getSelectionModel().getSelectedIndex();//The choosen patient as index (int)
        if (!chosenPatient.equals(0)){
             if  (!chosenPatient.equals("Patient laden") ){
                System.out.println(chosenPatient);//Port for the choosen patient as item (to String)
         }
        }
    }

    //was a try to create a seacht funktion for the DoctorMedication Textfield
    //private void get_DoctorMedication(ActionEvent actionEvent) {
      //  DoctorMedicationListView.getItems().addAll(TextF_DoctorMedication.getText());
    //}

    private void set_DoctorMedication(ActionEvent actionEvent) {
        currentDrMedication.add(String.valueOf(ChoiceB_DoctorMedication.getValue()));
        DoctorMedicationListView.getItems().setAll(currentDrMedication);
    }


    private void setECardDevice(Event event) {
        // TODO: 25.06.2022 E card daten abrufen und in Felder integrieren
       String input= String.valueOf(ECardBox.getValue());
       if (!input.equals("E Card Geräte")){
        System.out.println(input);
           System.out.println("Index = "+ECardBox.getSelectionModel().getSelectedIndex());}

    }

    private void setCondition(Event event) {
        currentConditions.add(String.valueOf(ChoiceB_Condition.getValue()));
        conditionListView.getItems().setAll(currentConditions);
      // a check for duplicates are needed

        }

        //

        @FXML
        private  void drugChecker(ActionEvent event){//Here the check button click is received and the query interaction is carried out
            // TODO: 25.06.2022 hint proccess integrieren und in  checkerListView ausgeben
      //  SQLtoJava.queryInteraction(connection,drugDoctor,condition);
      //  SQLtoJava.queryInteraction(connection,drugDoctor,patientDrug);

            //Test Code
            ConcurrentHashMap<String, String> hints = new ConcurrentHashMap<>(){{
                put("O01","Ein Aderlass wird empfohlen");
                put("O02","Empfählen Sie den Patienten eine Granderwasser Aufbereitunganlage");

            }} ;
            checkerListView.getItems().setAll(hints.values());
        }

    // TODO: 25.06.2022 save Patient needet to be conected to DB_Patient
    public void savePatient(ActionEvent actionEvent) {
        String name = t_fname.getText()+t_lname.getText();
        LocalDate now = now();
        Period age=between(birthdate,now);
        double wight = Double.parseDouble(String.valueOf(t_wight));
        System.out.println(age);
      //  DB_Patient PSave = new DB_Patient(name,currentConditions,currentPatientMedication,now,wight,age);

    }
}
