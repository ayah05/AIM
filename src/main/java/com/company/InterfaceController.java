package com.company;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



public class InterfaceController implements Initializable {
    // TODO: sein eigenes SQL-pw eingeben...
    public SQLtoJava connection = new SQLtoJava("sql");
    //public SQLtoJava connection = new SQLtoJava("0");
    //public SQLtoJava connection = new SQLtoJava("medProjekt");
    //public SQLtoJava connection = new SQLtoJava();

    // TODO: choice boxen auf standradwert stellen
    // TODO: how did i destroy saving >.<
    // TODO: aus listview zu patient adden
    // TODO: nach speichern geht load patient nimma..
    // TODO: duplikate in
    // TODO: update patient in DB
    // TODO: mayyybe geb-datum
    // TODO: mehr infos im UI
    // TODO: geburtsdatum maximal heute
    // TODO: schriftgröße in stage1?
    // TODO: ordnung in der Liste (alphabet)
    // TODO: interface2 check: soll auch die aktuellen medikamente mitvergleichen

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
        //DB_Patient testP = new DB_Patient(true);

    FileChooser fileChooser = new FileChooser();

    public void fileJson(ActionEvent event) {
        fileChooser.setTitle("Please choose a FHIR-R4-IPS in JavaScript Object Notation!");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FHIR JSON File", "*.json"));
        Stage choserStage = new Stage();
        choserStage.setMaximized(true);
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conditionListView.getItems().clear(); patMedListView.getItems().clear(); DoctorMedicationListView.getItems().clear(); checkerListView.getItems().clear();
        if(connection == null){ connection = new SQLtoJava(); }
        HashMap<String,String> conditionlist = connection.listAllConditions(false);

        allConditions.addAll(conditionlist.values());

        ChoiceB_Condition.getItems().addAll(allConditions);
        ChoiceB_Condition.setValue("Choose condition:");
        //System.out.println(testP.getConditions());
        //currentConditions.addAll(testP.getConditions());
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
        ECardBox.setValue("Choose card reader");

        //////////////////////
        //Pation load Section
        //////////////////////
        for(DB_Patient pat : connection.listAllPatients(false)){
            patientListMap.put(pat.getPatID(),  patientListMap.values().contains(pat.getFirstname()+" "+pat.getLastname()) ?
                    pat.getFirstname()+" "+pat.getLastname()+String.valueOf(pat.getPatID()) :
                    pat.getFirstname()+" "+pat.getLastname());
        }
        // interface stage 1
        ChoiceB_PationLoad.getItems().addAll(patientListMap.values());
        ChoiceB_PationLoad.setOnAction(this::setLoadPation);
        ChoiceB_PationLoad.setValue("load patient");
        // interface stage 2
      /*  ChoiceB_PationLoad2.getItems().addAll(patientListMap.values());
        ChoiceB_PationLoad2.setOnAction(this::setLoadPation2);
        ChoiceB_PationLoad2.setValue("load patient");*/
        ChoiceB_PationLoad2.setId("choice-box");
        ChoiceB_PationLoad2.getItems().addAll(patientListMap.values());
        ChoiceB_PationLoad2.setOnAction(this::setLoadPation2);
        ChoiceB_PationLoad2.setValue("load patient");
        ChoiceB_PationLoad2.getStylesheets().add("stylesheet.css");


        //////////////////
        //Medication Doctor
        //////////////////
        ConcurrentHashMap<String, String> doctorMedicationListMap = connection.getAnaesthesiaDrugs();
        //System.out.println(doctorMedicationListMap.values());
        ChoiceB_DoctorMedication.getItems().addAll(doctorMedicationListMap.values());
        ChoiceB_DoctorMedication.setValue("Choose medikation:");
        ChoiceB_DoctorMedication.setOnAction(this::set_DoctorMedication);
        //DoctorMedicationListView.getItems().addAll(doctorMedicationListMap.values());


        //////////////////
        //Medication Patient
        //////////////////
        ChoiceB_PatientMedication.getItems().addAll(connection.listAllDrugs(false).values());
        ChoiceB_PatientMedication.setValue("Choose medication:");


        // currentPatientMedication.addAll(connection.queryDrugNameFromDrugCode(testP.getDrugs()));
        patMedListView.getItems().addAll(currentPatientMedication);
        ChoiceB_PatientMedication.setOnAction(this::set_PatientMedication);
    }


