package com.silverback.lucy.cashlog.Model.DatabaseLocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.silverback.lucy.cashlog.Model.POJO.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CashLogDatabase.db";

    //money in table attributes
    public static final String TABLE_NAME_MONEY_IN = "MONEY_IN";
    public static final String COL_1_A = "ID"
            ;
    public static final String COL_2_A = "NAME";
    public static final String COL_3_A = "AMOUNT";
    public static final String COL_4_A = "DESCRIPTION";
    public static final String COL_5_A = "TIME";
    public static final String COL_6_A = "DAY";
    public static final String COL_7_A = "MONTH";
    public static final String COL_8_A = "YEAR";

    //money out table attributes
    public static final String TABLE_NAME_MONEY_OUT = "MONEY_OUT";
    public static final String COL_1_L = "ID";
    public static final String COL_2_L = "NAME";
    public static final String COL_3_L = "AMOUNT";
    public static final String COL_4_L = "DESCRIPTION";
    public static final String COL_5_L = "TIME";
    public static final String COL_6_L = "DAY";
    public static final String COL_7_L = "MONTH";
    public static final String COL_8_L = "YEAR";

    //user details table attributes
    public static final String TABLE_NAME_USER_DETAILS = "USER_DETAILS";
    public static final String COL_1_USER = "ID";
    public static final String COL_2_USER = "NAME";
    public static final String COL_3_USER = "SURNAME";
    public static final String COL_4_USER = "USERNAME";
    public static final String COL_5_USER = "EMAIL";
    public static final String COL_6_USER = "PASSWORD";

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    //constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create_table_asset = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s FLOAT, %s TEXT, %s LONG, %s INTEGER, %s INTEGER, %s INTEGER)",
                TABLE_NAME_MONEY_IN, COL_1_A, COL_2_A, COL_3_A, COL_4_A, COL_5_A, COL_6_A, COL_7_A, COL_8_A);

        String sql_create_table_liability = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s FLOAT, %s TEXT, %s LONG, %s INTEGER, %s INTEGER, %s INTEGER)",
                TABLE_NAME_MONEY_OUT, COL_1_L, COL_2_L, COL_3_L, COL_4_L, COL_5_L, COL_6_L, COL_7_L, COL_8_L);

        String sql_create_table_user = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME_USER_DETAILS, COL_1_USER, COL_2_USER, COL_3_USER, COL_4_USER, COL_5_USER, COL_6_USER);

        //executing create table SQL statements
        db.execSQL(sql_create_table_asset);
        db.execSQL(sql_create_table_liability);
        db.execSQL(sql_create_table_user);

    }       //end onCreate()

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_MONEY_IN);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_MONEY_OUT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_USER_DETAILS);

        onCreate(db);
    }       //end onUpgrade()


    //------------------------------method to insert Assets-----------------------------------------
    public boolean insertAsset(Item item){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        Date date = new Date();

        ContentValues contVal = new ContentValues();
        contVal.put(COL_2_A, item.getName());
        contVal.put(COL_3_A, item.getAmount());
        contVal.put(COL_4_A, item.getDescription());
        contVal.put(COL_5_A, date.getTime());
        contVal.put(COL_6_A, date.getDate());
        contVal.put(COL_7_A, date.getMonth());
        contVal.put(COL_8_A, date.getYear()+1900);

        long results = db.insert(TABLE_NAME_MONEY_IN, null, contVal);  //returns -1 or the inserted value

        //RETURNS A (-1) If DATA IS NOT ADDED
        if(results == -1){
            return false;
        }       //close the if statement
        else{
                return true;
        }       //close the else statement

    }      //end insertAsset()


    //------------------------------method to insert Item-----------------------------------------
    public boolean insertLiability(Item lia){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        Date date = new Date();

        ContentValues contVal = new ContentValues();
        contVal.put(COL_2_L, lia.getName());
        contVal.put(COL_3_L, -lia.getAmount());
        contVal.put(COL_4_L, lia.getDescription());
        contVal.put(COL_5_L, date.getTime());
        contVal.put(COL_6_L, date.getDate());
        contVal.put(COL_7_L, date.getMonth());
        contVal.put(COL_8_L, date.getYear()+1900);

        long results = db.insert(TABLE_NAME_MONEY_OUT, null, contVal);  //returns -1 or the inserted value

        //RETURNS A (-1) If DATA IS NOT ADDED
        if(results == -1){
            return false;
        }       //close the if statement
        else{
            return true;
        }       //close the else statement

    }      //end insertAsset()


    //--------------------------------------get all assets from database-----------------------------------------
    public Cursor getAllAssets(){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        // CURSORS ARE A RESULT OF A QUERY TO GET DATA FROM A DATABASE
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_MONEY_IN, null);        //res is an instance of the cursor

        return res;
    }       //end getAllAssets()


    //---------------------------returns an Array of the Asset objects from the database------------------------------
    public ArrayList<Item> getArrayAssets(){
        Cursor rs = getAllAssets();      //we call the cursor that contains all asset objects

        ArrayList<Item> list = new ArrayList();

        if(rs!=null){
            while (rs.moveToNext()){
                int id = rs.getInt(0);
                String name = rs.getString(1);
                float amount = rs.getFloat(2);
                String description = rs.getString(3);

                //list.add(new Item(id, name, amount, description));          //put the item object in the Array
            }       //end while()

        }       //end if()

        Collections.reverse(list);
        return list;

    }       //end getArrayAssets()()


    //--------------------------------------get all liability from database-----------------------------------------
    public Cursor getAllLiability(){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        // CURSORS ARE A RESULT OF A QUERY TO GET DATA FROM A DATABASE
        Cursor res = db.rawQuery("select * from "+TABLE_NAME_MONEY_OUT, null);        //res is an instance of the cursor

        return res;
    }       //close the getAllData method


    //r----------------------------returns an Array of the Liability objects from the database------------------------
    public ArrayList getArrayLiability(){
        Cursor rs = getAllLiability();      //we call the cursor that contains all liability objects

        ArrayList list = new ArrayList();

        if(rs!=null){
            while (rs.moveToNext()){
                int id = rs.getInt(0);
                String name = rs.getString(1);
                float amount = rs.getFloat(2);
                String description = rs.getString(3);
                long time = rs.getLong(4);
                int day = rs.getInt(5);
                int month = rs.getInt(6);
                int year = rs.getInt(7);

                //list.add(new Item(id, name, amount, description));          //put the item object in the Array
            }       //end while()
        }       //end if()

        Collections.reverse(list);
        return list;

    }       //end getArrayLiability()



    //--------------------------------------update asset in database-----------------------------------------
    public void updateAsset(int id, Item item){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        ContentValues contVal = new ContentValues();
        contVal.put(COL_2_A, item.getName());
        contVal.put(COL_3_A, item.getAmount());
        contVal.put(COL_4_A, item.getDescription());

        db.update(TABLE_NAME_MONEY_IN, contVal, COL_1_A+"=?", new String[]{id+""});

    }       //close the updateData method


    //--------------------------------------update liability in database-----------------------------------------
    public void updateLiability(int id, Item item){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        ContentValues contVal = new ContentValues();
        contVal.put(COL_2_L, item.getName());
        contVal.put(COL_3_L, -item.getAmount());
        contVal.put(COL_4_L, item.getDescription());

        db.update(TABLE_NAME_MONEY_OUT, contVal, COL_1_L+"=?", new String[]{id+""});

    }       //close the updateData method



    //--------------------------------------delete money in item-----------------------------------------
    public void deleteAsset(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_MONEY_IN, COL_1_A+"=?", new String[]{id+""});
    }       //close the deleteAsset method



    //--------------------------------------delete money out item-----------------------------------------
    public void deleteLiability(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_MONEY_OUT, COL_1_L+"=?", new String[]{id+""});
    }       //close the deleteAsset method



