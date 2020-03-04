package com.silverback.lucy.cashlog.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.R;

public class FragmentMessage extends Fragment {

    private static final String TAG = "FragmentMessage";

    //all views
    View layoutMain;
    Button submitBtn;
    EditText emailEt, messageEt;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment message is created");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_message, null);
        Log.d(TAG, "onCreateView: fragment message instantiated user interface view");

        //lock the navigation drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        initToolbar();
        initViews();

        return layoutMain;
    }       //close the onCreateView


    //initializing the views
    public void initViews(){
        emailEt = layoutMain.findViewById(R.id.emailEt);
        messageEt = layoutMain.findViewById(R.id.messageEt);

        submitBtn = layoutMain.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: submit button pressed");
                sendEmail();
            }
        });

    }       //end initViews()


    //method to initialize a toolbar
    public void initToolbar() {
        Toolbar toolbar = layoutMain.findViewById(R.id.toolbar_message_fragment);
        toolbar.setTitle("Message us");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);     //prints back icon into toolbar

        //Ob back button click, popBackStack, close the softKeyboard
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


        //hide the softKeyboard when the toolbar is touched
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

    }       //end initToolbar()



        //sending an email
    public void sendEmail(){
        String emailTo = "ebenezermathebula@gmail.com";
        String emailFrom = emailEt.getText().toString();
        String message = messageEt.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emailTo);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent, getString(R.string.choose_email_client)));
    }       //end sendEmail()


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


    //method to hide the soft keyboard
    public void hideKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


}       //end class
