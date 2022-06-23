package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

public class SQLtoJava {
    private static String url = "jdbc:postgresql://localhost:5432/AIM";
    private static String user = "postgres";
    private static String password = "medProjekt";
    private static Connection connection;

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
