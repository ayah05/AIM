package com.company;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class provides a Patient object according to the data-model of our postgreSQL-DB <p>
 *     Weight is in kilograms!
 */
public class DB_Patient {
    private List <String> conditions = new ArrayList<>();
    private List <String> drugs = new ArrayList<>();
    private String name;
    private Date dob;
    private int age = -1;
    private double weight = Double.NaN;
    private int patID_DB_PK= -1;

    public DB_Patient(){
    }
    public DB_Patient(boolean test){
       this.name = "Martha DeLarosa"; this.age = 50; this.dob = Date.from(LocalDate.of(1972, Month.MAY,1).atStartOfDay(ZoneId.of("Europe/Amsterdam")).toInstant()); this.conditions.addAll(List.of("N18.9","N95.1","Z88.0")); this.drugs.addAll(List.of("L02BG03","G02CX04","J01FA09", "B01AA03")); this.weight=63;
    }
    public DB_Patient(String name, List<String> conditions, List<String> drugs, Date dob, double weight, int age){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.dob = dob;
        this.weight = weight; this.age = age;
    }
    // overloaded constructor with less arguments #1
    public DB_Patient(String name, List<String> conditions, List<String> drugs, Date dob){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.dob = dob;
        this.age = (int)((System.currentTimeMillis() - dob.getTime()) / 3.154e+10);
    }
    // overloaded constructor with less arguments #2
    public DB_Patient(String name, List<String> conditions, List<String> drugs){
        this.name = name; this.conditions = conditions; this.drugs = drugs;
    }
    // overloaded constructor with all arguments (database primary key + age)
    public DB_Patient(String name, List<String> conditions, List<String> drugs,int age, double weight, int key, Date dob){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
        this.weight = weight; this.patID_DB_PK = key; this.dob = dob;
    }
    // overloaded constructor with more arguments (database primary key)
    public DB_Patient(String name, List<String> conditions, List<String> drugs, double weight, int key, Date dob){
        this.name = name; this.conditions = conditions; this.drugs = drugs;
        this.weight = weight; this.patID_DB_PK = key; this.dob = dob;
        this.age = (int)((System.currentTimeMillis() - dob.getTime()) / 3.154e+10);
    }

    // getters and setters:
    public List<String>  getConditions(){
        return this.conditions;
    }
    public void addConditions(List<String> cond){
        this.conditions.addAll(cond);
    }
    public List<String> getDrugs(){
        return this.drugs;
    }
    public void addDrugs(List<String> drgs){
        this.drugs.addAll(drgs);
    }
    public String getName(){
        return this.name;
    }
    public void setName(String new_name){
        this.name = new_name;
    }
    public int getAge(){
        return this.age;
    }
    public void setAge(int age){
        this.age = age;
    }
    public double getWeight(){
        return this.weight;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
    public void setPatID(int key){ // only if there is not alredy a key present:
        if(this.patID_DB_PK < 1 ){
            this.patID_DB_PK = key;
        }
    }
    public int getPatID(){ return this.patID_DB_PK;}
    public Date getDOB(){
        return this.dob;
    }
    public void setDob(Date dob){
        if(this.dob == null){
            this.dob = dob;
        }
    }

    @Override
    public String toString(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return String.format(Locale.ROOT,"\t%s, ID: %d\n\tAge: %d yrs, Weight: %.2f kg\n\tDOB: %s\n\tRist factors: %s\n\tSubstances: %s",name,patID_DB_PK,age,weight,fmt.format(dob),conditions,drugs); }
}
