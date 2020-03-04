package com.silverback.lucy.cashlog.Fragments;

/*
 * THINGS WWE DO IN THIS FRAGMENT
 *
 * Same as FragmentMoneyIn information
 *
 * */

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterListView;
import com.silverback.lucy.cashlog.Model.RepositoryFirebaseData;
import com.silverback.lucy.cashlog.Model.DatabaseLocal.DatabaseHelper;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import java.util.ArrayList;

public class FragmentMoneyOut extends Fragment {

    private static final String TAG = "FragmentMoneyOut";

    //all views
    View layoutMain;


    //get data from db
    DatabaseHelper myDB;


    //instance of FragmentUpdate, Fragment
    public static Fragment newUpdateFragment(String name, Item item) {
        Fragment fragment = new FragmentUpdate();

        Bundle args = new Bundle();
        args.putString("FRAG_NAME", name);
        args.putSerializable("ITEM", item);

        fragment.setArguments(args);

        return fragment;        //return fragment that contains its Name as a bundle
    }       //end newUpdateFragment()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment moneyOut is created");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_money_out, null);
        //Log.d(TAG, "onCreateView: fragment moneyOut instantiated user interface view");

        myDB = new DatabaseHelper(getActivity());

        initViews();


        return layoutMain;
    }


    //initializing the views
    public void initViews() {

        RepositoryFirebaseData repositoryFirebaseData = new RepositoryFirebaseData();
        final ArrayList list = repositoryFirebaseData.getItemMoneyOut();

        final AdapterListView adapter = new AdapterListView(getActivity(), R.layout.layout_custom_list_view_money_out, list);
        ListView listView = (ListView) layoutMain.findViewById(R.id.list_view_money_in);     //call the list view layoutMain from our Fragment
        listView.setAdapter(adapter);

        //listens for item click, opens update fragment
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) list.get(position);      //clicked item
                Log.d(TAG, "onItemClick: clicked item: " + item.getName());

                Fragment fragment = FragmentMoneyOut.newUpdateFragment("Money Out", item);

                if (fragment != null) {       //change fragment to selected fragment
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.layout_frame_main, fragment);
                    transaction.addToBackStack(fragment.toString());
                    transaction.commit();
                }       //end if()
            }
        });

    }       //end initViews()


}       //end class
