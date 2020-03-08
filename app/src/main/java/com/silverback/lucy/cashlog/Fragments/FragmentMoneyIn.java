package com.silverback.lucy.cashlog.Fragments;

/*
 * THINGS WWE DO IN THIS FRAGMENT
 *
 * Get Money In data from the database
 * Use our custom List view adapter to inflate data into the list view
 * Listen for an item click on the list view
 * Send to the update screen (with Item type, Item object, item ID)
 *
 *
 */

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterListView;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import java.util.ArrayList;

public class FragmentMoneyIn extends Fragment {

    private static final String TAG = "FragmentMoneyIn";

    //all views
    View layoutMain;
    ListView listView;

    ArrayList list;
    AdapterListView adapter;


    //instance of FragmentUpdate, with fragment name, clicked item, and item ID
    public static Fragment newUpdateFragment(String name, Item item) {
        Fragment fragment = new FragmentUpdate();
        Bundle args = new Bundle();
        args.putString("FRAG_NAME", name);
        //args.putSerializable("ITEM", item);
        fragment.setArguments(args);

        return fragment;        //return fragment that contains its Name as a bundle
    }       //end newUpdateFragment()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment moneyIn is created");

    }       //end onCreate()


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_money_in, null);

        list = new ArrayList();

        adapter = new AdapterListView(getActivity(), R.layout.layout_custom_list_view_money_in, list);
        listView = layoutMain.findViewById(R.id.list_view_money_in);
        listView.setAdapter(adapter);

        return layoutMain;
    }       //end onCreateView()




}       //end class
