package com.silverback.lucy.cashlog.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.Model.RepositoryFirebaseAuth;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Utils.Validation;


public class FragmentLogIn extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentLogIn";

    FragmentManager fragmentManager;

    //views
    View layoutMain;
    Button loginBtn;
    EditText emailEt, passwordEt;
    ProgressBar progressBar;

    String email, password;

    Validation validate = new Validation();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment LogIn is created");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_login, null);
        Log.d(TAG, "onCreateView: fragment logIn instantiated user interface view");

        //lock the navigation drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        fragmentManager = getActivity().getSupportFragmentManager();

        initViews();
        initToolbar();

        return layoutMain;
    }       //close the onCreateView


    public void initViews(){
        emailEt = (EditText) layoutMain.findViewById(R.id.emailEt);
        passwordEt = (EditText) layoutMain.findViewById(R.id.passwordEt);
        progressBar = (ProgressBar) layoutMain.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        loginBtn = (Button) layoutMain.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

    }       //end initViews()




    //method to a toolbar
    public void initToolbar() {
        Toolbar toolbar = layoutMain.findViewById(R.id.toolbar_login);
        toolbar.setTitle("Login");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);     //prints back icon into toolbar

        //back button click, popBackStack, hideKeyboard
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //return to previous fragment when back is pressed
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() != 0) {
                    fragmentManager.popBackStack();
                }

                hideKeyboard(v);

            }       //end onClick()
        }); //-------------------------end setNavigationOnClickListener

        //when toolbar is touched, hideKeyboard
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

    }       //end initToolbar()


    @Override
    public void onClick(final View v) {

        if(v.getId() == R.id.loginBtn){

            hideKeyboard(v);

            if((!validate.isEmpty(emailEt))&&(!validate.isEmpty(passwordEt))) {

                email = emailEt.getText().toString();
                password = passwordEt.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                RepositoryFirebaseAuth repositoryAuth = new RepositoryFirebaseAuth();
                repositoryAuth.login(email, password);


            }       //end validate
            else{
                if(validate.isEmpty(emailEt)){
                    validate.changeBG(emailEt);
                }

                if(validate.isEmpty(passwordEt)){
                    validate.changeBG(passwordEt);
                }

            }       //end else


        }       //end loginBtn

    }       //end onClick()


    @Override       //when this fragment is started
    public void onStart() {
        super.onStart();

    }       //end onStart()


    @Override       //when this fragment is resumed (is on display)
    public void onResume() {
        super.onResume();
        ((ActivityMain)getActivity()).getSupportActionBar().hide();     //remove the main toolbar
    }       //end onResume()


    @Override       //when this fragment is stopped
    public void onStop() {
        super.onStop();
        ((ActivityMain)getActivity()).getSupportActionBar().show();     //bring back main toolbar
    }       //end onStop()


    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}       //close the class