/*

    //GETS THE ID OF THE RELEVANT NAME
    public Cursor getItemId(String name){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        String query = "select "+ COL_1 + " from " + TABLE_NAME + " where " + COL_2 + " = '" + name + "'";

        Cursor res = db.rawQuery(query, null);        //res is an instance of the cursor

        return res;
    }       //close the getItemId method

    public Cursor getItemIdL(String name){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        String query = "select "+ COL_1 + " from " + TABLE_NAME + " where " + COL_4 + " = '" + name + "'";

        Cursor res = db.rawQuery(query, null);        //res is an instance of the cursor

        return res;
    }       //close the getItemId method




    public void updateDataL(String newNameL, String newAmountL, int id, String oldNameL){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        String queryName = "update " + TABLE_NAME + " set " +COL_4 + " = '" + newNameL + "' where " + COL_1 + " = '" +id + "'" + " and " + COL_4 + " = '" + oldNameL + "'";
        String queryAmount = "update " + TABLE_NAME + " set " +COL_5 + " = '" + newAmountL + "' where " + COL_1 + " = '" +id + "'";

        db.execSQL(queryName);
        db.execSQL(queryAmount);

    }       //close the updateData method




    //METHOD INSERTS USER DETAILS INTO THE RELEVANT DATABASE TABLE
    public boolean insertUserDetails(String Name, String Username, String Email, String Password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contValueDetails = new ContentValues();
        contValueDetails.put(COL_USER_2, Name);
        contValueDetails.put(COL_USER_3, Username);
        contValueDetails.put(COL_USER_4, Email);
        contValueDetails.put(COL_USER_5, Password);

        long userDeatils = db.insert(TABLE_NAME_USER_DETAILS, null, contValueDetails);

        if(userDeatils == -1){
            return false;
        }
        else{
            return true;
        }       //close the else statement

    }       //close the insertUserDetails() method


    //THIS METHOD GETS ALL THE DATA FROM DATABASE
    public Cursor getAllUserDetails(){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        // CURSORS ARE A RESULT OF A QUERY TO GET DATA FROM A DATABASE
        Cursor details = db.rawQuery("select * from "+TABLE_NAME_USER_DETAILS, null);        //res is an instance of the cursor

        return details;
    }       //close the getAllData method


    public Cursor getDetailsId(String email){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        String query = "select "+ COL_USER_1 + " from " + TABLE_NAME_USER_DETAILS + " where " + COL_USER_4 + " = '" + email + "'";
        Cursor id = db.rawQuery(query, null);        //res is an instance of the cursor

        return id;
    }       //close the getItemId method

    public Cursor getPassword(int id){
        SQLiteDatabase db = this.getWritableDatabase();     //instance of our database

        String query = "select "+ COL_USER_5 + " from " + TABLE_NAME_USER_DETAILS + " where " + COL_USER_1 + " = '" + id + "'";
        Cursor password = db.rawQuery(query, null);

        return password;
    }       //close the get password method
*/



}       //close the database class
