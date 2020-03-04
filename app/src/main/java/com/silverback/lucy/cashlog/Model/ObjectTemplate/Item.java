package com.silverback.lucy.cashlog.Model.ObjectTemplate;

import java.io.Serializable;


public class Item implements Serializable {

    int id;
    String name;
    float amount;
    String description;

    MyDate date;


    public Item(){
        this.name = "";
        this.amount = 0;
        this.description = "";
        this.date =null;
    }

    public Item(int ID, String nm, float am, String desc, MyDate date){
        this.id = ID;
        this.name = nm;
        this.amount = am;
        this.description = desc;
        this.date = date;
    }

    public Item(String nm, float am, String desc, MyDate date){
        this.name = nm;
        this.amount = am;
        this.description = desc;
        this.date = date;
    }

    public Item(int id, String nm, float am, String desc){
        this.id = id;
        this.name = nm;
        this.amount = am;
        this.description = desc;
    }

    public Item(String nm, float am, String desc){
        this.name = nm;
        this.amount = am;
        this.description = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return String.format("%s %s\n%s", name, amount,description);
    }
}
