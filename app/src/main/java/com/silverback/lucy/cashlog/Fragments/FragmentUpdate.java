package com.silverback.lucy.cashlog.Fragments;

/*
* THINGS WWE DO IN THIS FRAGMENT
*
* Get the information for the item from bundle(Item type, item object, item ID)
* Add toolbar with title, and back button. enable back button
* Get new values, update them in the database
*
* */


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Model.DatabaseLocal.DatabaseHelper;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

public class FragmentUpdate extends Fragment{

    private static final String TAG = "FragmentUpdate";

    //all views
    View layoutMain;
    EditText nameEt, descriptionEt, amountEt;
    String name, description;
    Float amount;

    DatabaseHelper myDB;

    String fragName;
    Item oldItem;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment update is created");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_update, null);
        setHasOptionsMenu(true);

        fragName = getArguments().getString("FRAG_NAME");
        oldItem = (Item) getArguments().getSerializable("ITEM");
        Log.d(TAG, "onCreateView: FragmentUpdate started for "+fragName+", item: "+oldItem.getId()+"."+oldItem.getName());

        //lock the navigation drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        myDB = new DatabaseHelper(getActivity());


        initToolbar();
        initViews();

        return layoutMain;
    }       //close the onCreateView




    //use information to create an item
    public Item getNewItem(){
        name = nameEt.getText().toString();
        amount = Float.parseFloat(amountEt.getText().toString());
        description = descriptionEt.getText().toString();

        Item newItem = new Item(fragName, name, amount, description);

        return newItem;
    }       //end insertData()


    //initializing the views
    public void initViews(){
        nameEt = (EditText) layoutMain.findViewById(R.id.firstNameEt);
        nameEt.setText(""+oldItem.getName());

        amountEt =(EditText) layoutMain.findViewById(R.id.amountEt);
        amountEt.setText(""+oldItem.getAmount());

        descriptionEt = (EditText) layoutMain.findViewById(R.id.descriptionEt);
        descriptionEt.setText(""+oldItem.getDescription());
    }       //end initViews()


    //initializing the toolbar
    public void initToolbar(){
        Toolbar toolbar = layoutMain.findViewById(R.id.toolbar_update_fragment);
        toolbar.inflateMenu(R.menu.menu_update_item);       //inflates menu into toolbar
        toolbar.setTitle(getString(R.string.title_update));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);     //prints back icon into toolbar

        //listener for the toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change fragment to previous fragment when back is pressed
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount() != 0){
                    fragmentManager.popBackStack();
                }       //end if()

                hideKeyboard();
            }       //end onClick()
        });


        toolbar.inflateMenu(R.menu.menu_insert_item);       //inflates menu into toolbar

        //listener for the toolbar save button
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                hideKeyboard();

                switch (item.getItemId()){

                    case R.id.delete: {
                        deleteItem(oldItem);
                        break;
                    }
                    case R.id.save: {
                        updateItem(getNewItem());
                        break;
                    }

                }       //end switch()

                //change fragment to previous fragment when save is pressed
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount() != 0){
                    fragmentManager.popBackStack();
                }       //end if();

                return true;
            }
        });
    }       //end initToolbar()


    public void updateItem(Item item){
        Log.d(TAG, "updateItem: updating item: "+item.getId()+"."+item.getName());
        if (fragName.equalsIgnoreCase(getString(R.string.type_money_in))) {
            myDB.updateAsset(oldItem.getId(), getNewItem());
        } else if (fragName.equalsIgnoreCase(getString(R.string.type_money_out))) {
            myDB.updateLiability(oldItem.getId(), getNewItem());
        }
    }

    public void deleteItem(Item item){
        Log.d(TAG, "deleteItem: deleting item: "+item.getId()+"."+item.getName());
        if (fragName.equalsIgnoreCase(getString(R.string.type_money_in))) {
            myDB.deleteAsset(oldItem.getId());
        } else if (fragName.equalsIgnoreCase(getString(R.string.type_money_out))) {
            myDB.deleteLiability(oldItem.getId());
        }
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


    //close the keyboard
    private void hideKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }       //closeKeyBoard()

}       //close the FragmentInsert class
