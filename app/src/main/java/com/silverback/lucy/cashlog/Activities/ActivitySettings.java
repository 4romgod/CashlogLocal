package com.silverback.lucy.cashlog.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import com.silverback.lucy.cashlog.Fragments.FragmentSettings;
import com.silverback.lucy.cashlog.R;


public class ActivitySettings extends AppCompatActivity {

    Toolbar toolbarSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbarSettings = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbarSettings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbarSettings.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);
        toolbarSettings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.layout_frame_settings, new SettingsFragment(), "SETTINGS FRAGMENT");
        transaction.commit();
    }       //end onCreate()


    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}       //end class()
