package com.silverback.lucy.cashlog.Fragments;

/*
* THINGS WWE DO IN THIS FRAGMENT
*
* Get the information for the item from bundle(Item type, item object, item ID)
* Add toolbar with title, and back button. enable back button
* Get new values, update them in the database
*
* */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.Model.ViewModelItem;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Model.POJO.Item;
import com.silverback.lucy.cashlog.Utils.UI;
import com.silverback.lucy.cashlog.Utils.Validation;

public class FragmentUpdate extends Fragment{
    private static final String TAG = "FragmentUpdate";

    ViewModelItem viewModelItem;

    //all views
    View layoutMain;
    EditText nameEt, descriptionEt, amountEt;

    String name, description;
    Float amount;

    String typeFrag;    //also gives us the TYPE of the Item
    Item oldItem;       //state of Item before updating


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment update is created");
    }       //end onCreate()


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_update, null);

        typeFrag = getArguments().getString("FRAG_TYPE");
        oldItem = (Item) getArguments().getSerializable("ITEM");
        Log.d(TAG, "onCreateView(): FragmentUpdate started for "+ typeFrag +", itemID:"+oldItem.getId()+". Name:"+oldItem.getName());

        viewModelItem = ViewModelProviders.of(getActivity()).get(ViewModelItem.class);

        initToolbar();
        initViews();

        return layoutMain;
    }       //close the onCreateView


    //use information to create an item
    public Item getNewItem(){
        name = nameEt.getText().toString();
        amount = Float.parseFloat(amountEt.getText().toString());
        description = descriptionEt.getText().toString();

        Item newItem = new Item(typeFrag, name, amount, description);
        newItem.setId(oldItem.getId());

        return newItem;
    }       //end insertData()


    @Override
    public void onStart() {
        super.onStart();
        //lock the navigation drawer
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
        setHasOptionsMenu(true);
        Toolbar toolbar = layoutMain.findViewById(R.id.toolbar_update_fragment);
        toolbar.inflateMenu(R.menu.menu_update_item);       //inflates menu into toolbar
        toolbar.setTitle(getString(R.string.title_update));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);     //prints back icon into toolbar

        //listener for the toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                UI.popBackStack(fragmentManager);       //change fragment to previous fragment
                UI.hideKeyboard(v, getContext());
            }       //end onClick()
        });


        //listener for the toolbar save button
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.delete: {
                        //database operation
                        viewModelItem.delete(oldItem);
                        break;
                    }
                    case R.id.save: {
                        if(!Validation.isEmpty(nameEt) && !Validation.isEmpty(amountEt)) {
                            View view = getActivity().getCurrentFocus();
                            if (view != null) {
                                UI.hideKeyboard(view, getActivity());
                            }

                            //database operation
                            viewModelItem.update(getNewItem());
                        }
                        break;
                    }

                }       //end switch()

                //change fragment to previous fragment when save is pressed
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                UI.popBackStack(fragmentManager);

                return true;
            }
        });
    }       //end initToolbar()


}       //close the FragmentInsert class
