package com.silverback.lucy.cashlog.Model;

import android.content.res.Resources;

import com.silverback.lucy.cashlog.Model.POJO.Item;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Utils.Constants;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * <p>Data Access Object<<p/>
 * <p>interface with abstract methods for data access</p>
 *
 */
@Dao
public interface DaoItem {

    @Insert
    void insert(Item item);

    /**
     * Method to return a list of all items
     * @return LiveData List of all items
     */
    @Query("Select * from " + Item.tableName)
    LiveData<List<Item>> getAllItems();


    /**
     * Method to return a list of all moneyIn items
     * @return LiveData List of all items
     */
    @Query("Select * from " + Item.tableName +" where TYPE = 'Money In'")
    LiveData<List<Item>> getAllItemsMoneyIn();


    /**
     * Method to return a list of all moneyOut items
     * @return LiveData List of all items
     */
    @Query("Select * from " + Item.tableName +" where TYPE = 'Money Out'")
    LiveData<List<Item>> getAllItemsMoneyOut();


    @Update
    void update(Item item);


    /**
     * Takes an item object and deletes it from the database
     * @param item Takes a item Object
     */
    @Delete
    void delete(Item item);

    @Query("DELETE FROM "+Item.tableName)
    void deleteAllItems();

}       //end class
