package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SQLtoJava {
    private static final String url = "jdbc:postgresql://localhost:5432/AIM";
    private static final String user = "postgres";
    private static final String password = "medProjekt";

    public SQLtoJava() {
    }

    public Connection setConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the database"+url+" is successful");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something went wrong. Please try again to connect to the Database.");
        }
        return null;
    }

    public List<DB_Patient> listAllPatients(Connection connection, boolean debug){
        ArrayList <DB_Patient> patientlist = new ArrayList<>();
        try{
            String query = "SELECT *FROM \"Patient\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                DB_Patient patient = new DB_Patient(rs.getString("Name"),rs.getString("Condition"),rs.getString("Drug"),rs.getDouble("Weight"),rs.getInt("PatientID"),rs.getInt("Age"));
                patientlist.add(patient);
                for(DB_Patient p: patientlist){
                    System.out.println(p.toStringWithoutDOB()+"\n");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patientlist;
    }

   public void listAllConditions(Connection connection, boolean debug){
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public HashMap<String,String> listAllDrugs(Connection connection, boolean debug){
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return druglist;
    }

    // TODO
    public void queryInteraction (Connection connection, String drug, String conditionOrDrug2){
        String drugFormat = "'"+drug+"'";
        String conditionOrDrug2Format = "'"+conditionOrDrug2+"'";
        try{
            String queryHint = "SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" ="+drugFormat+")" +
                    " AND (\"Interaction\".\"Drug2 | Condition\" ="+conditionOrDrug2Format+")";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryHint);
            //int columns = rs.getMetaData().getColumnCount();System.out.println(columns);
            while(rs.next()){
                System.out.println(rs.getString(1));
            }

            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int selectLastInteractionIDAndIncrement (Connection connection) {
        try {
            String queryInteractionID = "SELECT MAX(\"InteractionID\") AS max_InteractionID FROM \"Interaction\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryInteractionID);
                int interactionID = rs.getInt(1);
                return interactionID + 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return 0;
    }

    public void addInteraction(Connection connection,String drug,String conditionOrDrug2,String hint){
        String drugFormat = "'"+drug+"'";
        String conditionOrDrug2Format = "'"+conditionOrDrug2+"'";
        String hintFormat = "'"+hint+"'";
        try{
            int interactionID = selectLastInteractionIDAndIncrement(connection);
            String query = "INSERT INTO \"Interaction\" VALUES("+interactionID+","+drugFormat+","+conditionOrDrug2Format+","+hintFormat+") ON CONFLICT DO NOTHING;";
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Interaction with Drug: "+drugFormat+" and Drug/Condition: "+conditionOrDrug2Format+" was added to the Database with following Hint:"+hintFormat);
         } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        private int selectLastPatientIDAndIncrement (Connection connection) {
        try {
            String queryPatientID = "SELECT MAX(\"PatientID\") AS max_patientID FROM \"Patient\";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryPatientID);
                int patientID = rs.getInt(1);
                return patientID + 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }return 0;
    }

    public void addPatient(Connection connection, String drug, String condition, String name,   int age,  double weight){
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

    public void addPatient (Connection connection, String name,   int age,  double weight){
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
    public void addPatient(Connection connection,String name,int age,String drug,  double weight){
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

    public void addPatient(Connection connection, String condition, String name,int age,  double weight){
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

}
