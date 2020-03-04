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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.Model.RepositoryFirebaseAuth;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Utils.Validation;


public class FragmentSignUp extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentSignUp";

    FragmentManager fragmentManager;

    //all views
    View layoutMain;
    EditText nameEt, surnameEt, emailEt, password1Et, password2Et;
    Button registerBtn;
    ProgressBar progressBar;

    String name, surname, email, password;

    private FirebaseAuth mAuth;

    Validation validate = new Validation();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment signUp is created");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_signup, null);
        Log.d(TAG, "onCreateView: fragment signUp instantiated user interface view");

        //lock the navigation drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mAuth = FirebaseAuth.getInstance();     //init Firebase auth

        fragmentManager = getActivity().getSupportFragmentManager();

        initToolbar();
        initViews();

        return layoutMain;
    }       //close the onCreateView


    //initializing the views
    public void initViews(){
        registerBtn = (Button) layoutMain.findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        nameEt = (EditText) layoutMain.findViewById(R.id.firstNameEt);
        surnameEt = (EditText) layoutMain.findViewById(R.id.surnameEt);
        emailEt = (EditText) layoutMain.findViewById(R.id.emailEt);
        password1Et = (EditText) layoutMain.findViewById(R.id.password1Et);
        password2Et = (EditText) layoutMain.findViewById(R.id.password2Et);

        progressBar = (ProgressBar) layoutMain.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }       //end initViews()


    //initializing the toolbar
    public void initToolbar() {
        Toolbar toolbar = layoutMain.findViewById(R.id.toolbar_sign_up);
        toolbar.setTitle("Sign Up");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);     //prints back icon into toolbar

        //listener for the toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);

                //return to previous fragment when back is pressed
                if (fragmentManager.getBackStackEntryCount() != 0) {
                    fragmentManager.popBackStack();
                }

                //hideKeyboard();

            }       //end onClick()
        }); //-------------------------end setNavigationOnClickListener

        //hide keyboard when toolbar is pressed
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

    }       //end initToolbar()


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.registerBtn){

            hideKeyboard(v);

            Log.d(TAG, "onClick: register button is clicked. Validating details...");
            name = "";
            surname = "";
            email = "";
            password = "";

            if((!validate.isEmpty(emailEt)) && (!validate.isEmpty(password1Et)) && (!validate.isEmpty(password2Et))) {

                if(password1Et.getText().toString().equals(password2Et.getText().toString())) {

                    email=emailEt.getText().toString();
                    password=password1Et.getText().toString();
                    name=nameEt.getText().toString();
                    surname=surnameEt.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);

                    RepositoryFirebaseAuth repositoryAuth = new RepositoryFirebaseAuth();
                    repositoryAuth.register(email, password);

//                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "createUserWithEmail: Registration Successful! ");
//                                FirebaseUser newUser = mAuth.getCurrentUser();
//
//                                Log.d(TAG, "createUserWithEmail: UserId: " + newUser.getUid());
//
//                                //close the soft board
//                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//
//                                fragmentManager.popBackStack();
//                            }
//                            else {
//                                Log.w(TAG, "createUserWithEmail: Failure!", task.getException());
//                                Toast.makeText(getActivity(), "Registration Failed!", Toast.LENGTH_SHORT).show();
//
//                                progressBar.setVisibility(View.GONE);
//                            }
//
//                        }       //end onComplete()
//                    });

                }
                else{       //passwords don't match
                    Toast.makeText(getActivity(), "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                }

            }       //end validation

        }       //end registerBtn


    }       //close the onClick method


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        ((ActivityMain)getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onStop() {
        super.onStop();
        ((ActivityMain)getActivity()).getSupportActionBar().show();
    }


    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}       //close the class
