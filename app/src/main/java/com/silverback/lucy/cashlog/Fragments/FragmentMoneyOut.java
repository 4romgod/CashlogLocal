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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.silverback.lucy.cashlog.Adapters.AdapterListView;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import java.util.ArrayList;


public class FragmentMoneyOut extends Fragment {

    private static final String TAG = "FragmentMoneyOut";

    //all views
    View layoutMain;
    ListView listView;

    ArrayList list;

    AdapterListView adapter;


    //instance of FragmentUpdate, Fragment
    public static Fragment newUpdateFragment(String name, Item item) {
        Fragment fragment = new FragmentUpdate();

        Bundle args = new Bundle();
        args.putString("FRAG_NAME", name);
        fragment.setArguments(args);

        return fragment;        //return fragment that contains its Name as a bundle
    }       //end newUpdateFragment()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment moneyOut is created");

    }       //end onCreate()


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_money_out, null);

        list = new ArrayList();

        adapter = new AdapterListView(getActivity(), R.layout.layout_custom_list_view_money_out, list);
        listView = layoutMain.findViewById(R.id.list_view_money_out);
        listView.setAdapter(adapter);

        return layoutMain;
    }       //end onCreateView()


}       //end class
