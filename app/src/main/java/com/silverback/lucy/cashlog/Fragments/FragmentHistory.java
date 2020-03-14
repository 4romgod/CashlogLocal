package com.silverback.lucy.cashlog.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterListViewHistory;
import com.silverback.lucy.cashlog.Model.POJO.Item;

import java.util.ArrayList;

/**
 * Shows all the user's items that exists in the daatabase
 */
public class FragmentHistory extends Fragment {
    private static final String TAG = "FragmentHistory";

    //ALL VIEWS
    View layoutMain;
    ProgressBar progressBar;

    ArrayList<Item> allData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment history is created");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_history, null);
        Log.d(TAG, "onCreateView: fragment history instantiated user interface view");

        //unlock the navigation drawer
        ((ActivityMain) getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        progressBar = (ProgressBar) layoutMain.findViewById(R.id.progressBar);

        allData = new ArrayList<>();

        AdapterListViewHistory adapter = new AdapterListViewHistory(getActivity(), R.layout.layout_custom_list_view_history, allData);
        ListView listView = layoutMain.findViewById(R.id.list_view_history);
        listView.setAdapter(adapter);

        return layoutMain;
    }       //close the onCreateView


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_insert_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}       //close the class
