package com.company;

import java.sql.*;
import java.util.ArrayList;


public class SQLtoJava {
   // private static final ArrayList <DB_Patient> patientlist = new ArrayList<>();
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

    public void listingAllPatients(Connection connection){
        try{
            ArrayList <DB_Patient> patientlist = new ArrayList<>();
            String query = "SELECT *FROM \"Patient\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            DB_Patient patient;
            while (rs.next()){
                patient = new DB_Patient(rs.getString("Name"),rs.getString("Condition"),rs.getString("Drug"),rs.getDouble("Weight"),rs.getInt("PatientID"),rs.getInt("Age"));
                patientlist.add(patient);
                for(DB_Patient p: patientlist){
                    System.out.println(p.toStringWithoutDOB()+"\n");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

   /* public void listingAllConditions(Connection connection){
        try{
            ArrayList <DB_Patient> conditionlist = new ArrayList<>();
            String query = "SELECT *FROM \"Condition\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    public void queryInteraction (Connection connection, String drug, String conditionOrDrug2){
        String drugFormat = "'"+drug+"'";
        String conditionOrDrug2Format = "'"+conditionOrDrug2+"'";
        try{
            String queryHint = "SELECT \"Hint\" FROM \"Interaction\" WHERE (\"Interaction\".\"Drug\" ="+drugFormat+") AND (\"Interaction\".\"Drug2 | Condition\" ="+conditionOrDrug2Format+")";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(queryHint);
            /* int columns = rs.getMetaData().getColumnCount();
            System.out.println(columns);*/
            while(rs.next()){
                System.out.println(rs.getString(1));
            }

            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


/*
try {
        ArrayList<DB_Patient> patientlist = new ArrayList<>();
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);

        String query = "SELECT *FROM \"Patient\"";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        DB_Patient patient;
        //
        while (rs.next()){
            patient = new DB_Patient(rs.getString("Name"),rs.getString("Condition"),rs.getString("Drug"),rs.getDouble("Weight"),rs.getInt("PatientID"),rs.getInt("Age"));
            patientlist.add(patient);
            for(DB_Patient p: patientlist){
                System.out.println(p.toStringWithoutDOB()+"\n");
            }
        }
    } catch (SQLException | ClassNotFoundException throwables) {
        throwables.printStackTrace();
    }
*/

}
