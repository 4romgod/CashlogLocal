package com.silverback.lucy.cashlog.Model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.MyDate;
import com.silverback.lucy.cashlog.R;

import java.util.ArrayList;

/**
 * Handles interactions with Firebase Database and Authentication
 */
public class FirebaseSource {
    private static final String TAG = "FirebaseSource";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    ArrayList<ArrayList<Item>> allData = new ArrayList<>();

    public FirebaseAuth getFirebaseAuth(){
        return mAuth;
    }


    //------------------------------------------------------------- AUTHENTICATION --------------------------------------------------------
    /**
     * Takes email and password and create a new User
     * @param email
     * @param password
     */
    public void register(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail: Registration Successful! ");
                }
                else {
                    Log.w(TAG, "createUserWithEmail: Failure!", task.getException());
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });

    }       //end register()


    /**
     * Takes email and password and login an existing User
     * @param email
     * @param password
     */
    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail: Login Successful!");
                }       //end if()
                else {
                    Log.w(TAG, "signInWithEmail: failure!", task.getException());

                }       //end else()
            }
        });
    }       //end login()


    /**
     * Signs out a signed in user
     */
    public void logout(){
        mAuth.signOut();
    }       //end logout()

    //========================================================= AUTHENTICATION ==========================================================



    //--------------------------------------------------------- DATABASE -----------------------------------------------------------------

    /**
     * Stores an item to FirebaseDatabase
     *
     * @param item  The item object to be inserted to database
     * @param type  Whether item is moneyIn or moneyOut
     */
    public void insert(Item item, String type){

        if(currentUser!=null){      //user is logged in, store item in Firebase
            Log.d(TAG, "storeItem: about to push item to firebase database");

            DatabaseReference fireUsersRef = firebaseDatabase.getReference("").child("users").child(currentUser.getUid()).child(String.valueOf(type));
            fireUsersRef.push().setValue(item);

            Log.d(TAG, "storeItem: item successfully pushed to "+type);
        }

    }       //end storeItem()


    public ArrayList<Item> getListFirebaseMoneyIn(){

        final ArrayList<Item> listMoneyIn = new ArrayList<>();

        if(currentUser != null) {
            String userID = currentUser.getUid();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("").child("users").child(userID).child(String.valueOf(R.string.type_money_in));

            Log.d(TAG, "fetchData(): ABOUT TO GET DATA FROM FIREBASE...");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        //values of the objects from firebase
                        String name = dataSnapshot1.child("name").getValue(String.class);
                        float amount = dataSnapshot1.child("amount").getValue(float.class);
                        String description = dataSnapshot1.child("description").getValue(String.class);
                        MyDate date = dataSnapshot1.child("date").getValue(MyDate.class);

                        //initialize the object
                        Item currentItem = new Item(name, amount, description, date);
                        Log.i(TAG, "fetchData: item: " + currentItem.toString());    // + "  date: " + currentItem.getDate().toString());


                        listMoneyIn.add(0, currentItem);     //add the object in the list

                    }           //end for()

                }       //end onDataChange()

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }       //end if(currentUser)

        return listMoneyIn;
    }


    public ArrayList<Item> getListFirebaseMoneyOut(){

        final ArrayList<Item> listMoneyOut = new ArrayList<>();

        if(currentUser != null) {
            String userID = currentUser.getUid();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("").child("users").child(userID).child(String.valueOf(R.string.type_money_out));

            Log.d(TAG, "fetchData(): ABOUT TO GET DATA FROM FIREBASE...");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        //values of the objects from firebase
                        String name = dataSnapshot1.child("name").getValue(String.class);
                        float amount = dataSnapshot1.child("amount").getValue(float.class);
                        String description = dataSnapshot1.child("description").getValue(String.class);
                        MyDate date = dataSnapshot1.child("date").getValue(MyDate.class);

                        //initialize the object
                        Item currentItem = new Item(name, amount, description, date);
                        Log.i(TAG, "fetchData: item: " + currentItem.toString());    // + "  date: " + currentItem.getDate().toString());


                        listMoneyOut.add(0, currentItem);     //add the object in the list

                    }           //end for()

                }       //end onDataChange()

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }       //end if(currentUser)

        return listMoneyOut;
    }


    //====================================================================== DATABASE =============================================================

}       //end class
