package com.company;

/**
 * This class provides a Patient object according to the data-model of our postgreSQL-DB <p>
 *     Weight is in kilograms!
 */
public class DB_Patient {
    private String[] conditions;
    private String[] drugs;
    private String name;
    private int age;
    private double weight;
    private int patID_DB_PK;

    public DB_Patient(){}
    public DB_Patient(String name, String[] conditions, String[] drugs, int age, double weight){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
        this.weight = weight;
    }
    // overloaded constructor with less arguments #1
    public DB_Patient(String name, String[] conditions, String[] drugs, int age){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
    }
    // overloaded constructor with less arguments #2
    public DB_Patient(String name, String[] conditions, String[] drugs){
        this.name = name; this.conditions = conditions; this.drugs = drugs;
    }
    // overloaded constructor with more arguments (database primary key)
    public DB_Patient(String name, String[] conditions, String[] drugs,int age, double weight, int key){
        this.name = name; this.conditions = conditions; this.drugs = drugs; this.age = age;
        this.weight = weight; this.patID_DB_PK = key;
    }
    public String[] getConditions(){
        return this.conditions;
    }
    public void setConditions(String[] cond){
        if(this.conditions.length<1){
            this.conditions = cond;
        }else{ // if there are already conditions, add the new ones instead of overriding old ones.
            int new_len = this.conditions.length + cond.length;
            String[] result = new String[new_len];
            System.arraycopy(this.conditions, 0, result, 0, this.conditions.length);
            System.arraycopy(cond, 0, result, this.conditions.length, cond.length);
            this.conditions = result;
        }
    }
    public String[] getDrugs(){
        return this.drugs;
    }
    public void setDrugs(String[] drgs){
        if(this.drugs.length<1){
            this.drugs = drgs;
        }else{ // if there are already drugs, add the new ones instead of overriding old ones.
            int new_len = this.drugs.length + drgs.length;
            String[] result = new String[new_len];
            System.arraycopy(this.drugs, 0, result, 0, this.drugs.length);
            System.arraycopy(drgs, 0, result, this.drugs.length, drgs.length);
            this.drugs = result;
        }
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
}
