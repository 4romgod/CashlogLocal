package com.silverback.lucy.cashlog.Fragments;

/*
* THINGS WWE DO IN THIS FRAGMENT
*
* Create 2 tabs for MoneyIn and MoneyOut
* Create Floating Button to lead us to the FragmentInsert (with embedded arguments)
*
* */


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterTabs;

import java.util.ArrayList;


/**
 * This is the homepage which holds MoneyIn and MoneyOut tabs
 */
public class FragmentHome extends Fragment  {

    private static final String TAG = "FragmentHome";

    View layoutMain;

    TabLayout tabLayout;
    int tabPosition;    //position of the visible tab

    ArrayList<Fragment> mFragments = new ArrayList<>();

    FloatingActionButton fab;

    /**
     * It holds a fragment along with the title of the Fragment
     * @param name Title of the fragment to be opened by: {@link FragmentHome}
     * @return Returns a fragment bundled with its Title as String
     */
    public static Fragment newInsertFragment(String name){
        Fragment fragment = new FragmentInsert();

        Bundle args = new Bundle();
        args.putString("FRAG_NAME", name);
        fragment.setArguments(args);

        return fragment;
    }       //end newUpdateFragment()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment home is created");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView: fragment home instantiated user interface view");

        layoutMain = inflater.inflate(R.layout.fragment_home, null);

        //unlock the navigation drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        mFragments.add(new FragmentMoneyIn());
        mFragments.add(new FragmentMoneyOut());

        createTabs();       //method creates tab, and activate floating button
        floatingButton();

//        //initialize database with values
//        DatabaseHelper myDB = new DatabaseHelper(getActivity());
//        myDB.insertLiability(new Item("Grocery", "R1000", "I bought some groceries"));
//        myDB.insertLiability(new Item("Takeaway", "R200", "I bought some takeaways"));
//        myDB.insertLiability(new Item("Rent", "R4000", "I paid rent"));

        return layoutMain;
    }       //end onCreateView()


    /**
     * method to create tabs, and activate Floating button,
     */

    public void createTabs(){
        //Log.d(TAG, "createTabs: create tabs, init viewpager, set adapter");

        //create Tabs
        tabLayout = (TabLayout) layoutMain.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.type_money_in)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.type_money_out)));

        //view pager holds the tabs fragments
        final ViewPager viewPager = layoutMain.findViewById(R.id.view_pager);
        AdapterTabs tabsAdapter = new AdapterTabs(getChildFragmentManager(), mFragments);

        //listens for a swipe to switch to another tab, then changes tab focus
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(tabsAdapter);

        //default methods for the tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override       //gives us the currently selected tab
            public void onTabSelected(TabLayout.Tab tab) {
                if(viewPager != null) {
                    viewPager.setCurrentItem(tab.getPosition());
                    tabPosition = viewPager.getCurrentItem();        //position of the visible tab
                    Log.d(TAG, "onTabSelected: Position = "+tabPosition);
                }       //end if()
            }       //end onTabSelected()

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}

        });         //end creating Tabs

    }       //end createTabs()


    /**
     * Activates the floating button
     */
    public void floatingButton(){

        //activate the Floating Button
        fab = layoutMain.findViewById(R.id.floatingActionButton);

        //enable the floating button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;

                //chooses fragment to change into
                if(tabLayout.getSelectedTabPosition()==0){
                    fragment = FragmentHome.newInsertFragment(getString(R.string.type_money_in));
                }
                else{
                    fragment = FragmentHome.newInsertFragment(getString(R.string.type_money_out));
                }

                //change fragment
                if(fragment != null){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.layout_frame_main, fragment);
                    transaction.addToBackStack(fragment.toString());
                    transaction.commit();
                }       //end if()

            }       //end onClick()

        });         //end floating button

    }       //end floatingButton()




}       //close the class



/*
    //THIS IS THE METHOD TO SHOW THE DATABASE INFORMATION IN AN ALERT DIALOGUE
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);        //can be canceled after it is user
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }       //close the showMessage method

*/



