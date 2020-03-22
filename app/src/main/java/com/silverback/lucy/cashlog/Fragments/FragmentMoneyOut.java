package com.silverback.lucy.cashlog.Fragments;

/*
 * THINGS WWE DO IN THIS FRAGMENT
 *
 * Get Money Out data from the database
 * Use our custom List view adapter to inflate data into the list view
 * Listen for an item click on the list view
 * Send to the update screen (with Item type, Item object, item ID)
 *
 *
 */

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.silverback.lucy.cashlog.Model.ViewModelItem;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterListView;
import com.silverback.lucy.cashlog.Model.POJO.Item;
import com.silverback.lucy.cashlog.Utils.Calculations;
import com.silverback.lucy.cashlog.Utils.UI;

import java.util.List;

public class FragmentMoneyOut extends Fragment{

    private static final String TAG = "FragmentMoneyOut";

    private ViewModelItem mViewModelItem;

    //ALL VIEWS
    View layoutMain;
    ListView listView;
    ProgressBar progressBar;
    TextView tvTotalAmount;

    AdapterListView adapter;

    //instance of FragmentUpdate, with fragment name, clicked item, and item ID
    public static Fragment newFragmentUpdate(String type, Item item) {
        Fragment fragment = new FragmentUpdate();
        Bundle args = new Bundle();
        args.putString("FRAG_TYPE", type);
        args.putSerializable("ITEM", item);
        fragment.setArguments(args);

        return fragment;        //return fragment that contains its Name as a bundle
    }       //end newUpdateFragment()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: fragment moneyOut is created");
    }       //end onCreate()


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_money_out, null);
        listView = layoutMain.findViewById(R.id.list_view_money_out);
        progressBar = layoutMain.findViewById(R.id.progressBar);
        tvTotalAmount = layoutMain.findViewById(R.id.tvTotalAmount);

        mViewModelItem = ViewModelProviders.of(this).get(ViewModelItem.class);      //viewModel

        adapter = new AdapterListView(getActivity(), R.layout.layout_custom_list_view_money_out);
        listView.setAdapter(adapter);

        mViewModelItem.getAllItemsMoneyOut().observe(getActivity(), new Observer<List<Item>>() {
            @Override
            public void onChanged(final List<Item> items) {
                Toast.makeText(getContext(), "Something changed", Toast.LENGTH_SHORT).show();
                adapter.setItems(items);
                progressBar.setVisibility(View.GONE);

                //set the total amount
                String amountText = String.format("R%.2f", Calculations.getTotal(items));
                tvTotalAmount.setText(amountText);

                //go to update fragment when an item is clicked
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item item = items.get(position);
                        Fragment fragment = newFragmentUpdate(getString(R.string.type_money_out), item);

                        UI.loadFragment(getActivity().getSupportFragmentManager(), fragment, R.id.layout_frame_main);
                    }
                });

            }
        });

        return layoutMain;
    }       //end onCreateView()


}       //end class
