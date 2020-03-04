package com.silverback.lucy.cashlog.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.silverback.lucy.cashlog.R;

public class FragmentSettings extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }       //end onCreate()


}       //end class
