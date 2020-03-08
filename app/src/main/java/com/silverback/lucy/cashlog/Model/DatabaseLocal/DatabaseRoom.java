package com.silverback.lucy.cashlog.Model.DatabaseLocal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.silverback.lucy.cashlog.Model.DaoItem;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;


@Database(entities = Item.class, exportSchema = false, version = 1)
public abstract class DatabaseRoom extends RoomDatabase {

    private static final String DB_NAME = "items.db";
    private static DatabaseRoom instance;

    public static synchronized DatabaseRoom getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseRoom.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  instance;
    }

    public abstract DaoItem daoItem();

}
