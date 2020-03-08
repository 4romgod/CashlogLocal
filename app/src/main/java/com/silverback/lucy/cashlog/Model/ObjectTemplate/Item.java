package com.silverback.lucy.cashlog.Model.ObjectTemplate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "ITEMS")
public class Item {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "TYPE")
    String type;

    @ColumnInfo(name = "NAME")
    String name;

    @ColumnInfo(name = "AMOUNT")
    float amount;

    @ColumnInfo(name = "DESCRIPTION")
    String description;

    @Ignore
    MyDate date;


    public Item(){
        this.name = "";
        this.amount = 0;
        this.description = "";
        this.date = null;
    }

    public Item(int ID, String type, String nm, float am, String desc, MyDate date){
        this.id = ID;
        this.type = type;
        this.name = nm;
        this.amount = am;
        this.description = desc;
        this.date = date;
    }

    public Item(String type, String nm, float am, String desc, MyDate date){
        this.type = type;
        this.name = nm;
        this.amount = am;
        this.description = desc;
        this.date = date;
    }

    public Item(int id, String type, String nm, float am, String desc){
        this.id = id;
        this.type = type;
        this.name = nm;
        this.amount = am;
        this.description = desc;
    }

    public Item(String type, String nm, float am, String desc){
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
