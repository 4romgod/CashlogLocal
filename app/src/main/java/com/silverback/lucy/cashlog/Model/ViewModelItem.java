package com.silverback.lucy.cashlog.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.silverback.lucy.cashlog.Model.POJO.Item;

import java.util.List;

public class ViewModelItem extends AndroidViewModel {

    private RepositoryItem mRepositoryItem;

    private LiveData<List<Item>> mAllItems;
    private LiveData<List<Item>> mAllItemsMoneyIn;
    private LiveData<List<Item>> mAllItemsMoneyOut;


    public ViewModelItem(@NonNull Application application) {
        super(application);
        mRepositoryItem = new RepositoryItem(application);
        mAllItems = mRepositoryItem.getAllItems();
        mAllItemsMoneyIn = mRepositoryItem.getAllItemsMoneyIn();
        mAllItemsMoneyOut = mRepositoryItem.getAllItemsMoneyOut();
    }       //end constructor()

    public LiveData<List<Item>> getAllItems(){
        return mAllItems;
    }       //end getAllItems()

    public LiveData<List<Item>> getAllItemsMoneyIn(){
        return mAllItemsMoneyIn;
    }       //end getAllItemsMoneyIn()

    public LiveData<List<Item>> getAllItemsMoneyOut(){
        return mAllItemsMoneyOut;
    }       //end getAllItemsMoneyOut()


    public void insert(Item item){
        mRepositoryItem.insert(item);
    }       //end insert()

    public void update(Item item){
        mRepositoryItem.update(item);
    }

    public void delete(Item item){
        mRepositoryItem.delete(item);
    }

    public void deleteAllItems(){
        mRepositoryItem.deleteAllItems();
    }


}       //end class
