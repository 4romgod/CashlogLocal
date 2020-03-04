package com.silverback.lucy.cashlog.Fragments;

/*
 * THINGS WWE DO IN THIS FRAGMENT
 *
 * Gets name of the tab fragment that called it (Through bundle arguments)
 * Insert a toolbar, set the title of toolbar
 * Create and activate back button (Sends us to screen tab that called it)
 * Enable the save button
 *
 */

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.MyDate;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Model.DatabaseLocal.DatabaseHelper;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;
import com.silverback.lucy.cashlog.Utils.Validation;

import java.util.Date;


public class FragmentInsert extends Fragment {

    private static final String TAG = "FragmentInsert";

    //all views
    View layoutMain;
    EditText nameEt, descriptionEt, amountEt;
    String name, description;

    Drawable originalDrawable;          //original style of EditText

    float amount;
    String tabName;
    int nameEtColor, amountEtColor;

    Validation validation = new Validation();

    DatabaseHelper myDB;
    FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
    DatabaseReference fireUsersRef = fireDb.getReference("").child("users");
    DatabaseReference fireUserIdRefItem;

    FirebaseUser currentUser;
    String userId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment insert is created");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_insert, null);

        tabName = getArguments().getString("FRAG_NAME");    //get the name of prev fragment, either MoneyIn or MoneyOut
        Log.d(TAG, "onCreateView: fragment insert for"+tabName+" instantiated user interface view");
        setHasOptionsMenu(true);

        myDB = new DatabaseHelper(getActivity());

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null) {
            userId = currentUser.getUid();
            fireUserIdRefItem=fireUsersRef.child(userId).child(tabName);
        }

        initToolbar();       //enabling the toolbar
        initViews();

        return layoutMain;
    }       //close the onCreateView



    //initializing the views
    public void initViews(){
        nameEt = (EditText) layoutMain.findViewById(R.id.firstNameEt);
        amountEt = (EditText) layoutMain.findViewById(R.id.amountEt);
        descriptionEt = (EditText) layoutMain.findViewById(R.id.descriptionEt);
        originalDrawable = nameEt.getBackground();      //original background of EditText

        //when user start typing, return TV to original background
        nameEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                nameEt.setBackgroundDrawable(originalDrawable);
                return false;
            }
        });

        //when user start typing, return TV to original background
        amountEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                amountEt.setBackgroundDrawable(originalDrawable);
                return false;
            }
        });

    }       //end initViews()


    @Override
    public void onStart() {
        super.onStart();
        //lock the drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }


    @Override       //removes the main toolbar
    public void onResume() {
        super.onResume();
        ((ActivityMain) getActivity()).getSupportActionBar().hide();
    }       //end onResume()


    @Override       //returns the main toolbar when we exit this fragment
    public void onStop() {
        super.onStop();
        ((ActivityMain) getActivity()).getSupportActionBar().show();
    }       //end onStop()


    //method to ic_baseline_insert a toolbar
    public void initToolbar() {
        Toolbar toolbar = layoutMain.findViewById(R.id.toolbar_insert_fragment);
        toolbar.inflateMenu(R.menu.menu_insert_item);       //inflates menu into toolbar
        toolbar.setTitle(getString(R.string.title_insert) +" "+ tabName);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);     //prints back icon into toolbar

        //listener for the toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //return to previous fragment when back is pressed
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount() != 0){
                    fragmentManager.popBackStack();
                }

                hideKeyboard();

            }       //end onClick()
        }); //end setNavigationOnClickListener


        //listener for the toolbar save button
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //don't go forward if editText input is invalid
                validate(nameEt, nameEtColor);
                validate(amountEt, amountEtColor);

                storeItem(getInputItem(), tabName);

                hideKeyboard();

                //return fragment to previous fragment when save is pressed
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount() != 0){
                    fragmentManager.popBackStack();
                }

                return true;

            }       //end onMenuItemClick()
        });
    }       //end initToolbar()


    /**
     * Stores an item to FirebaseDatabase or Local Database
     *
     * @param item  The item object to be inserted to database
     * @param type  Whether item is moneyIn or moneyOut
     */
    public void storeItem(Item item, String type){

        if(currentUser!=null){      //user is logged in, store item in Firebase
            Log.d(TAG, "storeItem: about to push item to firebase database");
            fireUserIdRefItem.push().setValue(item);
            Log.d(TAG, "storeItem: item successfully pushed");
        }
/*
        else {                      //user not logged in, store item in locally
            if(type.equalsIgnoreCase(getString(R.string.type_money_in))){
                myDB.insertAsset(item);
            }
            else if(type.equalsIgnoreCase(getString(R.string.type_money_out))){
                myDB.insertLiability(getInputItem());
            }

        }       //end else
*/

    }       //end storeItem()


    //validate input, then change background of ET if invalid
    /**
     * @param et     EditText to be validated
     * @param color  Red color of invalid EditText
     */
    public Boolean validate(EditText et, int color){
        //don't go forward if nameEt input is invalid
        if(validation.isEmpty(et)){
            et.setBackgroundColor(Color.RED);
            color=Color.RED;
            return false;
        }
        return true;
    }       //end validate()


    //use information to create an item
    public Item getInputItem() {

        //get the times and dates
        Date date = new Date();
        MyDate theDate = new MyDate(date.getYear()+1900, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());

        //get the input values
        name = nameEt.getText().toString();
        amount = Float.parseFloat(amountEt.getText().toString());
        description = descriptionEt.getText().toString();

        //initialize the item
        Item item = new Item(name, amount, description, theDate);

        return item;
    }       //end insertData()


    private void hideKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }       //closeKeyBoard()


}       //close the FragmentInsert class
