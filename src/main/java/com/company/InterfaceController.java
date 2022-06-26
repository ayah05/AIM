package com.company;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



public class InterfaceController implements Initializable {
    /// TODO: sein eigenes SQL-pw eingeben.....
    public SQLtoJava connection = new SQLtoJava("0");

    @FXML
    private Button B_Anamnese,saveButton,importJason;
    @FXML
    private TextField t_fname,t_lname,t_wight;

    @FXML
    private DatePicker DP;
    @FXML
    private ChoiceBox<Object> ChoiceB_Condition = new ChoiceBox<>();
    @FXML
    private ChoiceBox<Object>ECardBox= new ChoiceBox<>(), ChoiceB_DoctorMedication= new ChoiceBox<>(), ChoiceB_PatientMedication = new ChoiceBox<>(),ChoiceB_PationLoad= new ChoiceBox<>(),ChoiceB_PationLoad2 = new ChoiceBox<>();
    @FXML
    private ListView<String> conditionListView = new ListView<>(), patMedListView = new ListView<>(), DoctorMedicationListView = new ListView<>(),checkerListView= new ListView<>();
    @FXML
    private TextFlow interList = new TextFlow();
    @FXML
    private TextField TextF_DoctorMedication;


    private String patientDrug;
    private  String condition;
    private  String drugDoctor;

    @FXML
    private TextField TFCondition;

    private Stage stage;
    private Scene scene;
    private Date birthdate;

    FHIR_IPS_parser parse = new FHIR_IPS_parser();

    private final List <String> currentDrMedication = new ArrayList<>();
    private final List <String> currentConditions = new ArrayList<>();
    private final List <String> allConditions = new ArrayList<>();
    private final List <String> currentPatientMedication = new ArrayList<>();
    private ConcurrentHashMap <Integer, String> patientListMap = new ConcurrentHashMap<>();

//Patient Data

        DB_Patient Patient = new DB_Patient();
        DB_Patient testP = new DB_Patient(true);

    FileChooser fileChooser = new FileChooser();

    public void fileJson(ActionEvent event) {
        fileChooser.setTitle("Please choose a FHIR-R4-IPS in JavaScript Object Notation!");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FHIR JSON File", "*.json"));
        Stage choserStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(choserStage); //gives the chosen adress
        if (selectedFile != null && selectedFile.canRead()){
            try{
                Patient = parse.readIPS(selectedFile.getPath());
                displayPatient();
            }catch (Exception ignored){}
        }
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
        birthdate =  Date.from(java.time.Instant.from(DP.getValue().atStartOfDay(java.time.ZoneId.systemDefault())));
    }

    // needet for choiceBoxes and ListView
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // bis 258!
        /////////////////////////////////////////////////////
        //Test Data
        /////////////////////////////////////////////////////

