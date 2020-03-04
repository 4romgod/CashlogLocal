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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silverback.lucy.cashlog.Activities.ActivityMain;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.MyDate;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Adapters.AdapterListViewHistory;
import com.silverback.lucy.cashlog.Model.DatabaseLocal.DatabaseHelper;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import java.util.ArrayList;

/**
 * Shows all the user's items that exists in the daatabase
 */
public class FragmentHistory extends Fragment {

    private static final String TAG = "FragmentHistory";

    FirebaseUser currentUser;

    //all views
    View layoutMain;
    ProgressBar progressBar;


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

        DatabaseHelper myDb = new DatabaseHelper(getActivity());
        final ArrayList arrayItems = myDb.getArrayAssets();
        ArrayList arrayMoneyOut = myDb.getArrayLiability();
        arrayItems.addAll(arrayMoneyOut);

        //unlock the navigation drawer
        ((ActivityMain) getActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        progressBar = (ProgressBar) layoutMain.findViewById(R.id.progressBar);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        fetchData();

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


    /**
     * It fetches the user's items from the firebase database
     */
    public void fetchData() {
        final ArrayList<ArrayList> firebaseList = new ArrayList();

        if (currentUser != null) {
            String userID = currentUser.getUid();

            FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
            DatabaseReference fireRef = fireDb.getReference("").child("users").child(userID);

            Log.d(TAG, "run: about to start addValueEventListener");
            fireRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot dataSnapMoneyIn = dataSnapshot.child("Money In");
                    DataSnapshot dataSnapMoneyOut = dataSnapshot.child("Money Out");

                    Log.d(TAG, "onDataChange: addValueEventListener started");

                    ArrayList<DataSnapshot> listSnaps = new ArrayList<>();
                    listSnaps.add(dataSnapMoneyIn);
                    listSnaps.add(dataSnapMoneyOut);

                    ArrayList<Item> listMoneyIn = new ArrayList<>();
                    ArrayList<Item> listMoneyOut = new ArrayList<>();

                    for (int s = 0; s < listSnaps.size(); s++) {                          //loops through the list of dataSnapshot

                        int counter = 0;
                        for (DataSnapshot itemSnap : listSnaps.get(s).getChildren()) {     //loops through a particular dataSnapshot

                            String key = itemSnap.getKey();
                            String name = itemSnap.child("name").getValue(String.class);
                            float amount = itemSnap.child("amount").getValue(float.class);
                            String description = itemSnap.child("description").getValue(String.class);
                            MyDate date = itemSnap.child("date").getValue(MyDate.class);

                            Item item = new Item(name, amount, description, date);

                            if (s == 0) {
                                listMoneyIn.add(0,item);
                                Log.i(TAG, "onDataChange: moneyIn: " + listMoneyIn.get(counter).toString());
                                counter++;
                                progressBar.setVisibility(View.GONE);
                            } else if (s == 1) {
                                listMoneyOut.add(0,item);
                                Log.i(TAG, "onDataChange: moneyOut: " + item.toString());

                            }

                        }       //end nested for()

                    }       //end big for(listSnaps)

                    firebaseList.add(listMoneyIn);
                    firebaseList.add(listMoneyOut);


                    ArrayList allData = firebaseList.get(0);
                    ArrayList moneyOut = firebaseList.get(1);

                    allData.addAll(moneyOut);

                    Log.i(TAG, "onLayoutChange: DATA: " + allData.get(0).toString());

                    AdapterListViewHistory adapter = new AdapterListViewHistory(getActivity(), R.layout.layout_custom_list_view_history, allData);
                    ListView listView = layoutMain.findViewById(R.id.list_view_history);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });     //end listener

        }       //end if(currentUser)


    }       //end fetchData()


}       //close the class
