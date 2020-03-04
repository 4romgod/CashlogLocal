package com.silverback.lucy.cashlog.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.R;


/**
 * Describes the developer. And provides a way to contact us
 */
public class FragmentAbout extends Fragment {

    private static final String TAG="FragmentAbout";

    //all Views
    View layoutMain;        //the view for the main layout


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment about is created");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_about, null);
        Log.d(TAG, "onCreateView: fragment about instantiated user interface view");

        //unlock the navigation drawer
        ((ActivityMain)getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        return layoutMain;
    }       //close the onCreateView



}       //close the class
