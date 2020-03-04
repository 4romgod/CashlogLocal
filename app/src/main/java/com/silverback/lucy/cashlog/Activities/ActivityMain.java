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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silverback.lucy.cashlog.Fragments.FragmentAbout;
import com.silverback.lucy.cashlog.Fragments.FragmentHistory;
import com.silverback.lucy.cashlog.Fragments.FragmentHome;
import com.silverback.lucy.cashlog.Fragments.FragmentLogIn;
import com.silverback.lucy.cashlog.Fragments.FragmentMessage;
import com.silverback.lucy.cashlog.Fragments.FragmentSignUp;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.MyDate;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Model.DatabaseLocal.DatabaseHelper;

import java.util.ArrayList;


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

    DatabaseHelper myDB;

    //Firebase instances
    FirebaseAuth mAuth;                                 //entry point for all server-side Firebase Auth actions
    FirebaseAuth.AuthStateListener mAuthListener;       //Listener called when there is a change in the Auth state
    FirebaseUser currentUser;                           //allows you to manipulate the profile of a user

    public ArrayList allData = new ArrayList<>();      //holds all the items from local or firebase database

    //views
    TextView emailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Main Activity has been created");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //get toolbar view, setSupportActionBar, create DrawerToggle, drawer listener, navViewLister
        setupToolbar();

        Log.d(TAG, "onCreate: inflating the headerView to the navigation drawer");
        View headerLayout = navView.inflateHeaderView(R.layout.layout_drawer_header);
        emailTv = headerLayout.findViewById(R.id.emailEt);

        //close the keyboard if the screen is touched
        findViewById(R.id.drawer_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        //close the keyboard if any toolbar button is clicked
        findViewById(R.id.toolbar_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });


    }       //==================================================== close the onCreate method ===========================================


    //-----------------------------------------------------------------EVENTS-----------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();

        //checks currentUser then hide relevant drawer menu items
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //the new state of the current user
                currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if(currentUser != null){
                    Log.d(TAG, "onAuthStateChanged: user email: "+currentUser.getEmail());
                    emailTv.setText(currentUser.getEmail());

                    hideMenuItem(R.id.SignUp);
                    hideMenuItem(R.id.LogIn);
                    showMenuItem(R.id.Logout);

                    GetFirebaseData data = new GetFirebaseData();
                    data.start();
                }
                else {
                    Log.d(TAG, "onAuthStateChanged: no user is logged in");
                    emailTv.setText("");

                    hideMenuItem(R.id.Logout);
                    showMenuItem(R.id.SignUp);
                    showMenuItem(R.id.LogIn);
                }
            }       //end onAuthStateChanged()
        };

        mAuth = FirebaseAuth.getInstance();     //instance of Firebase authentication
        mAuth.addAuthStateListener(mAuthListener);      //onStart, check who is logged in

        fragmentManager.addOnBackStackChangedListener(this);

        Log.d(TAG, "onStart: Main Activity about to load the Home Fragment");
        loadFragment(homeFrag);         //load the default fragment
        getSupportActionBar().setTitle(getString(R.string.title_home));
        navView.setCheckedItem(R.id.Home);

    }       //end onStart()


    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }       //end onStop()


    @Override       //handles pressing back button
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()==1){
            Log.d(TAG, "onBackPressed: Closing application");
            finish();
            return;
        }

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);     //close drawer if opened
        }
        else {
            super.onBackPressed();
        }

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

            case R.id.LogIn:
                fragment = new FragmentLogIn();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                //has its own toolbar
                break;

            case R.id.SignUp:
                fragment = new FragmentSignUp();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                //has its own toolbar
                break;

            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                break;

        }       //end switch()

        loadFragment(fragment);
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
    //loading a fragment into the frame layout
    /**
     * @param fragment     the fragment to be loaded
     */
    public void loadFragment(Fragment fragment){

        //change fragment to selected
        if(fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.layout_frame_main, fragment);
            transaction.addToBackStack(""+fragment.toString());
            //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }       //end loadFragment()



    //deals with the toobar
    public void setupToolbar(){
        Log.d(TAG, "setupToolbar: get toolbar view, setSupportActionBar, create DrawerToggle, drawer listener, navViewLister");
        //DEALS WITH THE TOOLBAR
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");       //removes the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //prints the toggle menu icon

        //handle the toggle menu icon
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();        //synchronize the indicator with state of linked DrawerLayout


        //helps listen to clicks on navigation view
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

    }       //end setupToolbar()



    //method to help close the keyboard
    public void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }       //closeKeyBoard()


    public void hideMenuItem(int item){
        Menu navMenu = navView.getMenu();
        navMenu.findItem(item).setVisible(false);
    }       //end hideMenuItem()

    public void showMenuItem(int item){
        Menu navMenu = navView.getMenu();
        navMenu.findItem(item).setVisible(true);
    }


    public FirebaseAuth getmAuth(){
        return mAuth;
    }

    //=======================================================HELPING METHODS================================================



    //------------------------------------------------------GET FIREBASE DATA------------------------------------------------
    class GetFirebaseData extends Thread{

        private static final String TAG = "GetFirebaseData";

        @Override
        public void run() {

            if(currentUser!=null) {
                String userID = currentUser.getUid();

                FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
                DatabaseReference fireRef = fireDb.getReference("").child("users").child(userID);

                Log.d(TAG, "run: ABOUT TO GET FIREBASE DATA...");
                fireRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        DataSnapshot dataSnapMoneyIn  = dataSnapshot.child(getString(R.string.type_money_in));
                        DataSnapshot dataSnapMoneyOut = dataSnapshot.child(getString(R.string.type_money_out));

                        ArrayList<DataSnapshot> listSnaps = new ArrayList<>();
                        listSnaps.add(dataSnapMoneyIn);
                        listSnaps.add(dataSnapMoneyOut);

                        ArrayList<Item> listMoneyIn = new ArrayList<>();
                        ArrayList<Item> listMoneyOut = new ArrayList<>();

                        for(int s=0; s<listSnaps.size(); s++){                          //loops through the allData of dataSnapshot

                            int counter=0;
                            for(DataSnapshot itemSnap: listSnaps.get(s).getChildren()){     //loops through a particular dataSnapshot
                                String key = itemSnap.getKey();
                                String name = itemSnap.child("name").getValue(String.class);
                                float amount = itemSnap.child("amount").getValue(float.class);
                                String description = itemSnap.child("description").getValue(String.class);
                                MyDate date = itemSnap.child("date").getValue(MyDate.class);

                                Item item = new Item(name, amount, description, date);

                                if(s==0){       //first dataSnapShot
                                    listMoneyIn.add(item);
                                    Log.i(TAG, "onDataChange: moneyIn: "+listMoneyIn.get(counter).toString());
                                    counter++;
                                }
                                else if(s==1){      //2nd dataSnapShot
                                    listMoneyOut.add(item);
                                    Log.i(TAG, "onDataChange: moneyOut: "+item.toString());
                                }

                            }       //end nested for()

                        }       //end big for(listSnaps)

                        allData.add(listMoneyIn);
                        allData.add(listMoneyOut);

                    }       //end onDataChange()

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });     //end listener

            }       //end if(currentUser)

        }       //end run()

    }       //end GetFirebaseData

    //=======================================================GET FIREBASE DATA====================================================




}      //close the class
