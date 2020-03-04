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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.MyDate;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterListView;
import com.silverback.lucy.cashlog.Model.DatabaseLocal.DatabaseHelper;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import java.util.ArrayList;

public class FragmentMoneyIn extends Fragment {

    private static final String TAG = "FragmentMoneyIn";

    //all views
    View layoutMain;
    ListView listView;
    ProgressBar progressBar;

    ArrayList list;
    AdapterListView adapter;

    FirebaseUser currentUser;
    String userId;


    //instance of FragmentUpdate, with fragment name, clicked item, and item ID
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
        Log.d(TAG, "onCreate: fragment home is created");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutMain = inflater.inflate(R.layout.fragment_money_in, null);
        //Log.d(TAG, "onCreateView: fragment moneyIn instantiated user interface view");

        progressBar = (ProgressBar) layoutMain.findViewById(R.id.progressBar);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        populateListView(fetchData());

        return layoutMain;
    }


    public ArrayList<Item> listItemLoggedOut() {
        //get data from db
        DatabaseHelper myDB = new DatabaseHelper(getActivity());
        ArrayList<Item> localList = myDB.getArrayAssets();

        return localList;
    }


    //method to retrieve firebase data
    public ArrayList fetchData() {
        final ArrayList<Object> firebaseList = new ArrayList();

        if (currentUser != null) {

            //thread for getting firebase moneyIn data
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("").child("users").child(currentUser.getUid()).child("Money In");

                    Log.d(TAG, "fetchData(): ABOUT TO GET DATA FROM FIREBASE...");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                progressBar.setVisibility(View.GONE);

                                //values of the objects from firebase
                                String name = dataSnapshot1.child("name").getValue(String.class);
                                float amount = dataSnapshot1.child("amount").getValue(float.class);
                                String description = dataSnapshot1.child("description").getValue(String.class);
                                MyDate date = dataSnapshot1.child("date").getValue(MyDate.class);

                                //initialize the object
                                Item currentItem = new Item(name, amount, description, date);
                                Log.i(TAG, "fetchData: item: " + currentItem.toString());    // + "  date: " + currentItem.getDate().toString());


                                firebaseList.add(0, currentItem);     //add the object in the list

                            }           //end for()

                        }       //end onDataChange()

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }       //end rum\n
            });     //end Thread creation

            thread.start();

        }       //end if(currentUser)


        return firebaseList;
    }       //end fetchData()


    public void populateListView(final ArrayList list) {
        Log.d(TAG, "populateListView(): ABOUT TO POPULATE LIST...");

        progressBar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                adapter = new AdapterListView(getActivity(), R.layout.layout_custom_list_view_money_in, list);
                listView = (ListView) layoutMain.findViewById(R.id.list_view_money_in);     //call the list view layoutMain from our Fragment
                listView.setAdapter(adapter);

                //listens for item click, then open update fragment
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item item = (Item) list.get(position);      //clicked item
                        Log.d(TAG, "onItemClick: clicked item: " + item.getName());

                        Fragment fragment = FragmentMoneyIn.newUpdateFragment("Money In", item);

                        if (fragment != null) {       //change fragment to update frag for selected item type
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.layout_frame_main, fragment);
                            transaction.addToBackStack(fragment.toString());
                            transaction.commit();
                        }       //end if()
                    }
                });


            }       //end onLayoutChange

        });     //end progressBar.add...

    }


}       //end class
