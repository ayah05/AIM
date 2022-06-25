package com.company;

import java.io.Console;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SQLtoJava {
    private static final String url = "jdbc:postgresql://localhost:5432/AIM";
    private static final String user = "postgres";

    public SQLtoJava() {
    }

    public static Connection setConnection(){
        try {
            String password = null;
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
    }

    public static List<DB_Patient> listAllPatients(Connection connection, boolean debug){
        ArrayList <DB_Patient> patientlist = new ArrayList<>();
        try{
            String query = "SELECT *FROM \"Patient\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                DB_Patient patient = new DB_Patient(rs.getString("Name"),rs.getString("Condition"),rs.getString("Drug"),rs.getDouble("Weight"),rs.getInt("PatientID"),rs.getInt("Age"));
                patientlist.add(patient);
                if (debug) {
                    for (DB_Patient p : patientlist) {
                        System.out.println(p.toStringWithoutDOB() + "\n");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patientlist;
    }

   public static HashMap<String, String> listAllConditions(Connection connection, boolean debug){
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

    public static HashMap<String,String> listAllDrugs(Connection connection, boolean debug){
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


    public static void queryInteraction (Connection connection, String drug, String conditionOrDrug2){
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

    private static boolean ifInteractionExists (Connection connection, String drug, String conditionOrDrug2) {
        String drugFormat = "'" + drug + "'";
        String conditionOrDrug2Format = "'" + conditionOrDrug2 + "'";
        try {
            String queryHint = "SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" =" + drugFormat + ")" +
                    " AND (\"Interaction\".\"Drug2orCond\" =" + conditionOrDrug2Format + ")";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryHint);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    private static int selectLastInteractionIDAndIncrement (Connection connection) {
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
//maybe adding a query to search if this interaction is already in the database
    public static void addInteraction(Connection connection,String drug,String conditionOrDrug2,String hint){
        String drugFormat = "'"+drug+"'";
        String conditionOrDrug2Format = "'"+conditionOrDrug2+"'";
        String hintFormat = "'"+hint+"'";
        try{
            if(ifInteractionExists(connection,drug,conditionOrDrug2)){
                System.out.println("This Interaction already exists in database");
            }else{
                Statement statement =  connection.createStatement();
                int interactionID = selectLastInteractionIDAndIncrement(connection);
                String query = "INSERT INTO \"Interaction\" VALUES(" + interactionID + "," + drugFormat + "," + conditionOrDrug2Format + "," + hintFormat + ") ON CONFLICT DO NOTHING;";
                statement.execute(query);
                System.out.println("Interaction with Drug: " + drugFormat + " and Drug/Condition: " + conditionOrDrug2Format + " was added to the Database with following Hint:" + hintFormat);
            }
         } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static int selectLastPatientIDAndIncrement (Connection connection) {
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

    public static void addPatient(Connection connection, String drug, String condition, String name,   int age,  double weight){
        String drugFormat = "'"+drug+"'";
        String conditionFormat = "'"+condition+"'";
        String nameFormat = "'"+name+"'";
        List<DB_Patient> patientlist = new ArrayList<>();
        try{
                int patientID = selectLastPatientIDAndIncrement(connection);
                String query = "INSERT INTO \"Patient\" VALUES("+patientID+","+drugFormat+","+conditionFormat+","+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
                Statement statement = connection.createStatement();
                statement.execute(query);
                DB_Patient patient = new DB_Patient(patientID,drug,condition,name,age,weight);
                patientlist.add(patient);
                System.out.println(patient.toStringWithoutDOB()+"\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addPatient (Connection connection, String name,   int age,  double weight){
        String nameFormat = "'"+name+"'";
        List<DB_Patient> patientlist = new ArrayList<>();
        try{
            int patientID = selectLastPatientIDAndIncrement(connection);
            String query = "INSERT INTO \"Patient\" VALUES("+patientID+",'','',"+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
            Statement statement = connection.createStatement();
            statement.execute(query);
            DB_Patient patient = new DB_Patient(patientID,name,age,weight);
            patientlist.add(patient);
            System.out.println(patient.toStringWithoutDOB()+"\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
    }}
    public static void addPatient(Connection connection,String name,int age,String drug,  double weight){
            String drugFormat = "'"+drug+"'";
            String nameFormat = "'"+name+"'";
            List<DB_Patient> patientlist = new ArrayList<>();
            try{
                int patientID = selectLastPatientIDAndIncrement(connection);
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

    public static void addPatient(Connection connection, String condition, String name,int age,  double weight){
            String conditionFormat = "'"+condition+"'";
            String nameFormat = "'"+name+"'";
            List<DB_Patient> patientlist = new ArrayList<>();
            try{
                int patientID = selectLastPatientIDAndIncrement(connection);
                String query = "INSERT INTO \"Patient\" VALUES("+patientID+",'',"+conditionFormat+","+nameFormat+","+age+","+weight+") ON CONFLICT DO NOTHING;";
                Statement statement = connection.createStatement();
                statement.execute(query);
                DB_Patient patient = new DB_Patient(patientID,condition,name,age,weight);
                patientlist.add(patient);
                System.out.println(patient.toStringWithoutDOB()+"\n");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }}

    /**
     * add a DB_Patient object to database. An ID will be assigned, age and weight will only be added if <0,
     * if conditions or drugs are empty, an empty string will be inserted
     * @param connection the SQL connection
     * @param patient a DB_Patient object of the patient
     * @return the patient object with the assigned database ID, if operation was unsucessful just the old patient
     */
    public static DB_Patient /*patient_with_sqlID*/ addPatient(Connection connection, DB_Patient patient){
        StringBuilder conditions = new StringBuilder();
        for(String cond : patient.getConditions()) {
            conditions.append(cond).append(",");
        }
        conditions.setLength(conditions.length()-1);

        StringBuilder drugs = new StringBuilder();
        for(String drug : patient.getDrugs()) {
            drugs.append(drug).append(",");
        }
        drugs.setLength(drugs.length()-1);

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
            int patientID = selectLastPatientIDAndIncrement(connection);
            String query = String.format("INSERT INTO \"Patient\" (%s) VALUES(%d,'%s','%s','%s'%s%s) ON CONFLICT DO NOTHING;",columns, patientID, drugs, conditions, patient.getName(),age,weight);
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
     * @param connection the database connection
     * @param patient the patient for whom to check interactions
     * @return a list of unique hints if successful, empty list if unseccessful
     */
    public static List<String> getInteractionsForPatient(Connection connection,DB_Patient patient){
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
                        System.out.println(condition);
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
}
