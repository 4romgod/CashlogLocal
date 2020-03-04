package com.silverback.lucy.cashlog.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
        toolbarSettings.setTitle("Settings");
        toolbarSettings.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarSettings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        FragmentSettings fragSettings = new FragmentSettings();
        transaction.add(R.id.layout_frame_settings, fragSettings, "SETTINGS FRAGMENT");
        transaction.commit();
    }       //end onCreate()


}       //end class()
