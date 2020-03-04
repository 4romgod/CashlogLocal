package com.silverback.lucy.cashlog.Model;


import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import java.util.ArrayList;

public class RepositoryFirebaseData {
    private static final String TAG = "";

    FirebaseSource firebase;

    

    public RepositoryFirebaseData() {
        this.firebase = new FirebaseSource();
    }

    public void insert(Item item, String type){
        firebase.insert(item, type);
    }

    public ArrayList<Item> getItemMoneyOut(){
        return firebase.getListFirebaseMoneyOut();
    }

}       //end class
