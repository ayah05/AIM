package com.company;

import java.util.List;

/**
 * This class provides a Patient object according to the data-model of our postgreSQL-DB <p>
 *     Weight is in kilograms!
 */
public class DB_Patient {
    private List <String> conditions;
    private List <String> drugs;
    private String name;
    private int age = -1;
    private double weight = Double.NaN ;
    private int patID_DB_PK= -1;

    public DB_Patient(){}
    public DB_Patient(String name, List<String> conditions, List<String> drugs, int age, double weight){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
        this.weight = weight;
    }
    // overloaded constructor with less arguments #1
    public DB_Patient(String name, List<String> conditions, List<String> drugs, int age){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
    }
    // overloaded constructor with less arguments #2
    public DB_Patient(String name, List<String> conditions, List<String> drugs){
        this.name = name; this.conditions = conditions; this.drugs = drugs;
    }
    // overloaded constructor with more arguments (database primary key)
    public DB_Patient(String name, List<String> conditions, List<String> drugs,int age, double weight, int key){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
        this.weight = weight; this.patID_DB_PK = key;
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

    @Override
    public String toString(){ return String.format("\t%s\n\tAge: %d, Weight: %f\n\t%s\n\t%s",name,age,weight,conditions,drugs); }
}
