package com.silverback.lucy.cashlog.Activities;

/*
* THINGS WE DO IN THIS ACTIVITY
*
* Create a toolbar
* Tie the toggle icon to functionality of drawer layout
* Initialize the app in the FragmentHome
* Enable back button to close Drawer menu if it is open
* Enable sidebar navigation
* Inflate options menu (top right of screen)
*
*
* */

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.silverback.lucy.cashlog.Fragments.FragmentAbout;
import com.silverback.lucy.cashlog.Fragments.FragmentHistory;
import com.silverback.lucy.cashlog.Fragments.FragmentHome;
import com.silverback.lucy.cashlog.Fragments.FragmentMessage;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Utils.UI;

/**
 * The first activity that the application opens (Except if there is a Splash Screen)
 */
public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener{
    private static final String TAG = "ActivityMain";


    //fragment instances
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment homeFrag = new FragmentHome();     //default fragment

    Toolbar toolbar;

    public DrawerLayout mDrawerLayout;     //container that allows drawer to be pulled out from either end
    private ActionBarDrawerToggle mToggle;      //tie functionality of drawer layout to the toolbar

    NavigationView navView;
    View headerLayout;      //header layout containing app name and icon


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Main Activity has been created");

        //get toolbar view, setSupportActionBar, create DrawerToggle, drawer listener, navViewLister
        setupToolbar();

        hideKeyBoard();     //hides keyboard when screen is touched

    }       //==================================================== close the onCreate method ===========================================


    //-----------------------------------------------------------------EVENTS-----------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Main Activity about to load the Home Fragment");

        fragmentManager.addOnBackStackChangedListener(this);

        UI.loadFragment(fragmentManager, homeFrag, R.id.layout_frame_main);         //load the default fragment
        getSupportActionBar().setTitle(getString(R.string.title_home));
        navView.setCheckedItem(R.id.Home);
    }       //end onStart()


    @Override
    protected void onStop() {
        super.onStop();
    }       //end onStop()


    @Override       //handles pressing back button
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);     //close drawer if opened
        }
        else {
            if(fragmentManager.getBackStackEntryCount()==1){
                Log.d(TAG, "onBackPressed: Closing application");
                finish();
            }
            else{
                super.onBackPressed();
            }

        }       //end else

    }       //end onBackPressed()


    @Override
    public void onBackStackChanged() {

        String entryName="";

        int count = fragmentManager.getBackStackEntryCount();
        for(int i=count-1; i>=0; i--) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);

            entryName = entry.getName();

            String stackEntryNames = "";
            stackEntryNames = stackEntryNames + entryName + "\n";

            Log.d(TAG, "onBackStackChanged: " +entry.getId()+"."+ stackEntryNames);
        }       //end for()

    }       //end onBackStackChanged()

    //========================================================EVENTS========================================================




    //------------------------------------------------------------MENU AND NAVIGATION---------------------------------------------------
    //make Toggle menu icon clickable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        if (item.getItemId()==R.id.Settings){
            Intent settingIntent = new Intent(ActivityMain.this, ActivitySettings.class);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);       //returns the selected menu item

    }       //end onOptionsItemSelected()


    @Override       //implemented by NavigationView (makes items selectable)
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;       //field that will hold fragments

        //choosing a fragment by clicking
        switch (menuItem.getItemId()) {
            case R.id.Home:
                fragment = new FragmentHome();
                getSupportActionBar().setTitle(getString(R.string.title_home));
                //navView.setItemBackground();
                break;

            case R.id.About:
                fragment = new FragmentAbout();
                getSupportActionBar().setTitle(getString(R.string.title_about));
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                //navView.setCheckedItem(R.id.About);
                break;

            case R.id.History:
                fragment = new FragmentHistory();
                getSupportActionBar().setTitle(getString(R.string.title_history));
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;

            case R.id.Share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Download CashLog App at the playStore");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an email client"));
                break;

            case R.id.Message:
                fragment = new FragmentMessage();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                //has its own toolbar
                break;

        }       //end switch()

        UI.loadFragment(fragmentManager, fragment, R.id.layout_frame_main);
        mDrawerLayout.closeDrawer(GravityCompat.START);     //close sidebar menu after item selection
        return true;
    }       //end onNavigationItemSelected()


    @Override       //inflates options menu on top right of screen
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);     //we inflate our activity with the menu_drawer
        return super.onCreateOptionsMenu(menu);

    }       //close the onCreateOptionsMenu

    //==============================================MENU AND NAVIGATION===================================================



    //------------------------------------------------------HELPING METHODS----------------------------------------

    //deals with the toobar
    public void setupToolbar(){
        Log.d(TAG, "setupToolbar: get toolbar view, setSupportActionBar, create DrawerToggle, drawer listener, navViewLister");
        //DEALS WITH THE TOOLBAR
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //prints the toggle menu icon
            actionBar.setTitle("");       //removes the title
        }

        //handle the toggle menu icon
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();        //synchronize the indicator with state of linked DrawerLayout


        //helps listen to clicks on navigation view
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        headerLayout = navView.inflateHeaderView(R.layout.layout_drawer_header);

    }       //end setupToolbar()



    public void hideKeyBoard(){
        //close the keyboard if the screen is touched
        findViewById(R.id.drawer_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UI.hideKeyboard(v, getApplicationContext());
                return false;
            }
        });

        //close the keyboard if any toolbar button is clicked
        findViewById(R.id.toolbar_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UI.hideKeyboard(v, getApplicationContext());
                return false;
            }
        });
    }
    //=======================================================HELPING METHODS================================================



}      //close the class
