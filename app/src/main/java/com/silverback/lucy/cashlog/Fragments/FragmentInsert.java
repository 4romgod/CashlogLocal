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

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.Model.POJO.MyDate;
import com.silverback.lucy.cashlog.Model.ViewModelItem;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Model.POJO.Item;
import com.silverback.lucy.cashlog.Utils.UI;
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

    }       //end onStart()


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
        setHasOptionsMenu(true);
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

                UI.hideKeyboard(v, getContext());

            }       //end onClick()
        }); //end setNavigationOnClickListener


        //listener for the toolbar save button
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(!Validation.isEmpty(nameEt) && !Validation.isEmpty(amountEt)){

                    View view = getActivity().getCurrentFocus();
                    if(view != null){
                        UI.hideKeyboard(view, getActivity());
                    }

                    //database operation
                    ViewModelItem viewModelItem = ViewModelProviders.of(getActivity()).get(ViewModelItem.class);
                    viewModelItem.insert(getInputItem());

                    //return fragment to previous fragment when save is pressed
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    if(fragmentManager.getBackStackEntryCount() != 0){
                        fragmentManager.popBackStack();
                    }

                }       //end if()

                return true;
            }       //end onMenuItemClick()
        });

    }       //end initToolbar()


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
        Item item = new Item(tabName, name, amount, description, theDate);

        return item;
    }       //end insertData()



}       //close the FragmentInsert class
