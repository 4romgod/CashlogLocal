package com.silverback.lucy.cashlog.Model.DatabaseLocal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.silverback.lucy.cashlog.Model.DaoItem;
import com.silverback.lucy.cashlog.Model.POJO.Item;

import java.util.ArrayList;
import java.util.List;


@Database(entities = {Item.class}, exportSchema = false, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static final String DB_NAME = "items.db";

    //So we can always reuse this database instance
    private static MyRoomDatabase INSTANCE;

    public abstract DaoItem daoItem();


    //get a database instance (Reusable)
    public static synchronized MyRoomDatabase getINSTANCE(Context context){
        if(INSTANCE ==null){
            INSTANCE = create(context);
        }
        return INSTANCE;
    }       //end getInstance()


    //create the database
    private static MyRoomDatabase create(Context context){
        RoomDatabase.Builder<MyRoomDatabase> builder =
                Room.databaseBuilder(context.getApplicationContext(), MyRoomDatabase.class, DB_NAME);

        return builder.fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
    }       //end create()


    //callback to insert data onStart()
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    //Async to add data onStart()
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private final DaoItem mDaoItem;

        List<Item> allItems = new ArrayList();

        PopulateDbAsync(MyRoomDatabase db){
            mDaoItem = db.daoItem();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            allItems.add(new Item("MoneyIn", "Salary", 2000, "Money from mom"));
            allItems.add(new Item("MoneyOut", "Grocery", 500, "Money from mom"));
            allItems.add(new Item("MoneyIn", "Boxing", 2000, "Money from mom"));

            for (int i=0; i<allItems.size(); i++){
                mDaoItem.insert(allItems.get(i));
            }

            return null;
        }
    }       //end class

}       //end class