        if(connection == null){ connection = new SQLtoJava(); }
        HashMap<String,String> conditionlist = connection.listAllConditions(false);
            /* ConcurrentHashMap<String, String> testConditionsAll = new ConcurrentHashMap<>(){{ // _concurrent_hashmap for thread safety -- dunno if important
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
        // adds all conditions to a list, which does nothing else...why?
        allConditions.addAll(conditionlist.values());
        // adds all conditions to choice box
        ChoiceB_Condition.getItems().addAll(allConditions);
        //System.out.println(testP.getConditions());
        currentConditions.addAll(testP.getConditions());
        // conditionListView.getItems().addAll(currentConditions);
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


        ECardBox.getItems().addAll(ReadEcardGeneric.getAllTerminalsWithCardPresent());
        ECardBox.setOnAction(this::readDataFromEcard);
        ECardBox.setValue("Kartenlesegerät wählen");

        //////////////////////
        //Pation load Section
        //////////////////////


       for(DB_Patient pat : connection.listAllPatients(false)){
           patientListMap.put(pat.getPatID(),pat.getName());
       }
       patientListMap.put(0,"Patient laden");
       ChoiceB_PationLoad.getItems().addAll(patientListMap.values());
       ChoiceB_PationLoad.setOnAction(this::setLoadPation);
       ChoiceB_PationLoad.setValue("Patient laden");

        //////////////////////
        //Pation load 2 Section
        //////////////////////

        // patientListMap.put(0,"Patient laden"); passiert das nicht eh oben schon?
        ChoiceB_PationLoad2.getItems().addAll(patientListMap.values());
        ChoiceB_PationLoad2.setOnAction(this::setLoadPationHints);
        ChoiceB_PationLoad2.setValue("Patient laden");

        //////////////////
        //Medication Doctor
        //////////////////

        ConcurrentHashMap<String, String> doctorMedicationList = connection.getAnaesthesiaDrugs();


        ChoiceB_DoctorMedication.getItems().addAll(doctorMedicationList.values());
        ChoiceB_DoctorMedication.setOnAction(this::set_DoctorMedication);
        DoctorMedicationListView.getItems().addAll(currentDrMedication);


        //////////////////
        //Medication Patient
        //////////////////
        HashMap<String,String> patientMedicationList=connection.listAllDrugs(false);
        ChoiceB_PatientMedication.getItems().addAll(connection.listAllDrugs(false).values());

        currentPatientMedication.addAll(testP.getDrugs());
        patMedListView.getItems().addAll(currentPatientMedication);

        ChoiceB_PatientMedication.setOnAction(this::set_PatientMedication);

    }

    private void setLoadPationHints(ActionEvent actionEvent) {
        String chosenPatient = String.valueOf(ChoiceB_PationLoad.getSelectionModel().getSelectedItem());
        if (!chosenPatient.isBlank() && !chosenPatient.equals("null") && !chosenPatient.equals("Patient laden")){
            // TODO Patient2 for the hint(check) funktion
            System.out.println(chosenPatient);//Port for the choosen patient as item (to String)
        }
    }

    private void set_PatientMedication(ActionEvent actionEvent) {

        currentPatientMedication.add(String.valueOf(ChoiceB_PatientMedication.getValue()));
        patMedListView.getItems().setAll(currentPatientMedication);
    }

    private void setLoadPation(ActionEvent actionEvent) {
        String chosenPatient = String.valueOf(ChoiceB_PationLoad.getSelectionModel().getSelectedItem());
        if (!chosenPatient.isBlank() && !chosenPatient.equals("null") && !chosenPatient.equals("Patient laden")){
            List<Integer> results = new ArrayList<>();
            if (patientListMap.containsValue(chosenPatient)){
                for(Map.Entry<Integer,String> entry: patientListMap.entrySet()){
                    if (Objects.equals(entry.getValue(),chosenPatient)) {
                        results.add(entry.getKey());
                    }
                }
                if (!results.isEmpty()){
                    // queries only for the first result
                    Patient = connection.queryPatient(results.get(0));
                    displayPatient();
                }
            }
            System.out.println(chosenPatient);//Port for the choosen patient as item (to String)
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


    public void scanForTerminalsWithCard(ActionEvent actionEvent) {
        ECardBox.getItems().clear();
        ECardBox.getItems().addAll(ReadEcardGeneric.getAllTerminalsWithCardPresent().isEmpty()?"Keine Karte erkannt..":ReadEcardGeneric.getAllTerminalsWithCardPresent());
    }
    private void readDataFromEcard(Event event) {
        if (!Objects.equals(ECardBox.getValue(),"Keine Karte erkannt..")||!Objects.equals(ECardBox.getValue(),"Kartenlesegerät wählen")){
           // System.out.println(ECardBox.getValue().getClass().getName());
            try{
                    ConcurrentHashMap<Integer,String> chosenTerminal= (ConcurrentHashMap<Integer,String>) ECardBox.getValue();
                    if(!chosenTerminal.isEmpty()) {
                        for(Map.Entry<Integer,String> terminal : chosenTerminal.entrySet()){
                            Patient = ReadEcardGeneric.readCard(terminal.getKey(),false);
                        }
                        displayPatient();
                    }
                } catch (ClassCastException just_do_nothing){}
        }
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

        double wight = Double.parseDouble(String.valueOf(t_wight));

      //  DB_Patient PSave = new DB_Patient(name,currentConditions,currentPatientMedication,now,wight,age);

    }


    public void displayPatient(){
        if(Patient != null){
            // name (all in firstname for now)
            if(Patient.getName() != null){
                t_fname.setText(Patient.getName());
            }
            // weight
            if (!Double.isNaN(Patient.getWeight()) && Patient.getWeight() > 0){
                t_wight.setText(String.format(Locale.ROOT,"%.1f",Patient.getWeight()));
            }
            // birthdate
            if(Patient.getDOB() != null){
                DP.setValue(Patient.getDOB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            // current medication
            currentPatientMedication.clear();
            currentPatientMedication.addAll(connection.queryDrugNameFromDrugCode(Patient.getDrugs()));
            patMedListView.getItems().addAll(currentPatientMedication);
            // current conditions
            currentConditions.clear();
            currentConditions.addAll(connection.queryConditionNameFromConditionCode(Patient.getConditions()));
            conditionListView.getItems().addAll(currentConditions);
        }
    }

    // TODO: 27.06.2022  
    public void textSave(KeyEvent keyEvent) {
        System.out.println(t_fname.getText());

    }

    public void wightlisener(KeyEvent keyEvent) {
        System.out.println(t_wight.getText());
    }
}