    private void set_PatientMedication(ActionEvent actionEvent) {
        currentPatientMedication.add(String.valueOf(ChoiceB_PatientMedication.getValue()));
        patMedListView.getItems().setAll(currentPatientMedication);
    }

    // können easy in eine fkt refactored werden:
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
                    Patient = connection.queryPatient(results.get(0));
                    displayPatient();
                }
            }
        }
    }
    private void setLoadPation2(ActionEvent actionEvent) {
        String chosenPatient = String.valueOf(ChoiceB_PationLoad2.getSelectionModel().getSelectedItem());
        if (!chosenPatient.isBlank() && !chosenPatient.equals("null") && !chosenPatient.equals("Patient laden")){
            List<Integer> results = new ArrayList<>();
            if (patientListMap.containsValue(chosenPatient)){
                for(Map.Entry<Integer,String> entry: patientListMap.entrySet()){
                    if (Objects.equals(entry.getValue(),chosenPatient)) {
                        results.add(entry.getKey());
                    }
                }
                if (!results.isEmpty()){
                    Patient = connection.queryPatient(results.get(0));
                    //System.out.println(Patient);
                }
            }
            //System.out.println(chosenPatient);
        }
        ChoiceB_PationLoad2.getStylesheets().add("stylesheet.css");
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
                } catch (ClassCastException ignored){}
        }
    }

    private void setCondition(Event event) {

        currentConditions.add(String.valueOf(ChoiceB_Condition.getValue()));
        conditionListView.getItems().setAll(currentConditions);
      // a check for duplicates is (nur ein check) needed
        }


    @FXML
    private  void drugChecker(ActionEvent event){
        //Here the check button click is received and the query interaction is carried out
        List<String> drugCodes = new ArrayList<>();
        Set<Map.Entry<String,String>> drMedListSet = connection.listAllDrugs(false).entrySet();
        System.out.println(DoctorMedicationListView.getItems());
        for(String drugName : DoctorMedicationListView.getItems()) {
            if (connection.listAllDrugs(false).containsValue(drugName)){
                for(Map.Entry<String,String> entry: drMedListSet){
                    if (Objects.equals(entry.getValue(),drugName) && !drugCodes.contains(entry.getKey())) {
                        drugCodes.add(entry.getKey());
                    }}
            }
            System.out.println(drugCodes);
            Patient.addDrugs(drugCodes);
            //System.out.println(Patient);
            List<String> hints = connection.getInteractionsForPatient(Patient);

            checkerListView.setCellFactory(lv -> {
                ListCell<String> cell = new ListCell<>();
                Text text = new Text();
                text.wrappingWidthProperty().bind(checkerListView.widthProperty());
                cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(text));
                text.textProperty().bind(cell.itemProperty());
                return cell ;
            });

            checkerListView.getItems().setAll(hints);
        }


    }



    public void savePatient(ActionEvent actionEvent) {
                patMedListView.getItems();
        // TODO Patient.addDrugs();
        connection.addPatient(Patient);
        //System.out.println("Patient added:"+Patient);

        for(DB_Patient pat : connection.listAllPatients(false)){
            patientListMap.put(pat.getPatID(),  patientListMap.containsValue(pat.getFirstname()+" "+pat.getLastname()) ?
                                                               pat.getFirstname()+" "+pat.getLastname()+pat.getPatID() :
                    pat.getFirstname()+" "+pat.getLastname());
        }
        ChoiceB_PationLoad.getItems().clear();
        ChoiceB_PationLoad.getItems().addAll(patientListMap.values());
        ChoiceB_PationLoad.setValue("Load patient");

        ChoiceB_PationLoad.getItems().clear();
        ChoiceB_PationLoad2.getItems().addAll(patientListMap.values());
        ChoiceB_PationLoad2.setValue("Load patient");
        ChoiceB_PationLoad2.getStylesheets().add("stylesheet.css");
    }


    public void displayPatient(){
        if(Patient != null){
            // name (all in firstname for now)
            if(Patient.getFirstname()!=null && Patient.getLastname() != null){
                t_fname.setText(Patient.getFirstname());
                t_lname.setText(Patient.getLastname());
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

    public void nameListener(KeyEvent keyEvent) {
        Patient.setFirstname(t_fname.getText());
        Patient.setLastname(t_lname.getText());
    }

    public void weightListener(KeyEvent keyEvent) {
        try {

            Patient.setWeight(Double.parseDouble(t_wight.getText()));
        } catch (NumberFormatException ignored) {//t_wight.setText("Bitte . statt , eingeben!");}
        }
    }
}
