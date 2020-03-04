package com.silverback.lucy.cashlog.Model;

import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;

import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * <p>Data Access Object<<p/>
 * <p>interface with abstract methods for data access</p>
 *
 */
public interface DaoItem {

    void insert(Item item);


    /**
     * Method to return a list of all the items
     * @return LiveData List of all items
     */
    LiveData<List<Item>> getItems();


    /**
     * Method to return a item specified by ID
     * @param id    The ID of the Item
     * @return      Returns a Item object
     */
    Item getItemById(int id);


    void update(Item item);


    /**
     * Takes an item object and deletes it from the database
     * @param item Takes a item Object
     */
    void delete(Item item);


    /**
     * Delets all the items from the database
     */
    void deleteAllItems();



}       //end class
