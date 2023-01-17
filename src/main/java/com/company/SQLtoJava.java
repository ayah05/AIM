package com.company;

import java.io.Console;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLtoJava {
    private static final String url = "jdbc:postgresql://localhost:5432/AIM";

    private static final String user = "postgres";
    private static Connection connection;

    public SQLtoJava() {
        try {
            String password;
            Console console = System.console();
            if(console != null){
                char[] pwd = console.readPassword("Please enter database Password: ");
                password = new String(pwd);
            }
            else{
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter database password (unmasked - sry..):");
                password = scanner.nextLine();
            }
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the database"+url+" established successfuly");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something went wrong. Please try connecting to the Database again.");
        }
    }

    public SQLtoJava(String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the database"+url+" established successfuly");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something went wrong. Please try connecting to the Database again.");
        }
    }

    /*// deprecated.
    public static Connection setConnection(){
        try {
         String password;
            Console console = System.console();
            if(console != null){
                char[] pwd = console.readPassword("Please enter database Password: ");
                password = new String(pwd);
            }
            else{
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter database password (unmasked - sry..):");
                password = scanner.nextLine();
            }
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the database"+url+" established successfuly");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something went wrong. Please try connecting to the Database again.");
        }
        return null;
    } */

    private List<String> getListFromString(String stuff){
        Scanner scan = new Scanner(stuff);
        List<String> result = new ArrayList<>();
        scan.useDelimiter(",");
        while (scan.hasNext()){
            result.add(scan.next());
        }
        return result;
    }
    public List<DB_Patient> listAllPatients(boolean debug){
        List <DB_Patient> patientlist = new ArrayList<>();
        try{
            String query = "SELECT * FROM \"Patient\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                // rs.getString("Condition"),rs.getString("Drug")
                List<String> conditions = getListFromString(rs.getString("Condition"));
                List<String> drugs = getListFromString(rs.getString("Drug"));
                DB_Patient patient = new DB_Patient(rs.getString("Firstname"),rs.getString("Lastname"),conditions,drugs,rs.getDouble("Weight"),rs.getInt("PatientID"),rs.getInt("Age"));

                patientlist.add(patient);
                if (debug) {
                    for (DB_Patient p : patientlist) {
                        System.out.println(p.toString() + "\n");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patientlist;
    }

   public HashMap<String, String> listAllConditions(boolean debug){
        try{
            HashMap<String ,String > conditionlist = new HashMap<>();
            String query = "SELECT *FROM \"Condition\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                String code = rs.getString("Code (ICD-10)");
                String name = rs.getString("Name");
                conditionlist.put(code,name);
                if(debug) {
                    for (String i : conditionlist.keySet()) {
                        System.out.println(i + "," + conditionlist.get(i));
                    }
                }
            } return conditionlist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
      return null;
   }

    public HashMap<String,String> listAllDrugs(boolean debug){
        HashMap<String ,String > druglist = new HashMap<>();
        try{
            String query = "SELECT *FROM \"Drug\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                String code = rs.getString("Code (ATC)");
                String name = rs.getString("Name");
                druglist.put(code,name);
                if(debug){
                    for (String i : druglist.keySet()) {
                        System.out.println(i + "," + druglist.get(i));
                    }
                }
            }return druglist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void queryInteraction (String drug, String conditionOrDrug2){
        String drugFormat = "'"+drug+"'";
        String conditionOrDrug2Format = "'"+conditionOrDrug2+"'";
        try{
            String queryHint = "SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" ="+drugFormat+")" +
                    " AND (\"Interaction\".\"Drug2orCond\" ="+conditionOrDrug2Format+")";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryHint);
                rs.next();
                System.out.println(rs.getString(1));

            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean ifInteractionExists (String drug, String conditionOrDrug2) {
        String drugFormat = "'" + drug + "'";
        String conditionOrDrug2Format = "'" + conditionOrDrug2 + "'";
        try {
            String queryHint = "SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" =" + drugFormat + ")" +
                    " AND (\"Interaction\".\"Drug2orCond\" =" + conditionOrDrug2Format + ")";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryHint);
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


    private int selectLastInteractionIDAndIncrement () {
        try {
            String queryInteractionID = "SELECT MAX(\"InteractionID\") AS max_InteractionID FROM \"Interaction\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryInteractionID);
                rs.next();
                int interactionID = rs.getInt(1);
                return interactionID + 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return 0;
    }

    public void addInteraction(String drug,String conditionOrDrug2,String hint){
        String drugFormat = "'"+drug+"'";
        String conditionOrDrug2Format = "'"+conditionOrDrug2+"'";
        String hintFormat = "'"+hint+"'";
        try{
            if(ifInteractionExists(drug,conditionOrDrug2)){
                System.out.println("This Interaction already exists in database");
            }else{
                Statement statement =  connection.createStatement();
                int interactionID = selectLastInteractionIDAndIncrement();
                String query = "INSERT INTO \"Interaction\" VALUES(" + interactionID + "," + drugFormat + "," + conditionOrDrug2Format + "," + hintFormat + ") ON CONFLICT DO NOTHING;";
                statement.execute(query);
                System.out.println("Interaction with Drug: " + drugFormat + " and Drug/Condition: " + conditionOrDrug2Format + " was added to the Database with following Hint:" + hintFormat);
            }
         } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int selectLastPatientIDAndIncrement () {
        try {
            String queryPatientID = "SELECT MAX(\"PatientID\") AS max_patientID FROM \"Patient\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryPatientID);
                rs.next();
                int patientID = rs.getInt(1);
                return patientID + 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return 0;
    }

    /*public void addPatient(String drug, String condition, String name,   int age,  double weight){
        String drugFormat = "'"+drug+"'";
        String conditionFormat = "'"+condition+"'";
        String nameFormat = "'"+name+"'";
        List<DB_Patient> patientlist = new ArrayList<>();
        try{
                int patientID = selectLastPatientIDAndIncrement();
                String query = "INSERT INTO \"Patient\" VALUES("+patientID+","+drugFormat+","+conditionFormat+","+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
                Statement statement = connection.createStatement();
                statement.execute(query);
                //DB_Patient patient = new DB_Patient(patientID,drug,condition,name,age,weight,patientID);
                //patientlist.add(patient);
                //System.out.println(patient.toStringWithoutDOB()+"\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addPatient ( String name,   int age,  double weight){
        String nameFormat = "'"+name+"'";
        List<DB_Patient> patientlist = new ArrayList<>();
        try{
            int patientID = selectLastPatientIDAndIncrement();
            String query = "INSERT INTO \"Patient\" VALUES("+patientID+",'','',"+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
            Statement statement = connection.createStatement();
            statement.execute(query);
            DB_Patient patient = new DB_Patient(patientID,name,age,weight);
            patientlist.add(patient);
            System.out.println(patient.toStringWithoutDOB()+"\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
    }}
    public void addPatient(String name,int age,String drug,  double weight){
            String drugFormat = "'"+drug+"'";
            String nameFormat = "'"+name+"'";
            List<DB_Patient> patientlist = new ArrayList<>();
            try{
                int patientID = selectLastPatientIDAndIncrement();
                String query = "INSERT INTO \"Patient\" VALUES("+patientID+","+drugFormat+",'',"+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
                Statement statement = connection.createStatement();
                statement.execute(query);
                DB_Patient patient = new DB_Patient(patientID,name,age,weight,drug);
                patientlist.add(patient);
                System.out.println(patient.toStringWithoutDOB()+"\n");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
             }
        }
    public void addPatient(String condition, String name,int age,  double weight){
            String conditionFormat = "'"+condition+"'";
            String nameFormat = "'"+name+"'";
            List<DB_Patient> patientlist = new ArrayList<>();
            try{
                int patientID = selectLastPatientIDAndIncrement();
                String query = "INSERT INTO \"Patient\" VALUES("+patientID+",'',"+conditionFormat+","+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
                Statement statement = connection.createStatement();
                statement.execute(query);
                //DB_Patient patient = new DB_Patient(patientID,condition,name,age,weight);
                //patientlist.add(patient);
                //System.out.println(patient.toStringWithoutDOB()+"\n");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }} */

    /**
     * add a DB_Patient object to database. An ID will be assigned, age and weight will only be added if <0,
     * if conditions or drugs are empty, an empty string will be inserted
     * @param patient a DB_Patient object of the patient
     * @return the patient object with the assigned database ID, if operation was unsucessful just the old patient
     */
    public DB_Patient /*patient_with_sqlID*/ addPatient(DB_Patient patient){
        StringBuilder conditions = new StringBuilder();
        for(String cond : patient.getConditions()) {
            conditions.append(cond).append(",");
        }
        if(conditions.length() >0){
            conditions.setLength(conditions.length()-1);
        }
        // TODO: add values from listView to patient.

        StringBuilder drugs = new StringBuilder();
        for(String drug : patient.getDrugs()) {
            drugs.append(drug).append(",");
        }
        if(drugs.length()<0);
        drugs.setLength(drugs.length()-1); // TODO what is happening here? this makes saving fail...

        String age = "";
        if(patient.getAge()>=0){
            age = "," + patient.getAge();
        }

        String weight = "";
        if (!Double.isNaN(patient.getWeight()) && patient.getWeight()>0){
            weight = String.format(Locale.ROOT,",%.1f",patient.getWeight());
        }
        // auf diese art könnte man auch die anderen spalten freiwillig machen aber denk leerer string geht eh auch, mal schauen wie viel zeit/lust wir noch haben:
        String columns = String.format("\"PatientID\", \"Drug\", \"Condition\", \"Name\"%s%s",age.isBlank()?"":", \"Age\"", weight.isBlank()?"":", \"Weight\"" );
        try{
            int patientID = selectLastPatientIDAndIncrement();
            String query = String.format("INSERT INTO \"Patient\" (%s) VALUES(%d,'%s','%s','%s','%s',%d,%f) ON CONFLICT DO NOTHING;",columns, patientID, drugs, conditions, patient.getFirstname(),patient.getLastname(),age,weight);
            Statement statement = connection.createStatement();
            statement.execute(query);
            patient.setPatID(patientID);
        } catch (SQLException throwables) {
            System.out.println("An error occured reading the database, no entry was added. Please try again or contact your system administrator.");
        }
        return patient;
    }

    /**
     * gets the interactions per patient rather inefficiently
     * @param patient the patient for whom to check interactions
     * @return a list of unique hints if successful, empty list if unseccessful
     */
    public List<String> getInteractionsForPatient(DB_Patient patient){
        List<String> result = new ArrayList<>();
        for(String drug : patient.getDrugs()) {
            String query_tmp = String.format("SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" = '%s') AND (\"Interaction\".\"Drug2orCond\" = '%cs');", drug, 0x25); // 0x25 is a fancy way of including a percent sign in a format string to create what i call a second order formatstring ^^
            try {
                // for drug/drug interaction
                for (String drug2 : patient.getDrugs()) {
                    if (!drug.equals(drug2)) {
                        String query = String.format(query_tmp, drug2);
                        //System.out.println(query);
                        ResultSet resultSet = connection.createStatement().executeQuery(query);
                        if(resultSet.next()){
                            result.add(resultSet.getString(1));
                        }
                    }
                }
                // for drug/condition interaction
                for (String cond : patient.getConditions()) {
                    String condition = cond;

                    // kinda crude, takes any ICD- starting with A00. to S99., cuts the . and the number behind...loses the vew specific ones but hey better than the other way round like it was before
                    Pattern pattern = Pattern.compile("^([A-S]\\d{2}\\x2E).*");
                    Matcher matcher = pattern.matcher(condition);
                    if (matcher.matches()){
                        condition = matcher.group().substring(0,3);
                        // System.out.println(condition);
                    }

                    String query = String.format(query_tmp, condition);
                    // System.out.println(query);
                    ResultSet resultSet = connection.createStatement().executeQuery(query);
                    if(resultSet.next()){
                        result.add(resultSet.getString(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    /*//method was implemented to check if queryDrugNameFromDrugCode method works
    public List<String> listAllDrugCodes(boolean debug){
        List<String> drugCodelist = new ArrayList<>();
        try{
            String query = "SELECT *FROM \"Drug\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                String code = rs.getString("Code (ATC)");
                drugCodelist.add(code);
                if(debug){
                    for (String cd : drugCodelist) {
                        System.out.println(cd);
                    }
                }
            }return drugCodelist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }*/

    public List<String> queryDrugNameFromDrugCode(List<String> drugCodes){
        List<String> drugNames = new ArrayList<>();
        try{
            // query NAME WHERE CODE = drugNames.add(name)
            for (String drugCode : drugCodes) {
                String query = "SELECT \"Drug\".\"Name\" FROM \"Drug\" WHERE \"Code (ATC)\" = '" + drugCode + "'";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                   String name = rs.getString("Name");
                   drugNames.add(name);
                }
            }
            for (String nm : drugNames) {
                System.out.println(nm);
            }
            return drugNames;
        }catch(SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

    public List<String> queryConditionNameFromConditionCode(List<String> condCodes){
        List<String> condNames = new ArrayList<>();
        try {
            for (String condCode : condCodes) {
                // query NAME WHERE CODE = drugNames.add(name)
                String query = "SELECT \"Condition\".\"Name\" FROM \"Condition\" WHERE \"Code (ICD-10)\" ='" + condCode + "'";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    String name = rs.getString("Name");
                    condNames.add(name);
                }
            }
            for (String nm : condNames) {
                System.out.println(nm);//sollen codeNamen ausgegeben werden oder nur als liste zurückgegeben werden?(wenn nur returnen, dann zeile 429-431 auskommentieren)
            }
            return condNames;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return condNames;
    }

    public ConcurrentHashMap<String,String> getAnaesthesiaDrugs(){
        ConcurrentHashMap<String,String> anaesthesiaDrugMap = new ConcurrentHashMap<>();
        try {
            String query = "SELECT * FROM \"Drug\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                String code = rs.getString("Code (ATC)");
                Pattern pattern = Pattern.compile("(^N01.*)|(^N02A.*)|(^M03.*)");//[NM]0[1-5].* N01,N02,N05,M03
                Matcher matcher = pattern.matcher(code);
               if (matcher.matches()) {
                    anaesthesiaDrugMap.put(code,rs.getString("Name"));
                    }
                }
            /*
            for (String i : anaesthesiaDrugMap.keySet()) {
                System.out.println(i + "," + anaesthesiaDrugMap.get(i));
            }
            */
            return anaesthesiaDrugMap;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public DB_Patient queryPatient(int patientID){
        try {
            String query = "SELECT * FROM \"Patient\" WHERE \"PatientID\" =" + patientID;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            DB_Patient patient = new DB_Patient();
            if(rs.next()) {
                // loooool ich wollte keinen leeren DB_Patient zurück sondern mit den daten xd
                List<String> conditions = getListFromString(rs.getString("Condition"));
                List<String> drugs = getListFromString(rs.getString("Drug"));
                patient = new DB_Patient(rs.getString("Firstname"),rs.getString("Lastname"),conditions,drugs,rs.getDouble("Weight"),rs.getInt("PatientID"),rs.getInt("Age"));
            }
            System.out.println(patient);
            return patient;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
